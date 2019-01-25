package com.teplot.testapp.ui.test7;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.teplot.testapp.R;
import com.teplot.testapp.base.BaseActivity;
import com.teplot.testapp.common.TopBar;


public class TestActivity76 extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test76);
        setImmersionBarNon();
        initHeadView();
    }
    private void initHeadView() {
        mTopBar = new TopBar(this);
        mTopBar.setPathName("人脸库");
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_1:
               // showShortToast("身份证OCR");
                showDataActivity(0);
                break;
            case R.id.ll_2:
                showDataActivity(1);
              //  startActivity(TestActivity72.class);
                break;
            case R.id.ll_3:
                showDataActivity(2);
                // showShortToast("身份证OCR");
                // startActivity(TestActivity73.class);
                break;
            case R.id.ll_4:
                showDataActivity(3);
               // startActivity(TestActivity74.class);
                break;
            case R.id.ll_5:
                showDataActivity(4);
                // showShortToast("身份证OCR");
              // startActivity(TestActivity75.class);
                break;
            case R.id.iv_add:
                 showShortToast("添加");
                 startActivity(TestActivity76Add.class);
                break;
            case R.id.iv_sousuo:
                // showShortToast("搜索");
                // startActivity(TestActivity75.class);
                break;

        }
    }

    private void showDataActivity(int group_id){
        Intent intent = new Intent(TestActivity76.this,TestActivity76List.class);
        intent.putExtra("group_id",group_id);
        startActivity(intent);
    }

}
