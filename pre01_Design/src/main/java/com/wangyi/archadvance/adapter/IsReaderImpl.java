package com.wangyi.archadvance.adapter;

import com.wangyi.archadvance.adapter.reader.IsReader;

import java.io.InputStream;
import java.io.InputStreamReader;

public class IsReaderImpl implements IsReader {
    private InputStream is;

    public IsReaderImpl(InputStream is) {
        this.is = is;
    }

    @Override
    public InputStreamReader getIsReader() {
        return new InputStreamReader(is);
    }
}
