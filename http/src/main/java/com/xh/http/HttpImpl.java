package com.xh.http;

import android.content.Context;

import com.xh.http.okhttp.OkHttpIpml;
import com.xh.http.volley.VolleyHttpImpl;

import java.io.InputStream;
import java.util.Map;

/**
 * 2018/7/9 17:54
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/

public class HttpImpl implements IHttp {
    private IHttp http;

    public HttpImpl(Context context) {
        try {
            Class.forName("okhttp3.OkHttpClient");
            http = new OkHttpIpml();
        } catch (Exception e) {
            try {
                Class.forName("com.android.volley.toolbox.Volley");
                http = new VolleyHttpImpl(context);
            } catch (Exception e1) {
                throw new RuntimeException("not found http");
            }
        }
    }

    public void asyncGet(Map<String, String> params, Map<String, String> heard, HttpListen listen, String url) {
        http.asyncRequest(IHttp.GET, params, heard, listen, url);
    }

    public void asyncGet(String url) {
        http.asyncRequest(IHttp.GET, null, null, null, url);
    }

    public void asyncGet(HttpListen listen, String url) {
        http.asyncRequest(IHttp.GET, null, null, listen, url);
    }

    public void asyncGet(Map<String, String> heard, HttpListen listen, String url) {
        http.asyncRequest(IHttp.GET, null, heard, listen, url);
    }


    public void asyncPost(Map<String, String> params, String url) {
        http.asyncRequest(IHttp.POST, params, null, null, url);
    }

    public void asyncPost(Map<String, String> params, HttpListen listen, String url) {
        http.asyncRequest(IHttp.POST, params, null, listen, url);
    }

    public void asyncPost(Map<String, String> params, Map<String, String> heard, HttpListen listen, String url) {
        http.asyncRequest(IHttp.POST, params, heard, listen, url);
    }

    @Override
    public void asyncRequest(int method, Map<String, String> params, Map<String, String> heard, HttpListen listen, String url) {
        http.asyncRequest(method, params, heard, listen, url);
    }

    public void asyncRequestFile(FileEntry entry, HttpListen listen, String url) {
        http.asyncRequestFile(entry, null, listen, url);
    }

    public void asyncRequestFile(FileEntry entry, String url) {
        http.asyncRequestFile(entry, null, null, url);
    }

    @Override
    public void asyncRequestFile(FileEntry entry, Map<String, String> heard, HttpListen listen, String url) {
        http.asyncRequestFile(entry, heard, listen, url);
    }

    @Override
    public void asyncRequestInputStrean(InputStream stream, Map<String, String> heard, HttpListen listen, String url) {
        http.asyncRequestInputStrean(stream, heard, listen, url);
    }

    public void asyncRequestInputStrean(InputStream stream, HttpListen listen, String url) {
        http.asyncRequestInputStrean(stream, null, listen, url);
    }

    public void asyncRequestInputStrean(InputStream stream, String url) {
        http.asyncRequestInputStrean(stream, null, null, url);
    }

    public Response get(String url) {
        return http.request(IHttp.GET, null, null, url);
    }

    public Response get(Map<String, String> params, String url) {
        return http.request(IHttp.GET, params, null, url);
    }

    public Response get(Map<String, String> params, Map<String, String> heard, String url) {
        return http.request(IHttp.GET, params, heard, url);
    }

    public Response post(Map<String, String> params, String url) {
        return http.request(IHttp.POST, params, null, url);
    }

    public Response post(Map<String, String> params, Map<String, String> heard, String url) {
        return http.request(IHttp.POST, params, heard, url);
    }

    @Override
    public Response request(int method, Map<String, String> params, Map<String, String> heard, String url) {
        return http.request(method, params, heard, url);
    }

    @Override
    public Response requestFile(FileEntry entry, Map<String, String> heard, String url) {
        return http.requestFile(entry, heard, url);
    }

    public Response requestFile(FileEntry entry, String url) {
        return http.requestFile(entry, null, url);
    }

    @Override
    public Response requestFileInputStream(InputStream stream, Map<String, String> heard, String url) {
        return http.requestFileInputStream(stream, heard, url);
    }

    public Response requestFileInputStream(InputStream stream, String url) {
        return http.requestFileInputStream(stream, null, url);
    }
}
