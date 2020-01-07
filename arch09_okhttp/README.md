# OkHttp
## 主线原理

```java
OkHttpClient okHttpClient=new OkHttpClient.Builder().build();//OkHttpClient构建者
Request request = new Request.Builder()//Request构建者
        .url("https://www.baidu.com")
        .get()
        .build();
Call call = okHttpClient.newCall(request);//实际调用RealCall.newRealCall返回RealCall
//异步
call.enqueue(new Callback() {//RealCall.enqueue(不能二次执行)--》调度器判断运行/等待队列后Dispatcher.execute--》AsyncCall真正执行耗时任务
    @Override
    public void onFailure(Call call, IOException e) {
        System.out.println(e.toString());
    }
    @Override
    public void onResponse(Call call, Response response) throws IOException {
        String string = response.body().string();
        System.out.println(string);
    }
});
```


OkHttpClient
```java
@Override public Call newCall(Request request) {
  return RealCall.newRealCall(this, request, false /* for web socket */);
}
```
RealCall
```java
static RealCall newRealCall(OkHttpClient client, Request originalRequest, boolean forWebSocket) {
  // Safely publish the Call instance to the EventListener.
  RealCall call = new RealCall(client, originalRequest, forWebSocket);
  call.eventListener = client.eventListenerFactory().create(call);
  return call;
}
```

Call实现类RealCall

```java
@Override public void enqueue(Callback responseCallback) {
  synchronized (this) {//不能二次调用
    if (executed) throw new IllegalStateException("Already Executed");
    executed = true;
  }
  captureCallStackTrace();
  eventListener.callStart(this);
  client.dispatcher().enqueue(new AsyncCall(responseCallback));//传AsyncCall给调度器执行enqueue方法
}
```

Dispatcher

```java
/** Running asynchronous calls. Includes canceled calls that haven't finished yet. */
private final Deque<AsyncCall> runningAsyncCalls = new ArrayDeque<>();//Deque双端队列

/** Running synchronous calls. Includes canceled calls that haven't finished yet. */
private final Deque<RealCall> runningSyncCalls = new ArrayDeque<>();
private int maxRequests = 64;//最大同时的请求任务
private int maxRequestsPerHost = 5;//最大同时访问的服务器
```

```java
synchronized void enqueue(AsyncCall call) {
  //同时请求任务小于64个&&同时访问服务器小于5个---》把请求任务加入运行队列 然后执行
  if (runningAsyncCalls.size() < maxRequests && runningCallsForHost(call) < maxRequestsPerHost) {
    runningAsyncCalls.add(call);//运行队列
    executorService().execute(call);//执行（AsyncCall执行耗时任务）
  } else {
    readyAsyncCalls.add(call);//等待队列
  }
}
```
RealCall内部类AsyncCall，实现NamedRunnable，线程池直接执行execute方法
```java
final class AsyncCall extends NamedRunnable {
  private final Callback responseCallback;

  AsyncCall(Callback responseCallback) {
    super("OkHttp %s", redactedUrl());
    this.responseCallback = responseCallback;
  }

  String host() {
    return originalRequest.url().host();
  }

  Request request() {
    return originalRequest;
  }

  RealCall get() {
    return RealCall.this;
  }

  @Override protected void execute() {//线程池直接执行此方法
    boolean signalledCallback = false;//责任划分标识
    try {
      Response response = getResponseWithInterceptorChain();//拿到Response
      if (retryAndFollowUpInterceptor.isCanceled()) {
        signalledCallback = true;
        responseCallback.onFailure(RealCall.this, new IOException("Canceled"));
      } else {
        signalledCallback = true;
        responseCallback.onResponse(RealCall.this, response);
      }
    } catch (IOException e) {
      if (signalledCallback) {//责任划分：用户造成的
        // Do not signal the callback twice!
        Platform.get().log(INFO, "Callback failure for " + toLoggableString(), e);
      } else {//责任划分：Okhttp造成，Response确实发生了异常
        eventListener.callFailed(RealCall.this, e);
        responseCallback.onFailure(RealCall.this, e);
      }
    } finally {
      client.dispatcher().finished(this);
    }
  }
}
```
拦截器责任链模式 
```java
Response getResponseWithInterceptorChain() throws IOException {
  // Build a full stack of interceptors.
  List<Interceptor> interceptors = new ArrayList<>();
  interceptors.addAll(client.interceptors());//用户自定义的拦截器
  interceptors.add(retryAndFollowUpInterceptor);//重试与重定向拦截器
  interceptors.add(new BridgeInterceptor(client.cookieJar()));//请求头处理拦截器
  interceptors.add(new CacheInterceptor(client.internalCache()));//缓存拦截器
  interceptors.add(new ConnectInterceptor(client));//服务器真实请求拦截器
  if (!forWebSocket) {
    interceptors.addAll(client.networkInterceptors());
  }
  interceptors.add(new CallServerInterceptor(forWebSocket));

  Interceptor.Chain chain = new RealInterceptorChain(interceptors, null, null, null, 0,
      originalRequest, this, eventListener, client.connectTimeoutMillis(),
      client.readTimeoutMillis(), client.writeTimeoutMillis());

  return chain.proceed(originalRequest);
}
```

