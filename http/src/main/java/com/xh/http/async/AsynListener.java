package com.xh.http.async;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.xh.http.HttpListen;
import com.xh.http.Response;

import java.io.ByteArrayInputStream;
import java.util.Map;

import cz.msebera.android.httpclient.Header;


/**
 * 2018/7/6 16:51
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/

public class AsynListener extends AsyncHttpResponseHandler {
    private  HttpListen mListen;
    public  AsynListener(HttpListen listen){
        mListen=listen;
    }
    @Override
    public void onStart() {
        // called before request is started
        if(mListen!=null)
            mListen.httpStart();
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, byte[] response) {
        // called when response HTTP status is "200 OK"
        if(mListen==null)
            return;
        Map<String,String> heard=null;
        if(headers!=null||headers.length>0){
            for(Header header:headers){
                heard.put(header.getName(),header.getValue());
            }
        }
        Response response1=new Response(statusCode,heard,new ByteArrayInputStream(response));
        mListen.httpLoaded(response1);
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
        // called when response HTTP status is "4XX" (eg. 401, 403, 404)
        if(mListen==null)
            return;
        Map<String,String> heard=null;
        if(headers!=null||headers.length>0){
            for(Header header:headers){
                heard.put(header.getName(),header.getValue());
            }
        }
        Response response1=new Response(statusCode,heard,new ByteArrayInputStream(errorResponse));
        mListen.httpLoadFail(response1);
    }

    @Override
    public void onRetry(int retryNo) {
        // called when request is retried
    }
}
