package com.ysxsoft.deliverylocker.utils;

import android.speech.tts.TextToSpeech;
import android.text.TextUtils;
import android.util.Log;

import com.ysxsoft.deliverylocker.app.MyApplication;


public class TtsUtil {

    private static TtsUtil ttsUtil;

    private TtsUtil() {
        init(null);
    }

    public static TtsUtil getInstance(){
        if (ttsUtil == null){
            synchronized (TtsUtil.class){
                if (ttsUtil == null){
                    ttsUtil = new TtsUtil();
                }
            }
        }
        return ttsUtil;
    }

    //定义一个tts对象
    private TextToSpeech tts;
    private boolean initSuccess;



    /**
     * 初始化引擎
     */
    private void  init(String text){
        tts = new TextToSpeech(MyApplication.getApplication(), status -> {
            // 判断是否转化成功
            if (status == TextToSpeech.SUCCESS){
                initSuccess = true;
                //默认设定语言为中文，原生的android貌似不支持中文。
                if (text != null && !TextUtils.isEmpty(text)){
                    Log.e("speak", text);
                    tts.speak(text,  TextToSpeech.QUEUE_FLUSH, null);
                }
            }
        });
    }

    /**
     *
     * @param text
     */
    public void speak(String text){
        if (initSuccess){
            Log.e("speak", text);
            tts.speak(text,  TextToSpeech.QUEUE_FLUSH, null);
        }else {
            init(text);
        }
    }
}
