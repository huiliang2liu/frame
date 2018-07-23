package com.xh.http.okhttp;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;


/**
 * 2018/7/9 14:33
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/

public class OkHttpStreamRequest extends RequestBody {
    private final static int LENGTH = 1024 * 1024;
    private InputStream mInputStream;
    private MediaType mMediaType;


    public OkHttpStreamRequest(InputStream inputStream, MediaType mediaType) {
        mMediaType = mediaType;
        mInputStream = inputStream;
    }


    @Override
    public MediaType contentType() {
        return mMediaType;
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        if (mInputStream == null)
            return;
        byte[] buff = new byte[LENGTH];
        int len = mInputStream.read(buff);
        while (len > 0) {
            sink.write(buff, 0, len);
            len = mInputStream.read(buff);
        }
        sink.flush();
        try {
            mInputStream.close();
        } catch (Exception e) {

        }
    }
}
