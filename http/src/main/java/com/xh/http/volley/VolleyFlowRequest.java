package com.xh.http.volley;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.xh.http.HttpListen;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Map;


/**
 * 2018/7/6 17:23
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/

public class VolleyFlowRequest extends Request<byte[]> {
    private HttpListen mListen;
    private int code = 500;
    private Map<String, String> mHeard;
    private Map<String, String> heard;
    private InputStream mStream;
    private final static int LEN = 1024 * 1024;
    private Object lock = new Object();
    private com.xh.http.Response mResponse;

    public VolleyFlowRequest(String url, InputStream stream, Map<String, String> heard, HttpListen listen) {
        super(Method.POST, url, new Err(listen));

        setShouldCache(false);
        mListen = listen;
        mHeard = heard;
        mStream = stream;
        setRetryPolicy(new DefaultRetryPolicy(10 * 1000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
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

    @Override
    public void deliverError(VolleyError error) {
        mResponse = new com.xh.http.Response(error.networkResponse.statusCode, error.networkResponse.headers, new ByteArrayInputStream(error.networkResponse.data));
        try {
            lock.notifyAll();
        } catch (Exception e) {

        }
        if (mListen == null)
            return;
        mListen.httpLoadFail(mResponse);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return mHeard;
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
        return super.getParams();
    }

    /**
     * 回调正确的数据
     *
     * @param response The parsed response returned by
     */
    @Override
    protected void deliverResponse(byte[] response) {
        mResponse = new com.xh.http.Response(code, heard, new ByteArrayInputStream(response));
        synchronized (lock) {
            try {
                lock.notifyAll();
            } catch (Exception e) {

            }
        }
        if (mListen != null) {
            mListen.httpLoaded(mResponse);
        }

    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        if (mStream == null)
            return super.getBody();
        byte[] byff = null;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buff = new byte[LEN];
        try {
            int len = mStream.read(buff);
            while (len > 0) {
                outputStream.write(buff, 0, len);
                len = mStream.read(buff);
            }
            outputStream.flush();
            byff = outputStream.toByteArray();
        } catch (Exception e) {

        } finally {
            try {
                outputStream.close();
            } catch (Exception e) {

            }
            try {
                mStream.close();
            } catch (Exception e) {

            }
        }
        return byff;
    }

    private static class Err implements Response.ErrorListener {
        HttpListen mListen;

        Err(HttpListen listen) {
            mListen = listen;
        }

        @Override
        public void onErrorResponse(VolleyError error) {
            if (mListen == null)
                return;
            com.xh.http.Response response1 = new com.xh.http.Response(error.networkResponse.statusCode, error.networkResponse.headers, new ByteArrayInputStream(error.networkResponse.data));
            mListen.httpLoadFail(response1);
        }
    }
}
