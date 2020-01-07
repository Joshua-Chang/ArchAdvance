package com.wangyi.arch09_okhttp.interceptorchain;

import com.wangyi.arch09_okhttp.myhttp.Request2;
import com.wangyi.arch09_okhttp.myhttp.RequestBody2;
import com.wangyi.arch09_okhttp.myhttp.Response2;
import com.wangyi.arch09_okhttp.myhttp.SocketRequestServer;

import java.io.IOException;
import java.util.Map;
/**
 * 请求头拦截器处理
 */
public class RequestHeaderInterceptor2 implements Interceptor2 {

    @Override
    public Response2 doNext(Chain2 chain2) throws IOException{
        InterceptorChainManager chainManager = (InterceptorChainManager) chain2;
        Request2 request = chainManager.getRequest();
        Map<String, String> mHeaderList =request.getmHeaderList();
        mHeaderList.put("Host",new SocketRequestServer().getHost(request));
        if (request.getRequestMethod().equalsIgnoreCase("POST")) {
            // 请求体   type lang
            /**
             * Content-Length: 48
             * Content-Type: application/x-www-form-urlencoded
             */
            mHeaderList.put("Content-Type", RequestBody2.TYPE);
            mHeaderList.put("Content-Length",request.getRequestBody2().getBody().length()+"");
//            mHeaderList.put("Connection","Keep-Alive");
        }
        // ChainManager.getResponse(更新后的Request)
        return chain2.getResponse(request);//chainManager 执行下一个拦截器（任务节点）
    }
}
