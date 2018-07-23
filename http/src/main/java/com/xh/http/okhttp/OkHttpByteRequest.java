package com.xh.http.okhttp;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;


/**
 * 2018/7/9 9:52
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/

public class OkHttpByteRequest extends RequestBody {
    private MediaType mMediaType;
    private byte[] mBuff;

    public OkHttpByteRequest(MediaType mediaType, byte[] buff) {
        mMediaType = mediaType;
        mBuff = buff;
    }

    @Override
    public long contentLength() throws IOException {
        if(mBuff==null)
            return super.contentLength();
        return mBuff.length;
    }

    @Override
    public MediaType contentType() {
        return mMediaType;
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        if(mBuff!=null)
        sink.write(mBuff);
    }
}
