package com.teplot.testapp.ui.test7;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.teplot.testapp.R;
import com.teplot.testapp.base.BaseActivity;
import com.teplot.testapp.common.TopBar;
import com.teplot.testapp.ui.test1.TestActivity1;
import com.teplot.testapp.ui.test1.TestActivity2;


public class TestActivity760 extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test760);
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
                //showShortToast("人脸验证");
                startActivity(TestActivityYanZheng.class);
                break;
            case R.id.ll_2:
                 showShortToast("人脸搜索");
                //startActivity(TestActivity2.class);
                break;
            case R.id.ll_3:
                // showShortToast("修改交易密码");
                startActivity(TestActivity76.class);
                break;

        }
    }
}
