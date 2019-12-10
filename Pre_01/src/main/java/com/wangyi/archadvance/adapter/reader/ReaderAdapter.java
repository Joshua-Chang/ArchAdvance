package com.wangyi.archadvance.adapter.reader;

import java.io.BufferedReader;

public class ReaderAdapter implements BReader {
    private IsReader isReader;

    public ReaderAdapter(IsReader isReader) {
        this.isReader = isReader;
    }

    @Override
    public BufferedReader getBReader() {
        return new BufferedReader(isReader.getIsReader());
    }
}
