package com.teplot.testapp.ui.test1;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.teplot.testapp.R;
import com.teplot.testapp.apps.AppData;
import com.teplot.testapp.base.BaseActivity;
import com.teplot.testapp.been.result.FanYiDataObj;
import com.teplot.testapp.common.SingleChoiceListDlg;
import com.teplot.testapp.common.TopBar;
import com.teplot.testapp.utils.DateTimeUtil;
import com.teplot.testapp.utils.JsonUtil;
import com.teplot.testapp.utils.MD5;
import com.teplot.testapp.utils.SortUtils;
import com.teplot.testapp.utils.StringUtil;
import com.teplot.testapp.utils.Utils;
import com.teplot.testapp.utils.login.Util;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;


public class TestActivity1 extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test1_1);
        setImmersionBarNon();
        initHeadView();
        initView();
    }
    private void initHeadView() {
        mTopBar = new TopBar(this);
        mTopBar.setPathName("文本翻译");
    }

    private TextView mLocation_address_tv7,mLocation_address_tv0,out_put_tv,content_num_tv;
    private EditText contentTextTv;
    private void initView(){
        mLocation_address_tv7 = (TextView) findViewById(R.id.location_address_tv7);
        mLocation_address_tv0 = (TextView) findViewById(R.id.location_address_tv0);
        out_put_tv = (TextView) findViewById(R.id.out_put_tv);
//        content_num_tv= (TextView) findViewById(R.id.content_num_tv);
//        contentTextTv = (EditText) findViewById(R.id.content_text_tv);
        mLocation_address_tv7.setText(sources[0]);
        mLocation_address_tv0.setText(targets[0]);
        initView2();
    }
    private int maxNum = 300;
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
            case R.id.location_address_tv7:
                showPosReportListDlg(sources,"选择源语言",0,mLocation_address_tv7);
                break;
            case R.id.location_address_tv0:
                showPosReportListDlg(targets,"选择目标语言",1,mLocation_address_tv0);
                break;
            case R.id.submit_send_bt:
                if (!StringUtil.isEmpty(contentTextTv.getText().toString().trim())){
                    getDataFanYi();
                }else {
                    showShortCenterToast("请输入相关内容");
                }
                break;
        }
    }
    private int[] chooseIDs = {0,0,0,0,0,0,0};
    private boolean[] isChoose = new boolean[7];
    private String[] sources = {
            "英文","中文","日文","韩文","法文","西班牙文","意大利文","德文",
            "土耳其文","俄文","葡萄牙文","越南文","印度尼西亚文","马来西亚文","泰文"};
    private String[] sourceIds = {
            "en","zh","jp","kr","fr","es","it","de",
            "tr","ru","pt","vi","id","ms","th"};
    private void showPosReportListDlg(final String[] strings,final String title,final int choose,final TextView bt) {
        if (strings != null && strings.length > 0) {
            SingleChoiceListDlg dlg = new SingleChoiceListDlg.Builder(
                    TestActivity1.this)
                    .setTitle(title)
                    .setSingleChoiceItems(strings, chooseIDs[choose],
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dlg,
                                                    int position) {
                                    isChoose[choose] = true;
                                    chooseIDs[choose] = position;

                                    if (choose==0){
                                        showData(position);
                                    }
                                    bt.setText(strings[position]);
                                    dlg.dismiss();
                                }
                            }).create();
            dlg.show();
        }
    }

    private String[] targets = {"中文","法文","西班牙文","意大利文","德文",
            "土耳其文","俄文","葡萄牙文","越南文","印度尼西亚文","马来西亚文","泰文"};
    private String[] targetIds = {"zh", "fr", "es", "it","de" ,
            "tr" , "ru","pt" , "vi","id" ,"ms" , "th"};

    private void showData(int position){
        chooseIDs[1] = 0;
        switch (position){
            case 0:
                targets = new String[]{"中文","法文","西班牙文","意大利文","德文",
                        "土耳其文","俄文","葡萄牙文","越南文","印度尼西亚文","马来西亚文","泰文"};
                targetIds = new String[]{"zh", "fr", "es", "it","de" ,
                        "tr" , "ru","pt" , "vi","id" ,"ms" , "th"};
                mLocation_address_tv0.setText(targets[0]);
                break;
            case 1:
                targets = new String[]{"英文","法文","西班牙文","意大利文","德文",
                        "土耳其文","俄文","葡萄牙文","越南文","印度尼西亚文","马来西亚文","泰文"
                        ,"日文","韩文"};
                targetIds = new String[]{"en", "fr", "es", "it","de" ,
                        "tr" , "ru","pt" , "vi","id" ,"ms" , "th"
                        ,"jp","kr"};
                mLocation_address_tv0.setText(targets[0]);
                break;
            case 2:
                targets = new String[]{"英文","中文","西班牙文","意大利文","德文",
                        "土耳其文","俄文","葡萄牙文"};
                targetIds = new String[]{"en","zh", "es", "it","de" ,
                        "tr" , "ru","pt"};
                mLocation_address_tv0.setText(targets[0]);
                break;
            case 3:
                targets = new String[]{"英文","中文","法文","意大利文","德文",
                        "土耳其文","俄文","葡萄牙文"};
                targetIds = new String[]{"en","zh", "fr", "it","de" ,
                        "tr" , "ru","pt" };
                mLocation_address_tv0.setText(targets[0]);
                break;
            case 4:
                targets = new String[]{"英文","中文","法文","西班牙文","德文",
                        "土耳其文","俄文","葡萄牙文"};
                targetIds = new String[]{"en","zh", "fr", "es", "de" ,
                        "tr" , "ru","pt" };
                mLocation_address_tv0.setText(targets[0]);
                break;
            case 5:
                targets = new String[]{"英文","中文","法文","西班牙文","意大利文",
                        "土耳其文","俄文","葡萄牙文"};
                targetIds = new String[]{"en","zh", "fr", "es", "it",
                        "tr" , "ru","pt" };
                mLocation_address_tv0.setText(targets[0]);
                break;
            case 6:
                targets = new String[]{"英文","中文","法文","西班牙文","意大利文","德文",
                        "俄文","葡萄牙文"};
                targetIds = new String[]{"en","zh", "fr", "es", "it","de" ,
                        "ru","pt" };
                mLocation_address_tv0.setText(targets[0]);
                break;
            case 7:
                targets = new String[]{"英文","中文","法文","西班牙文","意大利文","德文",
                        "土耳其文","葡萄牙文"};
                targetIds = new String[]{"en","zh", "fr", "es", "it","de" ,
                        "tr" ,"pt" };
                mLocation_address_tv0.setText(targets[0]);
                break;
            case 8:
                targets = new String[]{"英文","中文","法文","西班牙文","意大利文","德文",
                        "土耳其文","俄文"};
                targetIds = new String[]{"en","zh", "fr", "es", "it","de" ,
                        "tr" , "ru"};
                mLocation_address_tv0.setText(targets[0]);
                break;
            case 9:
                targets = new String[]{"英文","中文"};
                targetIds = new String[]{"en","zh"};
                mLocation_address_tv0.setText(targets[0]);
                break;
            case 10:
                targets = new String[]{"英文","中文"};
                targetIds = new String[]{"en","zh"};
                mLocation_address_tv0.setText(targets[0]);
                break;
            case 11:
                targets = new String[]{"英文","中文"};
                targetIds = new String[]{"en","zh"};
                mLocation_address_tv0.setText(targets[0]);
                break;
            case 12:
                targets = new String[]{"英文","中文"};
                targetIds = new String[]{"en","zh"};
                mLocation_address_tv0.setText(targets[0]);
                break;
            case 13:
                targets = new String[]{"中文"};
                targetIds = new String[]{"zh"};
                mLocation_address_tv0.setText(targets[0]);
                break;
            case 14:
                targets = new String[]{"中文"};
                targetIds = new String[]{"zh"};
                mLocation_address_tv0.setText(targets[0]);
                break;
        }
    }

    private void getDataFanYi(){
        showProdialog(null, "文本翻译中,请稍后...", null);
        long totalMilliSeconds = System.currentTimeMillis();
        long totalSeconds = totalMilliSeconds / 1000;

        String sourceId = sourceIds[chooseIDs[0]];
        String targetId= targetIds[chooseIDs[1]];;
        String text = contentTextTv.getText().toString().trim();

        Map<String, String> map = new HashMap<String, String>();
        map.put("app_id", AppData.TENCENT_AI_APP_ID);
        map.put("time_stamp", totalSeconds+"");
       // map.put("type","1");
        map.put("nonce_str", "fa577ce340859f9fe");
        map.put("text",text);

        map.put("source",sourceId);
        map.put("target",targetId);
        String url = SortUtils.formatUrlParam(map, "utf-8", true);

        String url2 = url+"&app_key="+AppData.TENCENT_AI_APP_KEY;
        showOut("====getDataFenCi=url=="+url2);

        String md5Url = MD5.stringToMD5(url2);

       // showOut("====getDataFenCi=md5Url=="+md5Url.toUpperCase());
       // showOut("====getDataFanYi=sourceId=="+sourceId+"====targetId=====>"+targetId);
        OkHttpUtils
                .post()
                .url(AppData.URL_NLP_TEXTTRANSLATE)
                .addParams("app_id",AppData.TENCENT_AI_APP_ID)
                .addParams("time_stamp",totalSeconds+"")
                .addParams("nonce_str","fa577ce340859f9fe")
                .addParams("sign",md5Url.toUpperCase())
               // .addParams("type","1")
                .addParams("text",text)

                .addParams("source",sourceId)
                .addParams("target",targetId)

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
                        showOut("-----获取到的json数据1--LoginActivity--getDataFanYi----"+response);
                        FanYiDataObj result = (FanYiDataObj) JsonUtil.jsonToBean(response,FanYiDataObj.class);
                        if (result.getRet()==0){
                            String target_text = result.getData().getTarget_text();
                            //Util.showResultTextDialog(TestActivity1.this, target_text, "文本翻译数据");
                            out_put_tv.setText(target_text);
                        }else {
                            showShortCenterToast(result.getMsg());
                        }
                    }
                });
    }
}
