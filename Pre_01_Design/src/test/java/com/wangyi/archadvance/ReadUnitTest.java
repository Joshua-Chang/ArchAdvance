package com.wangyi.archadvance;

import com.wangyi.archadvance.adapter.IsReaderImpl;
import com.wangyi.archadvance.adapter.reader.BReader;
import com.wangyi.archadvance.adapter.reader.ReaderAdapter;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class ReadUnitTest {
    @Test
    public BufferedReader getReader(File file) throws IOException {
        //文件字节流(文件读成流)
        FileInputStream fis = new FileInputStream(file);
        //字节读取流
        InputStreamReader isr = new InputStreamReader(fis);
        //缓存字节流
        return new BufferedReader(isr);
    }

    @Test
    public BufferedReader reader(File file) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        BReader bReader=new ReaderAdapter(new IsReaderImpl(fis));
        return bReader.getBReader();
    }
}
