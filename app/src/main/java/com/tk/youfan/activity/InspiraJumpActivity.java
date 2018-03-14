package com.tk.youfan.activity;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.tk.youfan.R;
import com.tk.youfan.utils.zxing.utils.BitmapUtil;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.utils.BitmapUtils;
import com.umeng.socialize.utils.Log;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InspiraJumpActivity extends Activity {


    @Bind(R.id.imgb_back_jump)
    ImageButton imgbBackJump;
    @Bind(R.id.tv_title_jump)
    TextView tvTitleJump;
    @Bind(R.id.imgb_share_jump)
    ImageButton imgbShareJump;
    @Bind(R.id.webview_jump)
    WebView webviewJump;
    private String url;
    private WebSettings webSettings;
    private String title_later ="";
    private String title ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspir_jump);
        ButterKnife.bind(this);
        getData();
    }
    private void getData() {
        url = getIntent().getStringExtra("url");
        title_later = getIntent().getStringExtra("title_later");
        title = getIntent().getStringExtra("title");
        tvTitleJump.setText(title);
        //设置支持javaScript
        webSettings = webviewJump.getSettings();
        //设置支持javaScript
        webSettings.setJavaScriptEnabled(true);
        //设置双击变大变小
        webSettings.setUseWideViewPort(true);
        //增加缩放按钮
        webSettings.setBuiltInZoomControls(true);
        //设置文字大小
//        webSettings.setTextSize(WebSettings.TextSize.NORMAL);
        webSettings.setTextZoom(100);
        //不让从当前网页跳转到系统的浏览器中

        webviewJump.setWebViewClient(new WebViewClient() {
            //当加载页面完成的时候回调
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
//                pbLoading.setVisibility(View.GONE);
                tvTitleJump.setText(title_later);
            }
        });
        webviewJump.loadUrl(url);

    }

    @OnClick({R.id.imgb_back_jump, R.id.imgb_share_jump})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgb_back_jump:
                break;
            case R.id.imgb_share_jump:
//                new ShareAction(InspiraJumpActivity.this).setPlatform(SHARE_MEDIA.QQ)
//                        .withText("hello")
//                        .setCallback(umShareListener)
//                        .share();
                new ShareAction(InspiraJumpActivity.this).withText(title_later)
                        .setDisplayList(SHARE_MEDIA.SINA,SHARE_MEDIA.QQ,SHARE_MEDIA.WEIXIN,SHARE_MEDIA.MORE)
                        .withTargetUrl(url)
                        .withMedia(new UMImage(InspiraJumpActivity.this,R.drawable.default100))
                        .setCallback(umShareListener).open();

                break;
        }
    }
    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Log.d("plat","platform"+platform);

            Toast.makeText(InspiraJumpActivity.this, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(InspiraJumpActivity.this,platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
            if(t!=null){
                Log.d("throw","throw:"+t.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(InspiraJumpActivity.this,platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };
}