AsyncCall父类NamedRunnable
```java
public abstract class NamedRunnable implements Runnable {
  protected final String name;

  public NamedRunnable(String format, Object... args) {
    this.name = Util.format(format, args);
  }

  @Override public final void run() {
    String oldName = Thread.currentThread().getName();
    Thread.currentThread().setName(name);
    try {
      execute();//线程池直接执的方法execute（）给子类实现
    } finally {
      Thread.currentThread().setName(oldName);
    }
  }

  protected abstract void execute();
}
```

## 线程池原理

分析结果：OKHTTP里面的线程池，采用的是缓存 方案
OKHTTP里面的线程池：采用的是缓存 方案，+ 线程工厂 name  不是守护线程

---> 总结：OKHTTP线程池采用的是缓存方案 + 定义线程工程（设置线程名，设置不是守护线程）
缓存方案：参数1 == 0
         参数2 Integer.Max
         参数3/4：60s闲置时间 只要参数1 ,只要Runnable > 参数1 起作用(60s之内 就会复用之前的任务，60s之后就会回收任务)

```java
/**
*  参数一：corePoolSize 核心线程数
*  参数二：maximumPoolSize 最大线程数 线程池非核心线程数 线程池规定大小
*  参数三/四：时间数值keepAliveTime， 单位：时分秒  60s 正在执行的任务Runnable20 < corePoolSize --> 参数三/参数四 才会起作用：Runnable1执行完毕后 闲置60s，如果过了闲置60s,会回收掉Runnable1任务,，如果在闲置时间60s 复用此线程Runnable1
*  参数五：workQueue队列 ：会把超出的任务加入到队列中 缓存起来
*  参数六：线程池工厂
*/

public synchronized ExecutorService executorService() {
  if (executorService == null) {
//（OKHTTP）永远只有一个线程在跑：MAX_VALUE 》 0 --> 参数三/参数四 才会起作用
//Runnable1执行完毕后 闲置60s，如果在闲置时间60s内 复用此线程Runnable1,如果过了闲置60s,会回收掉Runnable1任务,
    executorService = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60, TimeUnit.SECONDS,
        new SynchronousQueue<Runnable>(), Util.threadFactory("OkHttp Dispatcher", false));
  }
  return executorService;
}
```
```java
//        (okhttp)线程工厂
ExecutorService executorService = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread result = new Thread(r, "OkHttp Dispatcher");
                result.setDaemon(false);
                return result;
            }
        });
```

顶层接口

- ​    // Executor
- ​    // --- ExecutorService
- ​    //   --- AbstractExecutorService
- ​    //      ---- ThreadPoolExecutor

```java
public interface Executor {

    /**
     * Executes the given command at some time in the future.  The command
     * may execute in a new thread, in a pooled thread, or in the calling
     * thread, at the discretion of the {@code Executor} implementation.
     *
     * @param command the runnable task
     * @throws RejectedExecutionException if this task cannot be
     * accepted for execution
     * @throws NullPointerException if command is null
     */
    void execute(Runnable command);//必须为Runnable
}
```

```java
public interface ExecutorService extends Executor {}
```

```java
public abstract class AbstractExecutorService implements ExecutorService {}
```

```java
public class ThreadPoolExecutor extends AbstractExecutorService {}
```

```java
// Java设计者 考虑到了不用使用线程池的参数配置，提供了API包装
/**
* new ThreadPoolExecutor(0, Integer.MAX_VALUE,60L, TimeUnit.SECONDS,new SynchronousQueue<Runnable>());
*/
ExecutorService executorService = Executors.newCachedThreadPool();//缓存线程池
/**
* new ThreadPoolExecutor(1, 1,0L, TimeUnit.MILLISECONDS,new LinkedBlockingQueue<Runnable>()));
*/
ExecutorService executorService = Executors.newSingleThreadExecutor();//单例，线程池里只有一个核心线程，最大线程也一个
/**
* new ThreadPoolExecutor(nThreads, nThreads,0L, TimeUnit.MILLISECONDS,new LinkedBlockingQueue<Runnable>());
*/
ExecutorService executorService = Executors.newFixedThreadPool(5);//固定线程池，线程池里有N个核心线程，N个最大线程
```