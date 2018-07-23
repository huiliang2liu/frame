package com.xh.http.okhttp;

import android.util.Log;

import com.xh.http.FileEntry;
import com.xh.http.HttpListen;
import com.xh.http.IHttp;
import com.xh.http.Response;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * 2018/7/9 9:41
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/

public class OkHttpIpml implements IHttp {
    private final  static String TAG="OkHttpIpml";
    public OkHttpIpml(){
        Log.e(TAG,"OkHttpIpml");
    }
    @Override
    public void asyncRequest(int method, Map<String, String> params, Map<String, String> heard, final HttpListen listen, String url) {
        url=setParams(url,method,params);
        OkHttpClient client = new OkHttpClient();
        Request.Builder builder=new Request.Builder().url(url);
        addHeader(builder,heard);
        if(method==IHttp.POST&&params!=null&&params.size()>0){
            FormBody.Builder formBody = new FormBody.Builder();//创建表单请求体
            Iterator<String> keys=params.keySet().iterator();
            while (keys.hasNext()){
                String key=keys.next();
                String value=params.get(key);
                formBody.add(key,value);
            }
            builder.post(formBody.build());
        }
        if(listen!=null)
        listen.httpStart();
        client.newCall(builder.build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Response response=new Response(500,null,null);
                if(listen!=null)
                listen.httpLoadFail(response);
            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                Response response1=creatResponse(response);
                if(response.isSuccessful()&& listen!=null)
                listen.httpLoaded(response1);
                else  if(listen!=null)
                    listen.httpLoadFail(response1);
            }
        });
    }

    @Override
    public void asyncRequestFile(FileEntry entry, Map<String, String> heard,final HttpListen listen, String url) {
        Request.Builder builder=new Request.Builder().url(url);
        addHeader(builder,heard);
        OkHttpFileRequest okHttpFileRequest=new OkHttpFileRequest(entry,null);
        builder.post(okHttpFileRequest);
        OkHttpClient client = new OkHttpClient();
        if(listen!=null)
        listen.httpStart();
        client.newCall(builder.build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Response response=new Response(500,null,null);
                if(listen!=null)
                listen.httpLoadFail(response);
            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                Response response1=creatResponse(response);
                if(response.isSuccessful()&&listen!=null)
                    listen.httpLoaded(response1);
                else  if(listen!=null)
                    listen.httpLoadFail(response1);
            }
        });
    }

    @Override
    public void asyncRequestInputStrean(InputStream stream, Map<String, String> heard, final HttpListen listen, String url) {
        Request.Builder builder=new Request.Builder().url(url);
        addHeader(builder,heard);
        OkHttpStreamRequest okHttpFileRequest=new OkHttpStreamRequest(stream,null);
        builder.post(okHttpFileRequest);
        OkHttpClient client = new OkHttpClient();
        if(listen!=null)
            listen.httpStart();
        client.newCall(builder.build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Response response=new Response(500,null,null);
                if(listen!=null)
                listen.httpLoadFail(response);
            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                Response response1=creatResponse(response);
                if(response.isSuccessful()&&listen!=null)
                    listen.httpLoaded(response1);
                else  if(listen!=null)
                    listen.httpLoadFail(response1);
            }
        });
    }

    @Override
    public Response request(int method, Map<String, String> params, Map<String, String> heard, String url) {
        url=setParams(url,method,params);
        OkHttpClient client = new OkHttpClient();
        Request.Builder builder=new Request.Builder().url(url);
        addHeader(builder,heard);
        if(method==IHttp.POST&&params!=null&&params.size()>0){
            FormBody.Builder formBody = new FormBody.Builder();//创建表单请求体
            Iterator<String> keys=params.keySet().iterator();
            while (keys.hasNext()){
                String key=keys.next();
                String value=params.get(key);
                formBody.add(key,value);
            }
            builder.post(formBody.build());
        }
       try {
           return creatResponse(client.newCall(builder.build()).execute());
       }catch (Exception e){

       }
       return null;
    }

    @Override
    public Response requestFile(FileEntry entry, Map<String, String> heard, String url) {
        Request.Builder builder=new Request.Builder().url(url);
        addHeader(builder,heard);
        OkHttpFileRequest okHttpFileRequest=new OkHttpFileRequest(entry,null);
        builder.post(okHttpFileRequest);
        OkHttpClient client = new OkHttpClient();
        try {
            return  creatResponse(client.newCall(builder.build()).execute());
        }catch (Exception e){

        }
        return  null;
    }

    @Override
    public Response requestFileInputStream(InputStream stream, Map<String, String> heard, String url) {
        Request.Builder builder=new Request.Builder().url(url);
        addHeader(builder,heard);
        OkHttpStreamRequest okHttpFileRequest=new OkHttpStreamRequest(stream,null);
        builder.post(okHttpFileRequest);
        OkHttpClient client = new OkHttpClient();
        try {
            return  creatResponse(client.newCall(builder.build()).execute());
        }catch (Exception e){

        }
        return null;
    }
    String setParams( String url,int method, Map<String, String> params){
        if (method == IHttp.GET&&params!=null&&params.size()>0) {
            StringBuffer sb = null;
            if (url.indexOf("?") > 1)
                sb = new StringBuffer("&");
            else
                sb = new StringBuffer("?");
            Iterator<String> keys=params.keySet().iterator();
            while (keys.hasNext()){
                String key=keys.next();
                String value=params.get(key);
                sb.append(key).append("=").append(value).append("&");
            }
            url=sb.substring(0,sb.length()-1);
        }
        return url;
    }
    Response creatResponse(okhttp3.Response response){
        Map<String,String>header=new HashMap<>();
        if(response.headers().size()>0){
            int size=response.headers().size();
            for (int i=0;i<size;i++){
                String key=response.headers().name(i);
                String value=response.headers().value(i);
                header.put(key,value);
            }
        }
        Response response1=new Response(response.code(),header,response.body().byteStream());
//        response.close();
        return response1;
    }
    void addHeader(Request.Builder builder,Map<String, String> heard){
        if(heard!=null&&heard.size()>0){
            Iterator<String> keys=heard.keySet().iterator();
            while (keys.hasNext()){
                String key=keys.next();
                String value=heard.get(key);
                builder.addHeader(key,value);
            }
        }
    }
}
