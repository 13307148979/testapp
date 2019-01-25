package com.teplot.testapp.ui.test1;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.teplot.testapp.R;
import com.teplot.testapp.apps.AppData;
import com.teplot.testapp.base.BaseActivity;
import com.teplot.testapp.been.result.FanYiDataObj;
import com.teplot.testapp.common.SingleChoiceListDlg;
import com.teplot.testapp.common.TopBar;
import com.teplot.testapp.utils.JsonUtil;
import com.teplot.testapp.utils.MD5;
import com.teplot.testapp.utils.SortUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;


public class TestActivity extends BaseActivity {

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
                //showShortToast("修改登录密码");
                startActivity(TestActivity1.class);
                break;
            case R.id.ll_2:
                // showShortToast("修改交易密码");
                startActivity(TestActivity2.class);
                break;

        }
    }
    private TextView text1,text2;
    private void initView(){
        text1 = (TextView) findViewById(R.id.text1);
        text2 = (TextView) findViewById(R.id.text2);

        text1.setText("文本翻译");
        text2.setText("图片翻译");
        //initView2();
    }
}
