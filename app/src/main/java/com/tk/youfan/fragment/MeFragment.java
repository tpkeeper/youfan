package com.tk.youfan.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tk.youfan.R;
import com.tk.youfan.activity.LoginActivity;
import com.tk.youfan.activity.MainActivity;
import com.tk.youfan.base.BaseFragment;
import com.tk.youfan.utils.LogUtil;
import com.tk.youfan.utils.zxing.CommonScanActivity;
import com.tk.youfan.utils.zxing.utils.Constant;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UmengTool;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

/**
 * 作者：tpkeeper on 2016/9/28 00:07
 * 微信：lzy1056883354
 * QQ号：1056883354
 * 作用：xxxx
 */
public class MeFragment extends BaseFragment {
    LinearLayout scan;
    ImageView img_head_photo;
    TextView tv_name;
    private UMShareAPI mShareAPI;
    ImageView peirizhi;
    @Override
    public View initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.me_fragment, null);
        scan = (LinearLayout) view.findViewById(R.id.scan);
        img_head_photo = (ImageView) view.findViewById(R.id.img_head_photo);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        peirizhi = (ImageView) view.findViewById(R.id.peirizhi);
        initListener();
        return view;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        mShareAPI = UMShareAPI.get(mContext);
//        boolean authorize = mShareAPI.isAuthorize((MainActivity) mContext, SHARE_MEDIA.QQ);
//        if (authorize) {
//            mShareAPI.getPlatformInfo((MainActivity) mContext, SHARE_MEDIA.QQ, umAuthListener);
//        }else {
//            LogUtil.e("33333333333333333333333333333333");
//        }
    }

    private void initListener() {
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CommonScanActivity.class);
                intent.putExtra(Constant.REQUEST_SCAN_MODE, Constant.REQUEST_SCAN_MODE_ALL_MODE);
                startActivity(intent);
            }
        });
        img_head_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLoginActivity();

            }
        });
        peirizhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShareAPI.deleteOauth((MainActivity) mContext, SHARE_MEDIA.QQ, umdelAuthListener);
            }
        });
    }

    private void startLoginActivity() {
        Intent intent = new Intent(mContext,LoginActivity.class);
        startActivityForResult(intent,Activity.RESULT_FIRST_USER);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==0) {
            tv_name.setText(data.getStringExtra("screen_name"));
            Glide.with(mContext)
                    .load(data.getStringExtra("profile_image_url"))
                    .into(img_head_photo);
        }
    }

    /** delauth callback interface**/
    private UMAuthListener umdelAuthListener = new UMAuthListener() {
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            Toast.makeText(mContext, "delete Authorize succeed", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText( mContext, "delete Authorize fail", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText( mContext, "delete Authorize cancel", Toast.LENGTH_SHORT).show();
        }
    };


}
