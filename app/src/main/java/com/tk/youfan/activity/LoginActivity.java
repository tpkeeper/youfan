package com.tk.youfan.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tk.youfan.R;
import com.tk.youfan.utils.LogUtil;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class LoginActivity extends Activity {
    Button btn_login_qq;
    private UMShareAPI mShareAPI;
    Button btn_login_wx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mShareAPI = UMShareAPI.get(this);
        btn_login_qq = (Button) findViewById(R.id.btn_login_qq);
        btn_login_wx = (Button)findViewById(R.id.btn_login_wx);
        initListener();
    }

    private void initListener() {
        btn_login_qq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mShareAPI.doOauthVerify(LoginActivity.this, SHARE_MEDIA.QQ, doumAuthListener);

            }
        });
        btn_login_wx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShareAPI.doOauthVerify(LoginActivity.this, SHARE_MEDIA.WEIXIN, doumAuthListener);
            }
        });
    }

    private UMAuthListener doumAuthListener = new UMAuthListener() {
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            Toast.makeText(LoginActivity.this, "Authorize succeed", Toast.LENGTH_SHORT).show();
            LogUtil.e("dpAuthorize succeed");
            mShareAPI.getPlatformInfo(LoginActivity.this, SHARE_MEDIA.QQ, umAuthListener);
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText(LoginActivity.this, "Authorize fail", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText(LoginActivity.this, "Authorize cancel", Toast.LENGTH_SHORT).show();
        }
    };

    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            Toast.makeText(LoginActivity.this, "infoAuthorize succeed", Toast.LENGTH_SHORT).show();
//            String screen_name = data.get("screen_name");
//            String profile_image_url = data.get("profile_image_url");
//            Intent intent = new Intent();
//            intent.putExtra("screen_name", screen_name);
//            intent.putExtra("profile_image_url", profile_image_url);
//            setResult(0, intent);
//            finish();
            Set<String> strings = data.keySet();
            Iterator<String> iterator = strings.iterator();
            while (iterator.hasNext()){
                String next = iterator.next();
                String s = data.get(next);
                LogUtil.e(next+":"+s);
            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText(LoginActivity.this, "Authorize fail", Toast.LENGTH_SHORT).show();
            LogUtil.e("authorize failed");
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText(LoginActivity.this, "Authorize cancel", Toast.LENGTH_SHORT).show();
        }
    };


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(LoginActivity.this).HandleQQError(LoginActivity.this, requestCode, umAuthListener);
        mShareAPI.onActivityResult(requestCode, resultCode, data);
    }

}
