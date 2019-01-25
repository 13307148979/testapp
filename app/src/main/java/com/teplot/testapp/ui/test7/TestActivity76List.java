package com.teplot.testapp.ui.test7;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.teplot.testapp.R;
import com.teplot.testapp.adapter.Test0YuYiListAdapter;
import com.teplot.testapp.adapter.Test2XianLiaoListAdapter;
import com.teplot.testapp.adapter.Test7FacePersonListAdapter;
import com.teplot.testapp.apps.AppContext;
import com.teplot.testapp.apps.AppData;
import com.teplot.testapp.base.BaseActivity;
import com.teplot.testapp.been.details.FacePersonData;
import com.teplot.testapp.been.details.XianLiaoData;
import com.teplot.testapp.been.details.YuYIData;
import com.teplot.testapp.been.result.FaceAgeDataObj;
import com.teplot.testapp.been.result.FanYiListDataObj;
import com.teplot.testapp.been.result.XianLiaoDataObj;
import com.teplot.testapp.been.result.YuYiQingGanDataObj;
import com.teplot.testapp.common.ConfireDlg;
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


public class TestActivity76List extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test76_list);
        setImmersionBarNon();
        SoftHideKeyBoardUtil.assistActivity(TestActivity76List.this);
        initHeadView();
        initListView();

        getDataList();
    }

    private int group_id;
    private void initHeadView() {
        Intent intent = getIntent();
        group_id = intent.getIntExtra("group_id",0);

        mTopBar = new TopBar(this);
        mTopBar.setPathName("个体列表");
    }
    private ListView listview_main;
    private Test7FacePersonListAdapter adapterList;
    private void initListView(){
        listview_main = (ListView) findViewById(R.id.listview_main);
        adapterList = new Test7FacePersonListAdapter(TestActivity76List.this,new ArrayList<String>());
        listview_main.setAdapter(adapterList);

        listview_main.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               // showShortCenterToast("点击数据==》"+position);
                String person_id = adapterList.getItem(position);
                Intent intent = new Intent(TestActivity76List.this,TestActivity76EditDetail.class);
                intent.putExtra("person_id",person_id);
               // intent.putExtra("group_id",group_id);
                startActivityForResult(intent,DETAIL_RESULT_DATA);
            }
        });

        listview_main.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                String ids = adapterList.getItem(position);
                FacePersonData facePersonData= mApplication.getFacePersonMsg(ids);
                String name = facePersonData.getPerson_name();

                showLongAccoutDlg(name,ids);
                return true;
            }
        });
    }
    public  void showLongAccoutDlg(String address, final String id) {
        ConfireDlg exitDlg = new ConfireDlg
                .Builder(TestActivity76List.this)
                .setTitle("提示")
                .setCancelable(true)
                .setContent("\u3000确定删除数据："+address+"？")
                .setYesButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        // showShortToast("删除并刷新");
                        delDataItem(id);
                        //delData(userId,dataId,position);
                    }
                }).setNoButton("取消", null).create3();
        exitDlg.show();
    }

    private void getDataList(){
        showProdialog(null, "请求数据中,请稍后...", null);
        long totalMilliSeconds = System.currentTimeMillis();
        long totalSeconds = totalMilliSeconds / 1000;

        Map<String, String> map = new HashMap<String, String>();
        map.put("app_id", AppData.TENCENT_AI_APP_ID);
        map.put("time_stamp", totalSeconds+"");
        // map.put("type","1");
        map.put("nonce_str", "fa577ce340859f9fe");

        map.put("group_id",group_id+"");

        String url = SortUtils.formatUrlParam(map, "utf-8", true);

        String url2 = url+"&app_key="+AppData.TENCENT_AI_APP_KEY;
        showOut("====getDataFenCi=url=="+url2);

        String md5Url = MD5.stringToMD5(url2);

        // showOut("====getDataFenCi=md5Url=="+md5Url.toUpperCase());
        // showOut("====getDataFanYi=sourceId=="+sourceId+"====targetId=====>"+targetId);
        OkHttpUtils
                .post()
                .url(AppData.URL_FACE_GETPERSONIDS)
                .addParams("app_id",AppData.TENCENT_AI_APP_ID)
                .addParams("time_stamp",totalSeconds+"")
                .addParams("nonce_str","fa577ce340859f9fe")
                .addParams("sign",md5Url.toUpperCase())

                .addParams("group_id",group_id+"")

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
                        FaceAgeDataObj result = (FaceAgeDataObj) JsonUtil.jsonToBean(response,FaceAgeDataObj.class);

                        if (result.getRet()==0){
                            List<String> person_ids = result.getData().getPerson_ids();
                            adapterList.setAllItem(person_ids);
                        }else {
                            showShortCenterToast(result.getMsg());
                        }
                    }
                });
    }

    private void delDataItem(final String person_id){
        showProdialog(null, "数据删除中,请稍后...", null);
        long totalMilliSeconds = System.currentTimeMillis();
        long totalSeconds = totalMilliSeconds / 1000;

        Map<String, String> map = new HashMap<String, String>();
        map.put("app_id", AppData.TENCENT_AI_APP_ID);
        map.put("time_stamp", totalSeconds+"");
        // map.put("type","1");
        map.put("nonce_str", "fa577ce340859f9fe");

        map.put("person_id",person_id);

        String url = SortUtils.formatUrlParam(map, "utf-8", true);

        String url2 = url+"&app_key="+AppData.TENCENT_AI_APP_KEY;
        showOut("====getDataFenCi=url=="+url2);

        String md5Url = MD5.stringToMD5(url2);

        // showOut("====getDataFenCi=md5Url=="+md5Url.toUpperCase());
        // showOut("====getDataFanYi=sourceId=="+sourceId+"====targetId=====>"+targetId);
        OkHttpUtils
                .post()
                .url(AppData.URL_FACE_DELPERSON)
                .addParams("app_id",AppData.TENCENT_AI_APP_ID)
                .addParams("time_stamp",totalSeconds+"")
                .addParams("nonce_str","fa577ce340859f9fe")
                .addParams("sign",md5Url.toUpperCase())

                .addParams("person_id",person_id)

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
                        FaceAgeDataObj result = (FaceAgeDataObj) JsonUtil.jsonToBean(response,FaceAgeDataObj.class);

                        if (result.getRet()==0){
                            showShortCenterToast("删除成功！");
                           // mApplication.delFacePersonMsg(person_id);
                            adapterList.removeAllItem();
                            getDataList();
                        }else {
                            showShortCenterToast(result.getMsg());
                        }
                    }
                });
    }

    private final int DETAIL_RESULT_DATA = 0;

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case DETAIL_RESULT_DATA:
                if (resultCode == Activity.RESULT_OK) {
                   // String content = data.getStringExtra("content");
                  // showShortCenterToast("刷新数据");
                    getDataList();
                }
                break;

        }
    }
}
