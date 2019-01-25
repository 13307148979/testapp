package com.teplot.testapp;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import com.teplot.testapp.apps.AppContext;
import com.teplot.testapp.apps.AppData;
import com.teplot.testapp.base.BaseActivity;
import com.teplot.testapp.been.details.LoginInfo;
import com.teplot.testapp.been.details.UserInfo;
import com.teplot.testapp.common.ConfireDlg;
import com.teplot.testapp.db.UserLoginDB;
import com.teplot.testapp.utils.FileUtils;
import com.teplot.testapp.utils.MD5;
import com.teplot.testapp.utils.StringUtil;
import com.teplot.testapp.utils.Utils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.MediaType;

/**
 * 登陆
 */
public class LoginActivity extends BaseActivity {

    private EditText login_num_ET,index_pwd_ET;

    private UserInfo userInfo;

    public void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_login);
        //	initState();
        setImmersionBarNon();
        initView();
        showASK_LOCATION();
    }

    private  String loginName1,loginPwd1;
    //private LinearLayout pwd_ll,user_ll;
    private String phone,type;//,pwd
    private void initView(){

        Intent intent = getIntent();
        phone =  intent.getStringExtra("phone");
        type = intent.getStringExtra("type");
        //  pwd = intent.getStringExtra("pwd");

        //user_ll = (LinearLayout)findViewById(R.id.user_ll);
        // pwd_ll = (LinearLayout)findViewById(R.id.pwd_ll);
        login_num_ET = (EditText) findViewById(R.id.index_login_num);
        index_pwd_ET = (EditText) findViewById(R.id.index_login_pwd);

//		loginName1 = StringUtil.trimNull(mApplication.getLoginMsg2().getAccount());
//        loginPwd1 = StringUtil.trimNull(mApplication.getLoginMsg2().getPassword());

        if (StringUtil.isEmpty(phone)){//&&StringUtil.isEmpty(pwd)
            loginName1 = Utils.readSharedPreferences(LoginActivity.this,"loginName","");
            loginPwd1  = Utils.readSharedPreferences(LoginActivity.this,"loginPwd","");
        }else {
            loginName1 = phone;
            loginPwd1 = "";
        }

        login_num_ET.setText(loginName1);
        index_pwd_ET.setText(loginPwd1);

        index_pwd_ET.setOnKeyListener(Keylistener);
        // 默认弹出数字键盘 但不锁死输入类型
        login_num_ET.setRawInputType(InputType.TYPE_CLASS_NUMBER);
        index_pwd_ET.setRawInputType(InputType.TYPE_CLASS_NUMBER);

//        user_ll.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                login_num_ET.setText("");
//            }
//        });
//        pwd_ll.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                index_pwd_ET.setText("");
//            }
//        });
    }
    private void doLogin(){
        if (!StringUtil.isEmpty(loginName1)&&!StringUtil.isEmpty(loginPwd1)){
            if (!"2".equals(type)){
                login(loginName1,loginPwd1);
            }
        }
        if (StringUtil.isEmpty(loginName1)&&StringUtil.isEmpty(loginPwd1)){
            if (!"2".equals(type)){
                showLogin();
            }
        }
    }
    private void showLogin(){
        LoginInfo uInfo = mApplication.getLoginMsg2();

        uInfo.setAccount("");
        uInfo.setPassword("");

        uInfo.setId(-1);
        uInfo.setUserName("未登录");
        uInfo.setTelephone("");
        uInfo.setEmail("");
        uInfo.setIcon("");
        uInfo.setStatus("");
        uInfo.setRegisterTime("");

        uInfo.setSex("");
        uInfo.setSign("");
        uInfo.setWeiXin("");
        uInfo.setAddress("");
        uInfo.setRealNameStatus("");

        uInfo.setWeather_info("");
        uInfo.setNew_message("");
        uInfo.setDisturb_mode("");
        uInfo.setDisturb_time_start("");
        uInfo.setDisturb_time_end("");

        uInfo.setAreaId("");
        uInfo.setAreaName("");

        //保存用户信息
        UserLoginDB mySQLiteOpenHelper = new UserLoginDB(LoginActivity.this);
        mySQLiteOpenHelper.updateUser(uInfo);
        mySQLiteOpenHelper.close();

        skipMain();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.zhuce_phone_tv:
                showShortToast("手机注册");
                break;
            case R.id.wangji_pwd_tv:
                 showShortToast("忘记密码");
                break;
            case R.id.weixin_login_iv:
                showShortCenterToast("微信登陆");
               // skipMain();
                break;
            case R.id.qq_login_iv:
                showShortToast("QQ登陆");
               // skipMain();
                break;
            case R.id.weibo_login_iv:
                showShortToast("微博登陆");
                skipMain();
                break;
        }
    }

    //==============================================================
    public void showShortToast(String text){
        Toast.makeText(LoginActivity.this,text,Toast.LENGTH_SHORT).show();
    }
    // 登陆
    public void login(View view) {
        //showShortToast("登陆");
        if (StringUtil.isEmpty(login_num_ET.getText().toString())) {
            showShortToast("请输入账号！");
            ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                    .showSoftInput(login_num_ET, InputMethodManager.SHOW_FORCED);
            return;
        }
        if (StringUtil.isEmpty(index_pwd_ET.getText().toString())) {
            showShortToast("请输入密码！");
            ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                    .showSoftInput(index_pwd_ET, InputMethodManager.SHOW_FORCED);
            return;
        }

        // 隐藏键盘
        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(login_num_ET.getWindowToken(), 0);

        loginName1 = login_num_ET.getText().toString();
        loginPwd1 = index_pwd_ET.getText().toString();
        //登陆
       // skipMain();
        //login("ceshi","123456");
        login(loginName1,loginPwd1);
    }
    //    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        Log.d("SettingOIActivity", "-->onActivityResult " + requestCode  + " resultCode=" + resultCode);
