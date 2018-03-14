package com.tk.youfan.activity;

import android.app.Activity;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechUtility;
import com.tk.youfan.R;
import com.tk.youfan.utils.JsonParser;
import com.tk.youfan.utils.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class SpeachActivity extends Activity {
    ImageView img_circle;
    private ImageView img_circle2;
    private ImageView img_circle3;
    ImageView img_mic;
    private ImageView img_circle4;
    private ImageView img_top_close;
    TextView tv_click;
    TextView tv_top;
    TextView tv_voice;
    private SpeechRecognizer mIat;
    private RecognizerListener mRecoListener;
    private HashMap<String, String> mIatResults = new LinkedHashMap<String, String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_speach);
        initView();
        initListener();
        initSpeach();
    }

    private void initListener() {
        img_mic.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        //动画
                        img_circle.setVisibility(View.VISIBLE);
                        img_circle2.setVisibility(View.VISIBLE);
                        img_circle3.setVisibility(View.VISIBLE);
                        img_circle4.setVisibility(View.VISIBLE);
                        initAnim();
                        //文字
                        tv_click.setText("松开搜索");
                        tv_top.setText("正在倾听...");

                        // 3.开始听写
                        mIat.startListening(mRecoListener);
                        break;
                    case MotionEvent.ACTION_UP:
                        //动画
                        clearAnim();
                        img_circle.setVisibility(View.GONE);
                        img_circle2.setVisibility(View.GONE);
                        img_circle3.setVisibility(View.GONE);
                        img_circle4.setVisibility(View.GONE);
                        tv_voice.setVisibility(View.GONE);
                        mIat.stopListening();
                        tv_click.setText("长按说话");
                        tv_top.setText("大家都在搜");
                        break;
                }
                return true;
            }
        });
        img_top_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpeachActivity.this.finish();
            }
        });
    }

    private void initSpeach() {

        //1.创建SpeechRecognizer对象，第二个参数：本地识别时传InitListener
        mIat = SpeechRecognizer.createRecognizer(this, null);
        //2.设置听写参数，详见SDK中《MSC Reference Manual》文件夹下的SpeechConstant类
        mIat.setParameter(SpeechConstant.DOMAIN, "iat");
        mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        mIat.setParameter(SpeechConstant.ACCENT, "mandarin ");

        //保存音频文件到本地（有需要的话）   仅支持pcm和wav，且需要自行添加读写SD卡权限
//        mIat.setParameter(SpeechConstant.ASR_AUDIO_PATH, Environment.getExternalStorageDirectory() + "/msc/mIat.wav");
        //听写监听器
        //听写结果回调接口(返回Json格式结果，用户可参见附录13.1)；
// 一般情况下会通过onResults接口多次返回结果，完整的识别内容是多次结果的累加；
// 关于解析Json的代码可参见Demo中JsonParser类；
// isLast等于true时会话结束。
//会话发生错误回调接口
//打印错误码描述
//开始录音
//    volume音量值0~30，data音频数据
//结束录音
//扩展用接口
        mRecoListener = new RecognizerListener() {
            //听写结果回调接口(返回Json格式结果，用户可参见附录13.1)；
            // 一般情况下会通过onResults接口多次返回结果，完整的识别内容是多次结果的累加；
            // 关于解析Json的代码可参见Demo中JsonParser类；
            // isLast等于true时会话结束。
            public void onResult(RecognizerResult results, boolean isLast) {
                LogUtil.e("result:" + results.getResultString());
                tv_voice.setVisibility(View.GONE);
                printResult(results);
            }

            //会话发生错误回调接口
            public void onError(SpeechError error) {
                //打印错误码描述
                LogUtil.e("error:" + error.getPlainDescription(true));
                tv_top.setText(error.getPlainDescription(true));
                tv_voice.setVisibility(View.VISIBLE);
            }

            //开始录音
            public void onBeginOfSpeech() {
                LogUtil.e("speach start");
            }

            //    volume音量值0~30，data音频数据
            public void onVolumeChanged(int volume, byte[] data) {
            }

            //结束录音
            public void onEndOfSpeech() {
                LogUtil.e("speach end ");
            }

            //扩展用接口
            public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
            }
        };

    }
    private void printResult(RecognizerResult results) {
        String text = JsonParser.parseIatResult(results.getResultString());

        String sn = null;
        // 读取json结果中的sn字段
        try {
            JSONObject resultJson = new JSONObject(results.getResultString());
            sn = resultJson.optString("sn");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mIatResults.put(sn, text);

        StringBuffer resultBuffer = new StringBuffer();
        for (String key : mIatResults.keySet()) {
            resultBuffer.append(mIatResults.get(key));
        }

        tv_top.setText(resultBuffer.toString());
    }
    private void initView() {
        img_mic = (ImageView) findViewById(R.id.img_mic);
        img_circle = (ImageView) findViewById(R.id.img_circle);
        img_circle2 = (ImageView) findViewById(R.id.img_circle2);
        img_circle3 = (ImageView) findViewById(R.id.img_circle3);
        img_circle4 = (ImageView) findViewById(R.id.img_circle4);
        img_top_close = (ImageView) findViewById(R.id.img_top_close);
        tv_click = (TextView) findViewById(R.id.tv_click);
        tv_top = (TextView) findViewById(R.id.tv_top);
        tv_voice = (TextView) findViewById(R.id.tv_voice);
    }

    private void initAnim() {
        ScaleAnimation scaleAnimation = (ScaleAnimation) AnimationUtils.loadAnimation(this, R.anim.speach_mic_anim);
        ScaleAnimation scaleAnimation2 = (ScaleAnimation) AnimationUtils.loadAnimation(this, R.anim.speach_mic_anim2);
        ScaleAnimation scaleAnimation3 = (ScaleAnimation) AnimationUtils.loadAnimation(this, R.anim.speach_mic_anim3);
        Animation scaleAnimation4 = AnimationUtils.loadAnimation(this, R.anim.speach_mic_anim4);
        img_circle.startAnimation(scaleAnimation);
        img_circle2.startAnimation(scaleAnimation2);
        img_circle3.startAnimation(scaleAnimation3);
        img_circle4.startAnimation(scaleAnimation4);
    }

    private void clearAnim() {
        img_circle.clearAnimation();
        img_circle2.clearAnimation();
        img_circle3.clearAnimation();
        img_circle4.clearAnimation();

    }
}
