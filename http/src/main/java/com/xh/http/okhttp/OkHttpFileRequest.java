package com.xh.http.okhttp;

import com.xh.http.FileEntry;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;

/**
 * 2018/7/9 14:44
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/

public class OkHttpFileRequest extends RequestBody {
    private FileEntry mEntry;
    private MediaType mType;
    private static final String CONTENT_TYPE = "multipart/form-data"; // 内容类型
    public static final String BOUNDARY = UUID.randomUUID().toString(); // 边界标识
    private final static int LEN = 1024 * 1024;
    public OkHttpFileRequest(FileEntry entry, MediaType type) {
        mEntry=entry;
        mType=type;
    }

    @Override
    public MediaType contentType() {
        return mType;
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        if (mEntry == null || mEntry.file == null)
            return ;
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
        InputStream inputStream = null;
        byte[] byff = null;
        try {
            inputStream = new FileInputStream(mEntry.file);
            sink.write(sb.toString().getBytes("utf-8"));
                /*第五行*/
            //文件的二进制数据 + "\r\n"
            byte[] buff = new byte[LEN];
            int len = inputStream.read(buff);
            while (len > 0) {
                sink.write(buff, 0, len);
            }
            sink.write("\r\n".getBytes("utf-8"));
            String endLine = "--" + BOUNDARY + "--" + "\r\n";
            sink.write(endLine.toString().getBytes("utf-8"));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            if (inputStream != null)
                try {
                    inputStream.close();
                } catch (Exception e) {

                }
        }

    }
}
