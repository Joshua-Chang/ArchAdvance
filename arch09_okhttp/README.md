# OkHttp

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