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
import com.teplot.testapp.utils.ImageLoaderUtil;
import com.teplot.testapp.utils.PermissionUtils;
import com.teplot.testapp.utils.Utils;


/**
 * 监测界面
 */
public class HomeMainActivity4 extends BaseActivity {

    private LoginInfo loginInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_main1);
        createHandler();
        loginInfo =  mApplication.getLoginMsg2();
        initHeadView();
        initView();

    }
    private void initHeadView(){
        int height = Utils.getStatusBarHeight(HomeMainActivity4.this);
        DisplayMetrics dm = getResources().getDisplayMetrics();

        View home_bar_view = (View)findViewById(R.id.home_bar_view);
        LinearLayout.LayoutParams linearParams =(LinearLayout.LayoutParams) home_bar_view.getLayoutParams();
        linearParams.height = height;// 控件的高强制设成20
        linearParams.width = dm.widthPixels;// 控件的宽强制设成30
        // showShortToast("高度==》"+height+"屏幕宽度==》"+dm.widthPixels);
        home_bar_view.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
    }


    //====================================================================================================

    private TextView title_tv;
    private void initView(){
        title_tv = (TextView) findViewById(R.id.title_tv);
        title_tv.setText("首页4");
    }


    //此界面需要读写权限
    public void showPermissionCONTENT() {
        // type = "1";
        PermissionUtils.requestPermission(HomeMainActivity4.this, PermissionUtils.CODE_WRITE_EXTERNAL_STORAGE,
                mPermissionGrant);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImageLoaderUtil.getInstances().clear();
    }
    private PermissionUtils.PermissionGrant mPermissionGrant = new PermissionUtils.PermissionGrant() {
        @Override
        public void onPermissionGranted(int requestCode) {
            switch (requestCode) {
                case PermissionUtils.CODE_WRITE_EXTERNAL_STORAGE:
                    Utils.setImageLoaderUtilInit();
                    break;
                case PermissionUtils.CODE_ACCESS_COARSE_LOCATION://定位权限
                    showProdialog(null, "正在定位,请稍后...", null);
                    break;
                default:
                    break;
            }
        }
    };

    private final int REQUESTCODE_PERMISSIONS_CODE= 0;
    private final int SEND_RESULT_DATA = 1;
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUESTCODE_PERMISSIONS_CODE:
                // showPermissionLOCATION();
                break;
            case SEND_RESULT_DATA:
                if (resultCode == Activity.RESULT_OK) {
                    //刷新数据
                    //showShortToast("刷新数据");
                    String description = data.getStringExtra("description");
                    String icon = data.getStringExtra("icon");
                    // showWeatherData(description,icon);
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        PermissionUtils.requestPermissionsResult2(this, requestCode, permissions, grantResults, mPermissionGrant,REQUESTCODE_PERMISSIONS_CODE);
    }
}
