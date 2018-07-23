package com.xh.http.async;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.xh.http.FileEntry;
import com.xh.http.HttpListen;
import com.xh.http.IHttp;
import com.xh.http.Response;

import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;


/**
 * 2018/7/6 16:20
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/

public class AsyncHttpImpl implements IHttp {
    @Override
    public void asyncRequest(int method, Map<String, String> params, Map<String, String> heard, HttpListen listen, String url) {
        if (url == null || url.isEmpty())
            return;
        RequestParams mParams = new RequestParams(params);
        AsyncHttpClient client = getClient(heard);
        if (method == IHttp.GET) {
            client.get(url, mParams, new AsynListener(listen));
        } else {
            client.post(url, mParams, new AsynListener(listen));
        }

    }

    @Override
    public void asyncRequestFile(FileEntry entry, Map<String, String> heard, HttpListen listen, String url) {
        throw new RuntimeException("async not this requests");
    }

    @Override
    public void asyncRequestInputStrean(InputStream stream, Map<String, String> heard, HttpListen listen, String url) {
        throw new RuntimeException("async not this requests");
    }

    @Override
    public Response requestFileInputStream(InputStream stream, Map<String, String> heard,  String url) {
        throw new RuntimeException("async not this requests");
    }

    @Override
    public Response request(int method, Map<String, String> params, Map<String, String> heard, String url) {
        throw new RuntimeException("async not this requests");
    }

    @Override
    public Response requestFile(FileEntry entry, Map<String, String> heard,  String url) {
        throw new RuntimeException("async not this requests");
    }

    AsyncHttpClient getClient(Map<String, String> heard) {
        AsyncHttpClient httpClient = new AsyncHttpClient();
        if (heard != null) {
            Iterator<String> keys = heard.keySet().iterator();
            while (keys.hasNext()) {
                String key = keys.next();
                httpClient.addHeader(key, heard.get(key));
            }
        }
        return httpClient;
    }
}
