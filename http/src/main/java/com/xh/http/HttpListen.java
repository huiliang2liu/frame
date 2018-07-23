package com.xh.http;

/**
 * 2018/7/6 16:05
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/

public interface HttpListen {
     void httpStart();
     void httpLoaded(Response response);
     void httpLoadFail(Response response);
}
