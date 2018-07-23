package com.xh.http.volley;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.xh.http.FileEntry;
import com.xh.http.HttpListen;
import com.xh.http.IHttp;
import com.xh.http.Response;

import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;


/**
 * 2018/7/6 18:37
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/

public class VolleyHttpImpl implements IHttp {
    private RequestQueue mRequestQueue;

    public VolleyHttpImpl(Context context) {
        mRequestQueue = Volley.newRequestQueue(context);
        Log.e("VolleyHttpImpl","VolleyHttpImpl");
    }

    @Override
    public void asyncRequest(int method, Map<String, String> params, Map<String, String> heard, HttpListen listen, String url) {
        if (method == IHttp.GET&&params!=null&&params.size()>0) {
            StringBuffer sb = null;
            if (url.indexOf("?") > 1)
                sb = new StringBuffer("&");
            else
                sb = new StringBuffer("?");
            Iterator<String> keys=params.keySet().iterator();
            while (keys.hasNext()){
                String key=keys.next();
                String value=heard.get(key);
                sb.append(key).append("=").append(value).append("&");
            }
            url=sb.toString();
        }
        mRequestQueue.add(new VolleyRequest(url, method, params, heard, listen));
    }

    @Override
    public void asyncRequestFile(FileEntry entry, Map<String, String> heard, HttpListen listen, String url) {
        mRequestQueue.add(new VolleyFileRequest(url, entry, heard, listen));
    }

    @Override
    public void asyncRequestInputStrean(InputStream stream, Map<String, String> heard, HttpListen listen, String url) {
        mRequestQueue.add(new VolleyFlowRequest(url, stream, heard, listen));
    }

    @Override
    public Response request(int method, Map<String, String> params, Map<String, String> heard, String url) {
        if (method == IHttp.GET&&params!=null&&params.size()>0) {
            StringBuffer sb = null;
            if (url.indexOf("?") > 1)
                sb = new StringBuffer("&");
            else
                sb = new StringBuffer("?");
            Iterator<String> keys=params.keySet().iterator();
            while (keys.hasNext()){
                String key=keys.next();
                String value=heard.get(key);
                sb.append(key).append("=").append(value).append("&");
            }
            url=sb.toString();
        }
        VolleyRequest request = new VolleyRequest(url, method, params, heard, null);
        mRequestQueue.add(request);
        return request.get();
    }

    @Override
    public Response requestFile(FileEntry entry, Map<String, String> heard, String url) {
        VolleyFileRequest request = new VolleyFileRequest(url, entry, heard, null);
        mRequestQueue.add(request);
        return request.get();
    }

    @Override
    public Response requestFileInputStream(InputStream stream, Map<String, String> heard, String url) {
        VolleyFlowRequest request = new VolleyFlowRequest(url, stream, heard, null);
        mRequestQueue.add(request);
        return request.get();
    }
}
