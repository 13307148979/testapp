package com.teplot.testapp.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.View;

import android.widget.LinearLayout;
import android.widget.TextView;


import com.teplot.testapp.R;
import com.teplot.testapp.base.BaseActivity;
import com.teplot.testapp.been.details.LoginInfo;

import com.teplot.testapp.utils.PermissionUtils;
import com.teplot.testapp.utils.Utils;

import com.teplot.testapp.common.speech.JsonParser;
import com.teplot.testapp.common.speech.SpeakVoiceUtil;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import com.teplot.testapp.common.SingleChoiceListDlg;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import android.content.DialogInterface;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.iflytek.sunflower.FlowerCollector;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedHashMap;
/**
 * 监测界面
 */
public class HomeMainActivity1 extends BaseActivity {

    private LoginInfo loginInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_main1);
        createHandler();
        loginInfo =  mApplication.getLoginMsg2();
        initHeadView();
        initView();

         // 使用UI听写功能，请根据sdk文件目录下的notice.txt,放置布局文件和图片资源
        mIatDialog = new com.iflytek.cloud.ui.RecognizerDialog(HomeMainActivity1.this, mInitListener);
    }
    private void initHeadView(){
        int height = Utils.getStatusBarHeight(HomeMainActivity1.this);
        DisplayMetrics dm = getResources().getDisplayMetrics();

        View home_bar_view = (View)findViewById(R.id.home_bar_view);
        LinearLayout.LayoutParams linearParams =(LinearLayout.LayoutParams) home_bar_view.getLayoutParams();
        linearParams.height = height;// 控件的高强制设成20
        linearParams.width = dm.widthPixels;// 控件的宽强制设成30
       // showShortToast("高度==》"+height+"屏幕宽度==》"+dm.widthPixels);
        home_bar_view.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
    }


    //====================================================================================================


    private TextView title_tv,location_address_tv7;
    private EditText content_text_et;
    private void initView(){
        title_tv = (TextView) findViewById(R.id.title_tv);
        location_address_tv7 = (TextView) findViewById(R.id.location_address_tv7);
         content_text_et = (EditText) findViewById(R.id.content_text_et);
        title_tv.setText("讯飞语音");
    }
    private int[] chooseIDs = {0,0,0,0,0,0,0};
    private boolean[] isChoose = new boolean[7];
    private void showPosReportListDlg(final String[] strings,final String title,final int choose,final TextView bt) {
        if (strings != null && strings.length > 0) {
            SingleChoiceListDlg dlg = new SingleChoiceListDlg.Builder(
                    HomeMainActivity1.this)
                    .setTitle(title)
                    .setSingleChoiceItems(strings, chooseIDs[choose],
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dlg,
                                                    int position) {
                                    isChoose[choose] = true;
                                    chooseIDs[choose] = position;

                                    bt.setText(strings[position]);
                                    dlg.dismiss();
                                }
                            }).create();
            dlg.show();
        }
    }

    private String[] voicers = {
            "中英普青女小燕","中英普青男小宇","英文青女凯瑟琳","英文青男亨利",
            "英文青女玛丽","中英普青女小研","中英普青女小琪","中英普青男小峰",
            "中英粤青女小梅","中英台普青女小莉", "中英台普青女晓琳","汉语川青女小蓉",
            "汉语东北青女小芸","汉语东北青女小倩","汉语河南青男小坤","汉语河南青男小强",
            "汉语陕西青女小莹","汉普童男小新","汉普童女楠楠","汉普老男老孙",};
    private String[] voicerids = {
            "xiaoyan","xiaoyu","catherine","henry",
            "vimary", "vixy","xiaoqi","vixf",
            "xiaomei","vixl","xiaolin","xiaorong",
            "vixyun","xiaoqian","xiaokun","xiaoqiang",
            "vixying","xiaoxin","nannan","vils"};
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.microphone_iv:
               // showShortToast("语音输入");
                showPermissionAUDIO();
                break;
            case R.id.location_address_tv7:
               // showShortToast("语音输入");
                showPosReportListDlg(voicers,"发音人选择",0,location_address_tv7);
                break;

            case R.id.submit_send_bt:
                //showShortToast("语音合成");
                //showOut(voicers[chooseIDs[0]]+"===codeBts1[chooseIDs[4]]===>"+voicerids[chooseIDs[0]]);
                String smsStr = content_text_et.getText().toString().trim();
                new SpeakVoiceUtil(HomeMainActivity1.this,voicerids[chooseIDs[0]]).speak(smsStr);
                break;
        }
    }

    //讯飞语音
