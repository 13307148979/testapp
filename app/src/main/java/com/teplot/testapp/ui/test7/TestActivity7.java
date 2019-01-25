package com.teplot.testapp.ui.test7;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.teplot.testapp.R;
import com.teplot.testapp.base.BaseActivity;
import com.teplot.testapp.common.TopBar;
import com.teplot.testapp.ui.test5.TestActivity51;
import com.teplot.testapp.ui.test5.TestActivity52;


public class TestActivity7 extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test7);
        setImmersionBarNon();
        initHeadView();
    }

    private String title;
    private void initHeadView() {
        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        mTopBar = new TopBar(this);
        mTopBar.setPathName(title);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_1:
               // showShortToast("身份证OCR");
               startActivity(TestActivity71.class);
                break;
            case R.id.ll_2:
                startActivity(TestActivity72.class);
                break;
            case R.id.ll_3:
                // showShortToast("身份证OCR");
                 startActivity(TestActivity73.class);
                break;
            case R.id.ll_4:
                startActivity(TestActivity74.class);
                break;
            case R.id.ll_5:
                // showShortToast("身份证OCR");
               startActivity(TestActivity75.class);
                break;
            case R.id.ll_7:
                // showShortToast("身份证OCR");
                 startActivity(TestActivity760.class);
                break;
        }
    }
}
