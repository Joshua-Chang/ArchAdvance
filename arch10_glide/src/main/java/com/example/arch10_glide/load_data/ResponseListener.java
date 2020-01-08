package com.example.arch10_glide.load_data;

import com.example.arch10_glide.resource.Value;

public interface ResponseListener {
    public void ResponseSuccess(Value value);
    public void ResponseException(Exception e);
}
