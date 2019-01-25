package com.teplot.testapp.common;

import android.app.Activity;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.teplot.testapp.R;
import com.teplot.testapp.base.BaseActivity;
import com.teplot.testapp.utils.Utils;
import com.teplot.testapp.utils.ViewSetWhithAndHeightUtils;


/**
* Created by Administrator on 14-5-14.
*/
public class TopBar implements View.OnClickListener{
    private Activity mActivity;
    private View mView;

    //left
    public ImageView mBackIv;
    //center
    private TextView mPathNameTv;
    private String mPathName;

    private RelativeLayout rl_topbar;
    //error
//    private String mReloadMethod;
//    private LinearLayout mErrReloadLl;
//    private TextView mErrInfoTv;

    public PopupWindow mPopupWindow;


    public TopBar(BaseActivity activity) {
        this.mActivity = activity;
        initView();
    }

    public TopBar(BaseActivity activity, View view) {
        this.mActivity = activity;
        this.mView = view;
        initView();
    }
    private View home_bar_view;
    private void initView(){
        initHeadView();
        mBackIv = (ImageView) findViewById(R.id.iv_topbar_back);
        mBackIv.setOnClickListener(this);
        mPathNameTv = (TextView) findViewById(R.id.tv_pathName);
        ViewSetWhithAndHeightUtils.setTextViewMaxWidth(mActivity,mPathNameTv,156,1);
        rl_topbar = (RelativeLayout)findViewById(R.id.rl_topbar);
        home_bar_view =(View)findViewById(R.id.home_bar_view);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { //有透明状态栏
            home_bar_view.setVisibility(View.VISIBLE);
        }else {
            home_bar_view.setVisibility(View.GONE);
        }
//        mErrReloadLl = (LinearLayout) findViewById(R.id.error_reload);
//
//        mErrInfoTv = (TextView) findViewById(R.id.tv_reload_msg);
//        Button errRoadBtn = (Button)findViewById(R.id.btn_reload_button);
//        errRoadBtn.setOnClickListener(this);
    }
    private void initHeadView(){
        int height = Utils.getStatusBarHeight(mActivity);
        DisplayMetrics dm = mActivity.getResources().getDisplayMetrics();

        View home_bar_view = (View)findViewById(R.id.home_bar_view);
        LinearLayout.LayoutParams linearParams =(LinearLayout.LayoutParams) home_bar_view.getLayoutParams();
        linearParams.height = height;// 控件的高强制设成20
        linearParams.width = dm.widthPixels;// 控件的宽强制设成30
        // showShortToast("高度==》"+height+"屏幕宽度==》"+dm.widthPixels);
        home_bar_view.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
    }
    public void setPathName(String name){
        if (mPathNameTv != null) {
            mPathNameTv.setText(name);
        }
    }

    public void setBackBtnVisibility(boolean isVisibility){
        if (isVisibility) {
            mBackIv.setVisibility(View.VISIBLE);
        } else {
            mBackIv.setVisibility(View.GONE);
        }
    }


//    public boolean isErrReload(){
//        LinearLayout ll = (LinearLayout) findViewById(R.id.error_reload);
//        if (ll != null && ll.getVisibility() == View.VISIBLE)
//            return true;
//        return false;
//    }

//    public void doErrReload(AppException e,String methodName){
//        switch (e.getType()) {
//            case AppException.TYPE_NETWORK:
//                Utils.showNetWorkCfgDlg(mActivity);
//                break;
//            default:
//                new AppException().makeToast(mActivity);
//                break;
//        }
//        mPathName = mPathNameTv.getText().toString();
//        setPathName("出错啦");
//
//        if (mAddIv != null)
//            mAddIv.setVisibility(View.GONE);
//        if (mCancelBtn != null) {
//            mBackIv.setVisibility(View.VISIBLE);
//            mCancelBtn.setVisibility(View.GONE);
//        }
//        if (mSubmitBtn != null)
//            mSubmitBtn.setVisibility(View.GONE);
//
//        mReloadMethod = methodName;
//        mErrReloadLl.getLayoutParams().height =
//                AppContext.getInstance().getScreenHeight() - mTopBarRl.getHeight();
//        mErrReloadLl.setVisibility(View.VISIBLE);
//    }

    public View findViewById(int id){
        if (mActivity == null) {
            return null;
        }
        if (mView != null)
            return mView.findViewById(id);
        else
            return mActivity.findViewById(id);
    }

    public View findViewByIdInPopWindow(int id) {
        if (mActivity == null || mPopupWindow == null)
            return null;
        return mPopupWindow.getContentView().findViewById(id);
    }

    public void goBack(){
        if (mActivity != null) {
            if (mActivity instanceof BaseActivity)
                ((BaseActivity)(mActivity)).finishSelf();
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_topbar_back:
                goBack();
                break;
//            case R.id.btn_reload_button:
//            {
//                try {
//                    mPathNameTv.setText(mPathName);
//                    if (mAddIv != null)
//                        mAddIv.setVisibility(View.VISIBLE);
//                    if (mCancelBtn != null) {
//                        mBackIv.setVisibility(View.GONE);
//                        mCancelBtn.setVisibility(View.VISIBLE);
//                    }
//                    if (mSubmitBtn != null)
//                        mSubmitBtn.setVisibility(View.VISIBLE);
//                    mErrReloadLl.getLayoutParams().height = 0;
//                    mErrReloadLl.setVisibility(View.GONE);
//                    Class<?> localClass = mActivity.getClass();
//                    Class<?>[] arrayOfClass = new Class[0];
//                    Method localMethod = localClass.getMethod(this.mReloadMethod, arrayOfClass);
//                    Object[] arrayOfObject = new Object[0];
//                    localMethod.invoke(this.mActivity, arrayOfObject);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//                break;
        }
    }
}
