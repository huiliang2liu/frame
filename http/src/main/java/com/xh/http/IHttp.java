package com.xh.http;

import java.io.InputStream;
import java.util.Map;

/**
 * 2018/7/6 16:10
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/

public interface IHttp {
    public final static int GET = 0;
    public final static int POST = 1;

    void asyncRequest(int method, Map<String, String> params, Map<String, String> heard, HttpListen listen, String url);

    void asyncRequestFile(FileEntry entry, Map<String, String> heard, HttpListen listen, String url);

    void asyncRequestInputStrean( InputStream  stream, Map<String, String> heard, HttpListen listen, String url);

    Response request(int method, Map<String, String> params, Map<String, String> heard, String url);

    Response requestFile(FileEntry entry, Map<String, String> heard, String url);

    Response requestFileInputStream(InputStream stream, Map<String, String> heard, String url);
}
