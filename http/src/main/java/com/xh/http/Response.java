package com.xh.http;

import java.io.InputStream;
import java.util.Map;

/**
 * 2018/7/6 16:11
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/

public class Response {
    private int code;
    private Map<String, String> heard;
    private InputStream is;

    public Response(int code, Map<String, String> heard, InputStream is) {
        this.code = code;
        this.heard = heard;
        this.is = is;

    }

    public int getCode() {
        return code;
    }

    public Map<String, String> getHeard() {
        return heard;
    }

    public InputStream getInputStream() {
        return is;
    }
}
