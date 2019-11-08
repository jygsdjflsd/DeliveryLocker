package com.ysxsoft.deliverylocker.network;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;


import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.PostRequest;
import com.lzy.okgo.request.base.Request;
import com.ysxsoft.deliverylocker.app.MyApplication;
import com.ysxsoft.deliverylocker.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.List;
import java.util.Map;

public class OkGoUtils {

    /**
     * 网络请求的相关参数
     */
    public static class RequestOption {
        public Context context;
        public Map<String, String> params;
        public List<File> paramFiles;
        public List<String> paramFileKeys;
        public File paramFile;
        public String fileKey;
        public String url;
        public String token;
        public AbsPostJsonStringCb iPostJsonStringCb;
        /**
         * 是否同意处理response相应码
         * 一般来说都是默认统一处理，当后端部分接口返回不规范的时候，需要单独处理，
         */
        public boolean isNormalDeal = true;
        public JSONObject mJSONObject;
        //public LoadHelpView loadHelpView;
    }


    /**
     * 上传单个图片
     *
     * @param requestOption
     */
    public static void postFileCallback(final RequestOption requestOption) {
        //上传单个文件
        OkGo.<String>post(requestOption.url)
                .tag(MyApplication.getApplication())
                .params(requestOption.fileKey, requestOption.paramFile)
                .params(requestOption.params)
                .isMultipart(true)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        if (response.body() != null) {
                            String resp = response.body();
                            // 是否需要 统一的处理
                            if (requestOption.isNormalDeal) {
                                if (unifiedProcessingCode(resp, requestOption)) {
                                    backToSuccessNormal(resp, requestOption);
                                }
                            } else {
                                backToSuccessOriginal(resp, requestOption);
                            }
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        if (response.body() != null) {
                            if (requestOption.isNormalDeal) {//统一处理
                                ToastUtils.show("上传失败！！");
                            }
                        }
                        if (requestOption.iPostJsonStringCb != null) {
                            requestOption.iPostJsonStringCb.onError(response);
                        }
                    }

                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        if (requestOption.iPostJsonStringCb != null) {
                            requestOption.iPostJsonStringCb.onStart(request);
                        }
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        if (requestOption.iPostJsonStringCb != null) {
                            requestOption.iPostJsonStringCb.onFinish();
                        }
                    }
                });
    }

    /**
     * 上传多个图片未测试 1 key 对多图
     *
     * @param requestOption
     */
    public static void postFileMoreCallback(final RequestOption requestOption) {
        //上传多个个文件
        OkGo.<String>post(requestOption.url)
                .params(requestOption.params)
                .addFileParams("file", requestOption.paramFiles)
                .isMultipart(true)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        if (null != response.body()) {
                            String resp = response.body();
                            // 是否需要 统一的处理
                            if (requestOption.isNormalDeal) {
                                if (unifiedProcessingCode(resp, requestOption)) {
                                    backToSuccessNormal(resp, requestOption);
                                }
                            } else {
                                backToSuccessOriginal(resp, requestOption);
                            }
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        if (response.body() != null) {
                            String err = response.body();
                            if (requestOption.isNormalDeal) {//统一处理
                                ToastUtils.show(err);
                            }
                        }
                        if (requestOption.iPostJsonStringCb != null) {
                            requestOption.iPostJsonStringCb.onError(response);
                        }
                    }

                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        if (requestOption.iPostJsonStringCb != null) {
                            requestOption.iPostJsonStringCb.onStart(request);
                        }
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        if (requestOption.iPostJsonStringCb != null) {
                            requestOption.iPostJsonStringCb.onFinish();
                        }
                    }
                });
    }

    /**
     * 上传多个图片未测试 多 key 对多图
     *
     * @param requestOption
     */
    public static void postFileMoreKeyCallback(final RequestOption requestOption) {
        //上传多个个文件
        PostRequest<String> postRequest = OkGo.<String>post(requestOption.url)
                .tag(MyApplication.getApplication())
                .params(requestOption.params);
        if (requestOption.paramFileKeys.size() != requestOption.paramFiles.size()) {
            ToastUtils.show("图片和key不对应");
            return;
        }
        for (int i = 0; i < requestOption.paramFiles.size(); i++) {
            postRequest.params(requestOption.paramFileKeys.get(i), requestOption.paramFiles.get(i));
        }
        postRequest
                .isMultipart(true)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        if (null != response.body()) {
                            String resp = response.body();
                            // 是否需要 统一的处理
                            if (requestOption.isNormalDeal) {
                                if (unifiedProcessingCode(resp, requestOption)) {
                                    backToSuccessNormal(resp, requestOption);
                                }
                            } else {
                                backToSuccessOriginal(resp, requestOption);
                            }
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        if (response.body() != null) {
                            String err = response.body();
                            if (requestOption.isNormalDeal) {//统一处理
                                ToastUtils.show(err);
                            }
                        }
                        if (requestOption.iPostJsonStringCb != null) {
                            requestOption.iPostJsonStringCb.onError(response);
                        }
                    }

                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        if (requestOption.iPostJsonStringCb != null) {
                            requestOption.iPostJsonStringCb.onStart(request);
                        }
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        if (requestOption.iPostJsonStringCb != null) {
                            requestOption.iPostJsonStringCb.onFinish();
                        }
                    }
                });
    }

    /**
     * post请求
     * 以String形式返回数据，以post方式提交,包装这一层，是为了统一处理数据
     */
    public static void postJsonStringCallback(final RequestOption requestOption) {
        OkGo.<String>post(requestOption.url)
                .headers("Content-Type", "application/x-www-form-urlencoded")
                .params(requestOption.params, true)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        if (null != response.body()) {
                            String resp = response.body();
                            Log.e("onSuccess", "url:" + requestOption.url);
                            Log.e("onSuccess", resp);
                            // 是否需要 统一的处理
                            if (requestOption.isNormalDeal) {
                                if (unifiedProcessingCode(resp, requestOption)) {
                                    backToSuccessNormal(resp, requestOption);
                                }
                            } else {
                                backToSuccessOriginal(resp, requestOption);
                            }
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        if (response.body() != null) {
                            Log.e("onError", "url:" + requestOption.url + "body" + response.body());
                            String err = response.body();
                            if (requestOption.isNormalDeal) {//统一处理
                                ToastUtils.show(err);
                            }
                        }
                        if (requestOption.iPostJsonStringCb != null) {
                            requestOption.iPostJsonStringCb.onError(response);
                        }

                    }

                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        if (requestOption.iPostJsonStringCb != null) {
                            requestOption.iPostJsonStringCb.onStart(request);
                        }
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        if (requestOption.iPostJsonStringCb != null) {
                            requestOption.iPostJsonStringCb.onFinish();
                        }
                    }
                });
    }

    /**
     * 返回码的统一处理
     * 当返回 true 时，代表success。
     *
     * @param resp
     * @param requestOption
     * @return
     */
    private static boolean unifiedProcessingCode(String resp, final RequestOption requestOption) {
        if (!TextUtils.isEmpty(resp)) {
            try {
                JSONObject jsonObject = new JSONObject(resp);
                int code = 1;
                if (jsonObject.has("status")) {
                    code = jsonObject.optInt("status");
                }
                String msg = jsonObject.optString("message");
                // 假设返回码0为通过
                switch (code) {
                    case 0://成功
                        return true;
                    case 1://失败
                        ToastUtils.show(msg);
                        requestOption.iPostJsonStringCb.onError(new Response<>());
                        break;
                    case 2://数据为空
                        requestOption.iPostJsonStringCb.onEmpty();
                        break;
                    case 500://重新登陆
//                        Activity activity3 = ActivityManager.getAppManager().currentActivity();
//                        loginAgain(((AppCompatActivity) activity3).getSupportFragmentManager());
                        break;
                    default:
                        ToastUtils.show(msg);
                        break;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }


    /**
     * 某些错误码的出现，应该让程序直接抛到登录页面
     *
     * @param requestOption
     * @param content
     */
    private static void gotoLogin(final RequestOption requestOption, String content) {
        // code...
    }


    /**
     * get请求
     *
     * @param requestOption
     */
    public static void getJsonStringCallback(final RequestOption requestOption) {

        if (requestOption.params != null && requestOption.params.size() > 0) {
            Map<String, String> map = requestOption.params;
            boolean isFirst = true;
            StringBuffer stringBuffer = new StringBuffer();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                if (isFirst) {
                    stringBuffer.append("?" + entry.getKey() + "=" + entry.getValue());
                    isFirst = false;
                } else {
                    stringBuffer.append("&" + entry.getKey() + "=" + entry.getValue());
                }
            }
            requestOption.url = requestOption.url + stringBuffer.toString();
        }


        OkGo.<String>get(requestOption.url)
                // header 参数，如果需要的话
                .headers("Authorization", "本地存储")
                //.tag()
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String resp = response != null ? response.body() : "";
                        if (null != resp) {
                            Log.e("onSuccess", resp);
                            // 是否需要 统一的处理
                            if (requestOption.isNormalDeal) {
                                if (unifiedProcessingCode(resp, requestOption)) {
                                    backToSuccessNormal(resp, requestOption);
                                }
                            } else {
                                backToSuccessOriginal(resp, requestOption);
                            }
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        if (response.body() != null) {
                            String err = response.body().toString();
                        }

                        if (requestOption.iPostJsonStringCb != null) {
                            requestOption.iPostJsonStringCb.onError(response);
                        }

                    }

                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        if (requestOption.iPostJsonStringCb != null) {
                            requestOption.iPostJsonStringCb.onStart(request);
                        }
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        if (requestOption.iPostJsonStringCb != null) {
                            requestOption.iPostJsonStringCb.onFinish();
                        }
                    }
                });
    }

    /**
     * 请求success 通用处理
     *
     * @param resp
     * @param requestOption
     */
    private static void backToSuccessNormal(String resp, RequestOption requestOption) {

        if (requestOption.iPostJsonStringCb != null) {
            try {
                JSONObject jsonObject = new JSONObject(resp);
                String data = null;
                if (jsonObject.has("data")) {
                    data = jsonObject.getString("data");
                }
                requestOption.iPostJsonStringCb.onSuccess(resp, data);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 请求成功，原生字符串处理
     *
     * @param resp
     * @param requestOption
     */
    private static void backToSuccessOriginal(String resp, RequestOption requestOption) {

        if (requestOption.iPostJsonStringCb != null) {
            requestOption.iPostJsonStringCb.onSuccess(resp, resp);
        }
    }

}
