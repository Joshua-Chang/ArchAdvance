package com.wangyi.arch09_okhttp.interceptorchain;

import com.wangyi.arch09_okhttp.myhttp.Request2;
import com.wangyi.arch09_okhttp.myhttp.Response2;

import java.io.IOException;

public interface Chain2 {
    Request2 getRequest();

    Response2 getResponse(Request2 request2) throws IOException;
}
