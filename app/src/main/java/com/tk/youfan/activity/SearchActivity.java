package com.tk.youfan.activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;

import com.tk.youfan.R;

public class SearchActivity extends Activity {
ImageButton top_cebian_main;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_search);
        top_cebian_main = (ImageButton)findViewById(R.id.top_cebian_main);
        top_cebian_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
