package com.ysxsoft.deliverylocker.network;

import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;


public interface IPostJsonStringCb {
    void onSuccess(String str, String data);
    void onError(Response<String> response);
    void onOther(String str);
    void onStart(Request<String, ? extends Request> str);
    void onEmpty();
    void onFinish();
}