//        if (requestCode == Constants.REQUEST_LOGIN ||
//                requestCode == Constants.REQUEST_APPBAR) {
//            Tencent.onActivityResultData(requestCode,resultCode,data,loginListener);
//        }
//        super.onActivityResult(requestCode, resultCode, data);
//    }
    private void login(final String loginName, final String loginPwd){

        //测试用
       if ("123456".equals(loginName) && "123456".equals(loginPwd)){
           skipMain();
       }else {
           showShortCenterToast("账号或密码错误！");
       }
//        showProdialog(null, "正在登陆,请稍后...", null);
//        String pwd = MD5.stringToMD5(StringUtil.trimNull(loginPwd));
//        // showLongCenterToast(new Gson().toJson(new LoginInfo(StringUtil.trimNull(loginName), pwd)));
//        OkHttpUtils
//                .postString()
//                .url(AppData.URL_LOGIN)
//
//                .content(new Gson().toJson(new LoginInfo(StringUtil.trimNull(loginName), pwd)))
//                .mediaType(MediaType.parse("application/json; charset=utf-8"))
//
////                .addHeader("Content-Type","application/json")
////                .addParams("account", StringUtil.trimNull(loginName))
////                .addParams("password",pwd)
//
//                .build()
////                .execute(new StringCallback() {
////                    @Override
////                    public void onError(Call call, Exception e, int i) {
////                        showShortToast("StringCallback===>"+e.getMessage());
////                    }
////
////                    @Override
////                    public void onResponse(String s, int i) {
////                        System.out.println("StringCallback===>"+s);
////                        showShortToast("StringCallback===>"+s);
////                    }
////                });
//                .execute(new UserCallback() {
//                    @Override
//                    public void onError(Call call, Exception e, int i) {
//                        hideProdialog();
//                        //new AppException().makeToast(LoginActivity.this);
//                        showShortToast(e.getMessage());
//                    }
//                    @Override
//                    public void onResponse(LoginInfoObj loginInfoObj, int i) {
//                        hideProdialog();
//                        int isSecess = loginInfoObj.getCode();
//                        // showShortToast("isSecess的值为=====》"+isSecess);
//                        if (isSecess == 0){
//                            saveLoginInfo(loginInfoObj,loginName,loginPwd);
//                        }else {
//                            showShortToast(loginInfoObj.getMessage());
//                        }
//                    }
//                });
    }
    private void skipMain() {
        Intent intent = new Intent(this, HomeMainTabActivity.class);
        startActivity(intent);
        finish();
    }
    /**
     * 按键监听，当按下回车键时登陆
     */
    Button.OnKeyListener Keylistener = new Button.OnKeyListener() {
        @Override
        public boolean onKey(View arg0, int keyCode, KeyEvent event) {
            if (KeyEvent.KEYCODE_ENTER == keyCode
                    && event.getAction() == KeyEvent.ACTION_DOWN) {
                login(null);
            }
            return false;
        }
    };

    //===============================因为进入地图必须需要定位权限才能定位，并上传定位的相关信息，
    // 因此在这里要获取到定位权限，附带读写权限===========================================
    // 因此在这里要获取到定位权限，附带读写权限===========================================
    final public static int REQUEST_CODE_ASK_LOCATION = 0;
    final public static int REQUEST_CODE_ASK_CALL_STORGE = 1;
    final public static int REQUEST_CODE_ASK_CODE_CAMERA = 2;
    /**
     * 定位权限的获取
     */
    public void showASK_LOCATION() {
        if (Build.VERSION.SDK_INT >= 23) {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_CODE_ASK_LOCATION);
                return;
            }else {
                showCODE_CAMERA();
            }
        }else {
            doLogin();
        }
    }
    //相机权限
    public void showCODE_CAMERA() {
        if (Build.VERSION.SDK_INT >= 23) {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                        REQUEST_CODE_ASK_CODE_CAMERA);
                return;
            }else {
                showCALL_STORGE();
            }
        }
    }
    //读写权限
    public void showCALL_STORGE(){
        if (Build.VERSION.SDK_INT >= 23) {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_CODE_ASK_CALL_STORGE);
                return;
            }else {
                doLogin();
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_LOCATION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //showLongToast("以获取定位权限");
                    showCODE_CAMERA();
                } else {
                    // Permission Denied
                    //showLongToast("您禁止了定位权限");
                    showExitDlg("\u3000您禁止了定位权限,软件不能正常运行，请开启定位权限，取消将退出客户端！");
                    // AppContext.getInstance().exitApp();
                }
                break;
            case REQUEST_CODE_ASK_CODE_CAMERA:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //showLongToast("以获取定位权限");
                    showCALL_STORGE();
                } else {
                    // Permission Denied
                    //showLongToast("您禁止了定位权限");
                    showExitDlg("\u3000您禁止了相机权限,软件不能正常运行，请开启相机权限，取消将退出客户端！");
                    // AppContext.getInstance().exitApp();
                }
                break;
            case REQUEST_CODE_ASK_CALL_STORGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    FileUtils.createDirectory(AppData.PICTURE_PATH);
                    FileUtils.createDirectory(AppData.VIDIO_PATH);
                    //ImageLoader.init(AppContext.getInstance(), R.drawable.backgroud_nodata, AppData.SD_PATH + AppData.PICTURE_PATH);
                    //showLongToast("写入权限已获取2");
                    //监测新版本
                    // doCheckVer();
                    doLogin();
                } else {
                    // Permission Denied
                    // showLongToast("您禁止了写入权限");
                    showExitDlg("\u3000您禁止了读写权限,软件不能正常运行，请开启读写权限，取消将退出客户端！");
                    // AppContext.getInstance().exitApp();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
    private final int REQUESTCODE_PERMISSIONS_CODE= 0;
    public  void showExitDlg( String text) {
        ConfireDlg exitDlg = new ConfireDlg.Builder(this).setTitle("警告")
                .setCancelable(false)
                .setContent(text)
                .setYesButton("去开启", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);

                        Uri uri = Uri.fromParts("package", AppContext.getInstance().getPackageName(), null);
                        intent.setData(uri);
                        startActivityForResult(intent,REQUESTCODE_PERMISSIONS_CODE);
                        //startActivity(intent);
                    }
                }).setNoButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        AppContext.getInstance().exitApp();
                    }
                }).create3();
        exitDlg.show();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("SettingOIActivity", "-->onActivityResult " + requestCode  + " resultCode=" + resultCode);
        switch (requestCode) {
            case REQUESTCODE_PERMISSIONS_CODE:
                //showShortToast("REQUESTCODE_PERMISSIONS_CODE"+resultCode);
                showASK_LOCATION();
                break;
        }
    }
}
