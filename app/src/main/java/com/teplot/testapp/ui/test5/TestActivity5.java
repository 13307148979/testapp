package com.teplot.testapp.ui.test5;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.teplot.testapp.R;
import com.teplot.testapp.base.BaseActivity;
import com.teplot.testapp.common.TopBar;
import com.teplot.testapp.ui.test1.TestActivity1;
import com.teplot.testapp.ui.test1.TestActivity2;


public class TestActivity5 extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test1_0);
        setImmersionBarNon();
        initHeadView();
        initView();
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
                startActivity(TestActivity51.class);
                break;
            case R.id.ll_2:
                // showShortToast("其他OCR");
                startActivity(TestActivity52.class);
                break;

        }
    }
    private TextView text1,text2;
    private void initView(){
        text1 = (TextView) findViewById(R.id.text1);
        text2 = (TextView) findViewById(R.id.text2);

        text1.setText("身份证OCR");
        text2.setText("其他OCR");
        //initView2();
    }
}
