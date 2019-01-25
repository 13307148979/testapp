package com.teplot.testapp.ui.test2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.teplot.testapp.R;
import com.teplot.testapp.adapter.Test0YuYiListAdapter;
import com.teplot.testapp.adapter.Test2XianLiaoListAdapter;
import com.teplot.testapp.apps.AppData;
import com.teplot.testapp.base.BaseActivity;
import com.teplot.testapp.been.details.XianLiaoData;
import com.teplot.testapp.been.details.YuYIData;
import com.teplot.testapp.been.result.FanYiListDataObj;
import com.teplot.testapp.been.result.XianLiaoDataObj;
import com.teplot.testapp.been.result.YuYiQingGanDataObj;
import com.teplot.testapp.common.TopBar;
import com.teplot.testapp.utils.JsonUtil;
import com.teplot.testapp.utils.MD5;
import com.teplot.testapp.utils.SoftHideKeyBoardUtil;
import com.teplot.testapp.utils.SortUtils;
import com.teplot.testapp.utils.StringUtil;
import com.teplot.testapp.utils.Utils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;


public class TestActivity2 extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);
        setImmersionBarNon();
        SoftHideKeyBoardUtil.assistActivity(TestActivity2.this);
        initHeadView();
        initView();
        initListView();
    }
    private String title;
    private void initHeadView() {
        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        mTopBar = new TopBar(this);
        mTopBar.setPathName(title);
    }
    private ListView listview_main;
    private Test2XianLiaoListAdapter adapterList;
    private void initListView(){
        listview_main = (ListView) findViewById(R.id.listview_main);
        adapterList = new Test2XianLiaoListAdapter(TestActivity2.this,new ArrayList<XianLiaoData>());
        listview_main.setAdapter(adapterList);
    }

    private TextView content_num_tv;
    private EditText contentTextTv;
    private long session;
    private void initView(){
        session = System.currentTimeMillis();
        initView2();
    }
    private int maxNum = 100;
    private void initView2(){

        //灾情、隐患内容
        contentTextTv = (EditText) findViewById(R.id.content_text_tv);
        content_num_tv = (TextView) findViewById(R.id.content_num_tv);
        content_num_tv.setText(0+"/"+maxNum);
        Utils.showTextViewNUM(content_num_tv, contentTextTv, maxNum);
        // Utils.showEditViewInScallview(contentTextTv);
    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit_send_bt:
              //  adapterList.removeAllItem();
                if (!StringUtil.isEmpty(contentTextTv.getText().toString().trim())){
                    getDataDuiHua();
                }else {
                    showShortCenterToast("请输入相关内容");
                }
                break;
        }
    }

    private  String question;
    private void getDataDuiHua(){
        //showProdialog(null, "意图分析中,请稍后...", null);
        long totalMilliSeconds = System.currentTimeMillis();
        long totalSeconds = totalMilliSeconds / 1000;

        question = contentTextTv.getText().toString().trim();
        contentTextTv.setText("");
        Map<String, String> map = new HashMap<String, String>();
        map.put("app_id", AppData.TENCENT_AI_APP_ID);
        map.put("time_stamp", totalSeconds+"");
        // map.put("type","1");
        map.put("nonce_str", "fa577ce340859f9fe");
        map.put("question",question);
        map.put("session",session+"");


        String url = SortUtils.formatUrlParam(map, "utf-8", true);

        String url2 = url+"&app_key="+AppData.TENCENT_AI_APP_KEY;
        showOut("====getDataFenCi=url=="+url2);

        String md5Url = MD5.stringToMD5(url2);

        // showOut("====getDataFenCi=md5Url=="+md5Url.toUpperCase());
        // showOut("====getDataFanYi=sourceId=="+sourceId+"====targetId=====>"+targetId);
        OkHttpUtils
                .post()
                .url(AppData.URL_NLP_TEXTCHAT)
                .addParams("app_id",AppData.TENCENT_AI_APP_ID)
                .addParams("time_stamp",totalSeconds+"")
                .addParams("nonce_str","fa577ce340859f9fe")
                .addParams("sign",md5Url.toUpperCase())

                .addParams("session",session+"")
                .addParams("question",question)

                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int i) {
                       // hideProdialog();
                        showShortToast(e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int i) {
                        //hideProdialog();
                        showOut("-----获取到的json数据1--activity_test0--getDataYiTu----"+response);
                        XianLiaoDataObj result = (XianLiaoDataObj) JsonUtil.jsonToBean(response,XianLiaoDataObj.class);
                        if (result.getRet()==0){

                            String answer = result.getData().getAnswer();
                            XianLiaoData data = new XianLiaoData(answer,question);

                            adapterList.addItem(data);
                            listview_main.setSelection(adapterList.getCount()-1);
                        }else {
                            showShortCenterToast(result.getMsg());
                        }
                    }
                });
    }
}
