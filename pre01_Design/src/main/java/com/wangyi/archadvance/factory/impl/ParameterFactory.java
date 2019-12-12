package com.wangyi.archadvance.factory.impl;

import com.wangyi.archadvance.factory.Api;

public class ParameterFactory {
    public static Api createApi(int parameter){
        switch (parameter) {
            case 1:
                return new ApiImpl_A();
            case 2:
                return new ApiImpl_B();
        }
        return null;
    }
}
