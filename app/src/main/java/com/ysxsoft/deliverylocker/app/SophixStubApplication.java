package com.ysxsoft.deliverylocker.app;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Keep;

import com.taobao.sophix.PatchStatus;
import com.taobao.sophix.SophixApplication;
import com.taobao.sophix.SophixEntry;
import com.taobao.sophix.SophixManager;

import java.util.ArrayList;

/**
 * Sophix入口类，专门用于初始化Sophix，不应包含任何业务逻辑。
 * 此类必须继承自SophixApplication，onCreate方法不需要实现。
 * 此类不应与项目中的其他类有任何互相调用的逻辑，必须完全做到隔离。
 * AndroidManifest中设置application为此类，而SophixEntry中设为原先Application类。
 * 注意原先Application里不需要再重复初始化Sophix，并且需要避免混淆原先Application类。
 * 如有其它自定义改造，请咨询官方后妥善处理。
 */
public class SophixStubApplication extends SophixApplication {
    private final String TAG = "SophixStubApplication";

    private final String RSA = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQDhZe8RFBxtmfwcF8HrqY0A6Wzf6067fryL4cqHxd7n5bisRcvaCuWXAs0VakVNgWkc87xPBDT83v2W89+ooTXV5lp0afDeVwqAfE1WbBbLzwWheZXX9zd/uSJtdjX4+t/qVFf9zGxUJtCqmSAga1guzVQF0My4fW+dqEdGFnzmYHxEu5mvW9DyvsRN0dGw++GBQQICNi2A1JGKr09UZ8ezaxQm/k44RCQ09GlK+aLlSXJhpd4GDRzXOZcUgFULHlftprFjgmZUF4TeLOtKO06EOVk7PxyFyt2C8+MpdKpMNRXMVO5nLdmcRdGx9hXlxPC2/Zf/iOhFdhZJ5Po0JXFLAgMBAAECggEAGeJAz+9FTHjQcNFxnYR9AsvLivNUuUdWk6G8Rlc8v9irmmzxefqiXvMdZgYsKzhfP8nlqBVNmHRrHrimyJCqxAOOA6xe0smgFR0xLGmBPKprdR2nTYPARdSW1Ycrr4nvyNMiv6yIDCng8JZhEBgDqmw95+UQfWc6OBqpBK10bmgS6jKfxdc3kCUEzgyQT/sLt71c7LpWetfApLG0q79Kq/naTbtVoIYlrOL6bD0sT3+ZC4WNAMZEo5nfj8QMg+wq7u2U9bpzcidqTnAkCPxkMxP+wxJoE7LRitsUlp39MGKXLTDwzt7glPWKCJqJHcmFE02UWqT40Zzsoz6sshIzEQKBgQD4Dxmayi1CIaMn/COdYB6yZmJsYNLmxLXVZ77cjcIvWi1xH41CliKW+9dde97KLN1g6TLu2WrfUgOmwuEiCJKM7Ua8WNacjzQ2xGv+olQdnoOeg3/OUVpaNSeOM/J1oOLPP0VVeQlBNkf7jYgRNGOb28MQ2GdOHzIZjPY3PWEJXwKBgQDonR+U4yzgnJF5yoZTdJodqY+204010rLJl2VqOQII5YCjP06kxokFDumAk5d12Ui94/xFSlHqdYvSdzAp06ZLvx2hhfaDm9YF/i+YMPh/oIQSgl8SIUrZgpGj4pOPaNzeihBHGHgFDJhM/L+YyPcbI2MWAo24rOhF7vVuPX0jlQKBgQCXTW6KsaLlYWsJKov2o/hVTIngHTbGt1lJr5wFHxpAk7p/84TQBo9WlIoKZVHA85icUeFVOpwrVvUPNHLptVzeRKQOoglVOSvZ8/kDObQg1NznRMIh8BEyUJWt8RWElfuB+c+qkMcKVbeWQf+qrs/K80KJ9dUdG34Ng6Nykier9QKBgQDAh+xv62hlsLGoQLzVrpkxG/tbs9YZSn+445qJRQ4uFudWNzv8nmcizKjwRzkBzgqrQVy4bT9H9Gr6lLREqm3mHBWGb78OR5np8+2o5j+IQ2+NcmMGOpAdYDjzkMHZ3U2S1GuMrPAKHdahQkHDT/NXfv6wZQSwak0GNTbnO/Wr0QKBgQDTs26FIwc4oumzzxNYcNFMLPBev0dZ9Ow69frhcq6PgfeTCUprqa+Q/YnZPQX4MdE1/edFFpD8jQuYUwZd2B+V0q7E/9KjhuNC77R77vzMYgrEpmGhw8N3TSEB5VasCtCppCy0xht84DgkC38+7vtkmhpD1rx7lsPmvgsKvM8+Zg==";

    // 此处SophixEntry应指定真正的Application，并且保证RealApplicationStub类名不被混淆。
    @Keep
    @SophixEntry(MyApplication.class)
    static class RealApplicationStub {
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
//         如果需要使用MultiDex，需要在此处调用。
//        MultiDex.install(this);
        initSophix();
    }

    private void initSophix() {
        String appVersion = "0.0.0";
        try {
            appVersion = this.getPackageManager()
                    .getPackageInfo(this.getPackageName(), 0)
                    .versionName;
        } catch (Exception e) {
        }
        final SophixManager instance = SophixManager.getInstance();
        Log.e(TAG, "initSophix");
        instance.setContext(this)
                .setAppVersion(appVersion)
                .setSecretMetaData("28125467-1", "30bf3c00819dc7bbe930d4896937474d", RSA)
                .setEnableDebug(true)//是否调试模式, 调试模式下会输出日志以及不进行补丁签名校验. 线下调试此参数可以设置为true,发布必须调整为false否则不做签名验证
                .setEnableFullLog()
                .setAesKey(null)
                .setPatchLoadStatusStub((mode, code, info, handlePatchVersion) -> {
                    // mode: 无实际意义, 为了兼容老版本, 默认始终为0
                    // code: 补丁加载状态码, 详情查看PatchStatus类说明
                    // info: 补丁加载详细说明
                    // handlePatchVersion: 当前处理的补丁版本号, 0:无 -1:本地补丁 其它:后台补丁
                    if (code == PatchStatus.CODE_LOAD_SUCCESS) {// 表明补丁加载成功
                        Log.i(TAG, "sophix load patch success!");
                    } else if (code == PatchStatus.CODE_LOAD_RELAUNCH) {// 表明新补丁生效需要重启. 开发者可提示用户或者强制重启;
                        // 如果需要在后台重启，建议此处用SharePreference保存状态。
                        SophixManager.getInstance().killProcessSafely();
                        Log.i(TAG, "sophix preload patch success. restart app to make effect.");
                    } else {
                        // 其它错误信息, 查看PatchStatus类说明

                    }
                }).initialize();
    }
}
