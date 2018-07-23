package com.xh.http.volley;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.xh.http.FileEntry;
import com.xh.http.HttpListen;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.UUID;


/**
 * 2018/7/6 17:23
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/

public class VolleyFileRequest extends Request<byte[]> {
    private static final String CONTENT_TYPE = "multipart/form-data"; // 内容类型
    public static final String BOUNDARY = UUID.randomUUID().toString(); // 边界标识
    private final static int LEN = 1024 * 1024;
    private HttpListen mListen;
    private int code = 500;
    private Map<String, String> mHeard;
    private Map<String, String> heard;
    private FileEntry mEntry;
    private Object lock = new Object();
    private com.xh.http.Response mResponse;

    public VolleyFileRequest(String url, FileEntry entry, Map<String, String> heard, HttpListen listen) {
        super(Method.POST, url, new Err(listen));

        setShouldCache(false);
        mListen = listen;
        mHeard = heard;
        mEntry = entry;
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
        if (mEntry == null || mEntry.file == null)
            return super.getBody();
        StringBuffer sb = new StringBuffer();
            /*第一行*/
        //`"--" + BOUNDARY + "\r\n"`
        sb.append("--" + BOUNDARY);
        sb.append("\r\n");
            /*第二行*/
        //Content-Disposition: form-data; name="参数的名称"; filename="上传的文件名" + "\r\n"
        sb.append("Content-Disposition: form-data;");
        sb.append(" name=\"");
        sb.append(mEntry.name);
        sb.append("\"");
        sb.append("; filename=\"");
        sb.append(mEntry.fileName);
        sb.append("\"");
        sb.append("\r\n");
            /*第三行*/
        //Content-Type: 文件的 mime 类型 + "\r\n"
        sb.append("Content-Type: ");
        sb.append(mEntry.contentType);
        sb.append("\r\n");
            /*第四行*/
        //"\r\n"
        sb.append("\r\n");
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        InputStream inputStream = null;
        byte[] byff = null;
        try {
            inputStream = new FileInputStream(mEntry.file);
            outputStream.write(sb.toString().getBytes("utf-8"));
                /*第五行*/
            //文件的二进制数据 + "\r\n"
            byte[] buff = new byte[LEN];
            int len = inputStream.read(buff);
            while (len > 0) {
                outputStream.write(buff, 0, len);
            }
            outputStream.write("\r\n".getBytes("utf-8"));
            String endLine = "--" + BOUNDARY + "--" + "\r\n";
            outputStream.write(endLine.toString().getBytes("utf-8"));
            byff = outputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                outputStream.close();
            } catch (Exception e) {

            }
            if (inputStream != null)
                try {
                    inputStream.close();
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