//===========================================用于语音听写=========================================

    private android.content.SharedPreferences mSharedPreferences;
    // 引擎类型
    private String mEngineType = SpeechConstant.TYPE_CLOUD;
    int ret = 0; // 函数调用返回值
    // 语音听写UI
    private RecognizerDialog mIatDialog;

    private boolean mTranslateEnable = false;
    // 用HashMap存储听写结果
    private HashMap<String, String> mIatResults = new LinkedHashMap<String, String>();
    private int first = -1;
    public  void speeck(){
        first = -1;
        // 开始听写
        // 如何判断一次听写结束：OnResult isLast=true 或者 onError
        // 移动数据分析，收集开始听写事件
        FlowerCollector.onEvent(HomeMainActivity1.this, "iat_recognize");

        // speech_tv.setText(null);// 清空显示内容
        mIatResults.clear();
        // 设置参数
        //setParam();

        mIatDialog.setListener(mRecognizerDialogListener);
        mIatDialog.show();
        unShowText();
        showShortCenterToast(getString(R.string.text_begin));
//        boolean isShowDialog = mSharedPreferences.getBoolean(
//                getString(R.string.pref_key_iat_show), true);
//        if (isShowDialog) {
//            // 显示听写对话框
//
//        } else {
//            // 不显示听写对话框
//            ret = mIat.startListening(mRecognizerListener);
//            if (ret != ErrorCode.SUCCESS) {
//                showShortToast("听写失败,错误码：" + ret);
//            } else {
//                showShortToast(getString(R.string.text_begin));
//            }
//        }
    }

    private void unShowText(){
        //获取字体所在的控件，设置为"",隐藏字体，
       TextView txt = (TextView)mIatDialog.getWindow().getDecorView().findViewWithTag("textlink");
       txt.setText("");
    }
    /**
     * 听写UI监听器
     */
    private RecognizerDialogListener mRecognizerDialogListener = new RecognizerDialogListener() {
        public void onResult(RecognizerResult results, boolean isLast) {
            if( mTranslateEnable ){
                printTransResult( results );
            }else{
                if (first==-1){
                    first = 1;
                    printResult(results);
                }
            }
        }

        /**
         * 识别回调错误.
         */
        public void onError(SpeechError error) {
            if(mTranslateEnable && error.getErrorCode() == 14002) {
                showShortToast( error.getPlainDescription(true)+"\n请确认是否已开通翻译功能" );
            } else {
                showShortToast(error.getPlainDescription(true));
            }
        }
    };

    /**
     * 初始化监听器。
     */
    private InitListener mInitListener = new InitListener() {

        @Override
        public void onInit(int code) {
            Log.d("MainActivity", "SpeechRecognizer init() code = " + code);
            if (code != ErrorCode.SUCCESS) {
                showShortToast("初始化失败，错误码：" + code);
            }
        }
    };
    private void printTransResult (RecognizerResult results) {

        String trans  = JsonParser.parseTransResult(results.getResultString(),"dst");
        String oris = JsonParser.parseTransResult(results.getResultString(),"src");

        if( TextUtils.isEmpty(trans)||TextUtils.isEmpty(oris) ){
            showShortToast( "解析结果失败，请确认是否已开通翻译功能。" );
        }else{
            content_text_et.setText( "原始语言:\n"+oris+"\n目标语言:\n"+trans );
        }
    }
    private void printResult(RecognizerResult results) {
        String text = JsonParser.parseIatResult(results.getResultString());

        String sn = null;
        // 读取json结果中的sn字段
        try {
            JSONObject resultJson = new JSONObject(results.getResultString());
            sn = resultJson.optString("sn");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mIatResults.put(sn, text);

        StringBuffer resultBuffer = new StringBuffer();
        for (String key : mIatResults.keySet()) {
            resultBuffer.append(mIatResults.get(key));
        }
        setStringBuffer(resultBuffer.toString());
      //  String content = getStringBuffer(resultBuffer.toString());
       // content_text_et.setText(content);
       // content_text_et.setSelection(content_text_et.length());
    }
    private void setStringBuffer(String content){
        int index = content_text_et.getSelectionStart();
        android.text.Editable editable = content_text_et.getText();
        editable.insert(index, content);
        content_text_et.setSelection(content_text_et.length());
    }
    //=============================================================
    public void showPermissionAUDIO() {
        PermissionUtils.requestPermission(HomeMainActivity1.this,
                PermissionUtils.CODE_RECORD_AUDIO, mPermissionGrant);
    }

    private PermissionUtils.PermissionGrant mPermissionGrant = new PermissionUtils.PermissionGrant() {
        @Override
        public void onPermissionGranted(int requestCode) {
            switch (requestCode) {
                case PermissionUtils.CODE_RECORD_AUDIO:
                        speeck();
                    break;
                default:
                    break;
            }
        }
    };
    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        PermissionUtils.requestPermissionsResult2(this, requestCode, permissions, grantResults,
                mPermissionGrant,REQUESTCODE_PERMISSIONS_CODE);
    }
    private final int REQUESTCODE_PERMISSIONS_CODE= 0;

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUESTCODE_PERMISSIONS_CODE:
                showPermissionAUDIO();
                break;
        }
    }
}
