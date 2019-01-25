package com.teplot.testapp.ui.test0;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.teplot.testapp.R;
import com.teplot.testapp.adapter.Test0YuYiListAdapter;
import com.teplot.testapp.apps.AppData;
import com.teplot.testapp.base.BaseActivity;
import com.teplot.testapp.been.details.FanYiData;
import com.teplot.testapp.been.details.YuYIData;
import com.teplot.testapp.been.result.FanYiDataObj;
import com.teplot.testapp.been.result.FanYiListDataObj;
import com.teplot.testapp.been.result.YuYiQingGanDataObj;
import com.teplot.testapp.common.SingleChoiceListDlg;
import com.teplot.testapp.common.TopBar;
import com.teplot.testapp.utils.JsonUtil;
import com.teplot.testapp.utils.MD5;
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


public class TestActivity0 extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test0);
        setImmersionBarNon();
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
    private Test0YuYiListAdapter adapterList;
    private void initListView(){
        listview_main = (ListView) findViewById(R.id.listview_main);
        adapterList = new Test0YuYiListAdapter(TestActivity0.this,new ArrayList<YuYIData>());
        listview_main.setAdapter(adapterList);
    }

    private TextView out_put_tv,content_num_tv;
    private EditText contentTextTv;
    private void initView(){
        out_put_tv = (TextView) findViewById(R.id.out_put_tv);
        initView2();
    }
    private int maxNum = 50;
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
                if (!StringUtil.isEmpty(contentTextTv.getText().toString().trim())){
                    adapterList.removeAllItem();
                    getDataQinGan();
                }else {
                    showShortCenterToast("请输入相关内容");
                }
                break;
        }
    }

    private String qingGan;
    private void getDataQinGan(){
        showProdialog(null, "情感分析中,请稍后...", null);
        long totalMilliSeconds = System.currentTimeMillis();
        long totalSeconds = totalMilliSeconds / 1000;

        String text = contentTextTv.getText().toString().trim();

        Map<String, String> map = new HashMap<String, String>();
        map.put("app_id", AppData.TENCENT_AI_APP_ID);
        map.put("time_stamp", totalSeconds+"");
       // map.put("type","1");
        map.put("nonce_str", "fa577ce340859f9fe");
        map.put("text",text);

        String url = SortUtils.formatUrlParam(map, "utf-8", true);

        String url2 = url+"&app_key="+AppData.TENCENT_AI_APP_KEY;
        showOut("====getDataFenCi=url=="+url2);

        String md5Url = MD5.stringToMD5(url2);

       // showOut("====getDataFenCi=md5Url=="+md5Url.toUpperCase());
       // showOut("====getDataFanYi=sourceId=="+sourceId+"====targetId=====>"+targetId);
        OkHttpUtils
                .post()
                .url(AppData.URL_NLP_TEXTPOLAR)
                .addParams("app_id",AppData.TENCENT_AI_APP_ID)
                .addParams("time_stamp",totalSeconds+"")
                .addParams("nonce_str","fa577ce340859f9fe")
                .addParams("sign",md5Url.toUpperCase())
               // .addParams("type","1")
                .addParams("text",text)

                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int i) {
                        hideProdialog();
                        showShortToast(e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int i) {
                        hideProdialog();
                        showOut("-----获取到的json数据1--activity_test0--getDataFanYi----"+response);
                        YuYiQingGanDataObj result = (YuYiQingGanDataObj) JsonUtil.jsonToBean(response,YuYiQingGanDataObj.class);
                        if (result.getRet()==0){
                            getDataYiTu();
                            int polar = result.getData().getPolar();
                            qingGan = "情感描述："+ StringUtil.getTypePolarString(polar);
                            //Util.showResultTextDialog(TestActivity1.this, target_text, "文本翻译数据");
                           // out_put_tv.setText(qingGan);
                        }else {
                            showShortCenterToast(result.getMsg());
                        }
                    }
                });
    }

    private void getDataYiTu(){
        showProdialog(null, "意图分析中,请稍后...", null);
        long totalMilliSeconds = System.currentTimeMillis();
        long totalSeconds = totalMilliSeconds / 1000;

        String text = contentTextTv.getText().toString().trim();

        Map<String, String> map = new HashMap<String, String>();
        map.put("app_id", AppData.TENCENT_AI_APP_ID);
        map.put("time_stamp", totalSeconds+"");
        // map.put("type","1");
        map.put("nonce_str", "fa577ce340859f9fe");
        map.put("text",text);

        String url = SortUtils.formatUrlParam(map, "utf-8", true);

        String url2 = url+"&app_key="+AppData.TENCENT_AI_APP_KEY;
        showOut("====getDataFenCi=url=="+url2);

        String md5Url = MD5.stringToMD5(url2);

        // showOut("====getDataFenCi=md5Url=="+md5Url.toUpperCase());
        // showOut("====getDataFanYi=sourceId=="+sourceId+"====targetId=====>"+targetId);
        OkHttpUtils
                .post()
                .url(AppData.URL_NLP_WORDCOM)
                .addParams("app_id",AppData.TENCENT_AI_APP_ID)
                .addParams("time_stamp",totalSeconds+"")
                .addParams("nonce_str","fa577ce340859f9fe")
                .addParams("sign",md5Url.toUpperCase())
                // .addParams("type","1")
                .addParams("text",text)

                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int i) {
                        hideProdialog();
                        showShortToast(e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int i) {
                        hideProdialog();
                        showOut("-----获取到的json数据1--activity_test0--getDataYiTu----"+response);
                        FanYiListDataObj result = (FanYiListDataObj) JsonUtil.jsonToBean(response,FanYiListDataObj.class);
                        if (result.getRet()==0){
                            int intent = result.getData().getIntent();

                            String content = "意图描述："+ StringUtil.getTypeIntentString(intent);
                            //Util.showResultTextDialog(TestActivity1.this, target_text, "文本翻译数据");
                            out_put_tv.setText(qingGan+"\n"+content);
                            List<YuYIData> list = result.getData().getCom_tokens();
                            //Util.showResultDialog(TestActivity2.this, response.toString(), "图片翻译数据");
                            adapterList.setAllItem(list);
                        }else {
                            int intent = result.getData().getIntent();

                            String content = "意图描述："+ StringUtil.getTypeIntentString(intent);
                            //Util.showResultTextDialog(TestActivity1.this, target_text, "文本翻译数据");
                            out_put_tv.setText(qingGan+"\n"+content);
                            showShortCenterToast(result.getMsg());
                        }
                    }
                });
    }
}
