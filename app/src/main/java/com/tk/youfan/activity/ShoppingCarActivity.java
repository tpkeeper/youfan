package com.tk.youfan.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.FrameLayout;

import com.tk.youfan.R;
import com.tk.youfan.fragment.PurchaseFragment;

public class ShoppingCarActivity extends FragmentActivity {
    FrameLayout frm_content_shoppingcar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_shopping_car);
        frm_content_shoppingcar = (FrameLayout) findViewById(R.id.frm_content_shoppingcar);
        getSupportFragmentManager().beginTransaction().add(R.id.frm_content_shoppingcar,new PurchaseFragment())
                .commit();
    }
}
