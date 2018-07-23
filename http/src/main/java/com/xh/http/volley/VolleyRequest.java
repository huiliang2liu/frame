package com.xh.http.volley;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.xh.http.HttpListen;
import com.xh.http.IHttp;

import java.io.ByteArrayInputStream;
import java.util.Map;


/**
 * 2018/7/6 17:23
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/

public class VolleyRequest extends Request<byte[]> {

    private HttpListen mListen;
    private int code = 500;
    private Map<String, String> mHeard;
    private Map<String, String> heard;
    private Map<String, String> mParams;
    private Object lock = new Object();
    private com.xh.http.Response mResponse;

    public VolleyRequest(String url, int method, Map<String, String> params, Map<String, String> heard, HttpListen listen) {
        super(method == IHttp.POST ? Method.POST : Method.GET, url, null);
        setShouldCache(false);
        mListen = listen;
        mHeard = heard;
        mParams = params;
        setRetryPolicy(new DefaultRetryPolicy(10 * 1000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }


    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return mHeard;
    }
    @Override
    public void deliverError(VolleyError error) {
        mResponse = new com.xh.http.Response(error.networkResponse.statusCode, error.networkResponse.headers, new ByteArrayInputStream(error.networkResponse.data));
        try {
            lock.notifyAll();
        }catch (Exception e){

        }
        if (mListen == null)
            return;
        mListen.httpLoadFail(mResponse);
    }
    /**
     * 这里开始解析数据
     *
     * @param response Response from the network
     * @return
     */
    @Override
    protected Response<byte[]> parseNetworkResponse(NetworkResponse response) {
        code = response.statusCode;
        heard = response.headers;
        return Response.success(response.data,
                HttpHeaderParser.parseCacheHeaders(response));
    }


    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return mParams;
    }

    public com.xh.http.Response get() {
        synchronized (lock) {
            if (mResponse == null)
                try {
                    lock.wait();
                } catch (Exception e) {

                }
            return mResponse;
        }
    }

    /**
     * 回调正确的数据
     *
     * @param response The parsed response returned by
     */
    @Override
    protected void deliverResponse(byte[] response) {
        mResponse = new com.xh.http.Response(code, heard, new ByteArrayInputStream(response));
        synchronized (lock){
            try {
                lock.notifyAll();
            }catch (Exception e){

            }
        }
        if (mListen != null) {
            mListen.httpLoaded(mResponse);
        }

    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        return super.getBody();
    }

}
