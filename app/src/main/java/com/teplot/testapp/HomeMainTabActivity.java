package com.teplot.testapp;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.gyf.barlibrary.ImmersionBar;

import com.teplot.testapp.adapter.MainMeListAdapter;
import com.teplot.testapp.apps.AppContext;
import com.teplot.testapp.been.details.LoginInfo;
import com.teplot.testapp.been.details.MainMeData;
import com.teplot.testapp.common.CircleImageView;
import com.teplot.testapp.common.FixedSpeedScroller;
import com.teplot.testapp.main.HomeMainActivity1;
import com.teplot.testapp.main.HomeMainActivity2;
import com.teplot.testapp.main.HomeMainActivity3;
import com.teplot.testapp.main.HomeMainActivity4;
import com.teplot.testapp.ui.test0.TestActivity0;
import com.teplot.testapp.ui.test1.TestActivity;
import com.teplot.testapp.ui.test1.TestActivity1;
import com.teplot.testapp.ui.test2.TestActivity2;
import com.teplot.testapp.ui.test3.TestActivity3;
import com.teplot.testapp.ui.test4.TestActivity4;
import com.teplot.testapp.ui.test5.TestActivity5;
import com.teplot.testapp.ui.test6.TestActivity6;
import com.teplot.testapp.ui.test7.TestActivity7;
import com.teplot.testapp.utils.ImageLoaderUtil;
import com.teplot.testapp.utils.PermissionUtils;
import com.teplot.testapp.utils.Utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class HomeMainTabActivity extends TabActivity {

    private LinearLayout morell,moreMsgLL,homeTabLL;
    private ViewPager vp;
    private TabHost mTabHost;
    public AppContext mApplication;
    private String mTextviewArray[] = { "讯飞语音", "首页2","首页3", "首页4" };
    // 定义数组来存放按钮图片
    private int mImageViewArray[] = { R.drawable.tab_map_btn,R.drawable.tab_message_btn,
            R.drawable.tab_set_btn,
            R.drawable.tab_more_btn };
    // 四个按钮对应的类
    private Class fragmentArray[] = { HomeMainActivity1.class,
            HomeMainActivity2.class, HomeMainActivity3.class, HomeMainActivity4.class};

    private Handler mHandler;
    private LoginInfo user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_tap);
        mApplication = (AppContext) getApplication();
        user =  mApplication.getLoginMsg2();
        mHandler = new Handler();
        ImmersionBar mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.init();
        initHomeTab();
        initHeadView();
        initView();
        showPermissionCONTENT();
    }
    private void initView(){
        morell = (LinearLayout)findViewById(R.id.home_tab_ll);
        moreMsgLL = (LinearLayout)findViewById(R.id.rl_moreMsg);
        moreMsgLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl_moreClick();
            }
        });
    }
    private void initHeadView(){
        int height = Utils.getStatusBarHeight(HomeMainTabActivity.this);
        DisplayMetrics dm = getResources().getDisplayMetrics();

        View home_bar_view = (View)findViewById(R.id.home_bar_view);
        LinearLayout.LayoutParams linearParams =(LinearLayout.LayoutParams) home_bar_view.getLayoutParams();
        linearParams.height = height;// 控件的高强制设成20
        linearParams.width = dm.widthPixels;// 控件的宽强制设成30
        // showShortToast("高度==》"+height+"屏幕宽度==》"+dm.widthPixels);
        home_bar_view.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
    }
    public void llClick(View view) {
        moreClick();
    }
    TabHost.TabSpec tabSpec;
    List<View> list=new ArrayList<View>();

    //老师界面的tab
    private void initHomeTab() {
        // TODO Auto-generated method stub
        mTabHost = this.getTabHost();
        for (int i = 0; i < mImageViewArray.length; i++) {
            list.add(getTabItemView(i));
        }
        for (int i = 0; i < mImageViewArray.length; i++) {
            // 为每一个Tab按钮设置图标、文字和内容
            tabSpec = mTabHost
                    .newTabSpec(mTextviewArray[i])
                    .setIndicator(list.get(i))
                    .setContent(
                            new Intent(HomeMainTabActivity.this,
                                    fragmentArray[i]));
            // 将Tab按钮添加进Tab选项卡中
            mTabHost.addTab(tabSpec);
            mTabHost.setCurrentTab(0);
        }
        for (int j = 0; j < 4; j++) {
            final int indext=j;
            //final int indext2 =  mTabHost.getCurrentTab();
            mTabHost.getTabWidget().getChildAt(j).
                    setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mTabHost.setCurrentTab(indext);
                        }
                    });
        }
    }

    private View getTabItemView(int index) {
        View view = getLayoutInflater().inflate(R.layout.tab_item_view, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.tab_item_iv);
        TextView textView = (TextView) view.findViewById(R.id.tab_item_tv);

        imageView.setImageResource(mImageViewArray[index]);
        textView.setText(mTextviewArray[index]);

        return view;
    }

    private ListView listview_patrol_content;
    private MainMeListAdapter adapter;
    private CircleImageView user_img_iv;
    private TextView name_tv,content_tv;

    private Class gvItemClass[] = {
            TestActivity0.class,
            TestActivity.class,
            TestActivity2.class,
            TestActivity3.class,

            TestActivity4.class,
            TestActivity5.class,
            TestActivity6.class,
            TestActivity7.class};
    private void initListView(View view){

        //用户相关
        user_img_iv = (CircleImageView)view.findViewById(R.id.user_img_iv);


        //user_img_iv.setImageResource(R.drawable.user_img);

        name_tv = (TextView) view.findViewById(R.id.name_tv);
        content_tv =  (TextView) view.findViewById(R.id.content_tv);

        setData();
        listview_patrol_content = (ListView)view.findViewById(R.id.listview_patrol_content);
        adapter = new MainMeListAdapter(HomeMainTabActivity.this,getListData());
        listview_patrol_content.setAdapter(adapter);
        listview_patrol_content.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MainMeData data = adapter.getItem(position);

                adapter.setChooseItem(position);
                adapter.notifyDataSetChanged();

                Intent intent= new Intent(HomeMainTabActivity.this,gvItemClass[position]);
                intent.putExtra("title",data.getTitle());
                startActivity(intent);

            }
        });

    }
    private void setData(){
        user_img_iv.setImageResource(R.drawable.logo);
        name_tv.setText("智能小A");
        content_tv.setText("我是智能小A，不要小瞧我哟！");
    }
    private ArrayList<MainMeData> getListData(){
        ArrayList<MainMeData> list = new ArrayList<MainMeData>();
//        MainMeData data1 = new MainMeData("基础文本",R.drawable.shoucang_img2,R.drawable.listview_cehua_bg1,"喜欢的全在这里");
//        list.add(data1);
        MainMeData data2 = new MainMeData("语义解析",R.drawable.zhuanghu_img2,R.drawable.listview_cehua_bg2,"完善账户资料，保护帐号安全");
        list.add(data2);
        MainMeData data3 = new MainMeData("机器翻译",R.drawable.ziliao_img2,R.drawable.listview_cehua_bg3,"完善账户资料，让别人更多的了解你");
        list.add(data3);
        MainMeData data4 = new MainMeData("智能闲聊",R.drawable.longchang_img2,R.drawable.listview_cehua_bg4,"更好的管理自己的农场");
        list.add(data4);
        MainMeData data5 = new MainMeData("图片识别",R.drawable.shezhi_img2,R.drawable.listview_cehua_bg5,"系统功能偏好设置");
        list.add(data5);

        MainMeData data6 = new MainMeData("敏感信息审核",R.drawable.guanyu_img2,R.drawable.listview_cehua_bg6,"APP操作手册及疑问解答");
        list.add(data6);
        MainMeData data7 = new MainMeData("OCR相关",R.drawable.guanyu_img2,R.drawable.listview_cehua_bg6,"APP操作手册及疑问解答");
        list.add(data7);
        MainMeData data8 = new MainMeData("图片特效",R.drawable.guanyu_img2,R.drawable.listview_cehua_bg6,"APP操作手册及疑问解答");
        list.add(data8);
        MainMeData data9 = new MainMeData("人脸识别",R.drawable.guanyu_img2,R.drawable.listview_cehua_bg6,"APP操作手册及疑问解答");
        list.add(data9);

//        MainMeData data10 = new MainMeData("语音合成",R.drawable.guanyu_img2,R.drawable.listview_cehua_bg6,"APP操作手册及疑问解答");
//        list.add(data10);
//        MainMeData data11 = new MainMeData("语音识别",R.drawable.guanyu_img2,R.drawable.listview_cehua_bg6,"APP操作手册及疑问解答");
//        list.add(data11);

        return list;
    };

    private List<View> viewList;

    private ImageView img_red_iv2;
    /**
     * 初始化伪装成侧滑菜单的viewpage
     */
    private void initViewPage() {
        View view = View.inflate(HomeMainTabActivity.this,
                R.layout.home_tab_listview, null);
        initListView(view);
        viewList = new ArrayList<View>();
        viewList.add(view);
        viewList.add(new TextView(HomeMainTabActivity.this));

        vp = (ViewPager)findViewById(R.id.home_tab_vPager);
        vp.addOnPageChangeListener(new MyOnPageChangeListener());
        vp.setAdapter(new MyPagerAdapter(viewList));
        vp.setOffscreenPageLimit(3);
        vp.setCurrentItem(0);
        vp.setCurrentItem(1);

        // 设置viewpage滑动时间
        try {
            Field mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            FixedSpeedScroller scroller = new FixedSpeedScroller(
                    vp.getContext());
            scroller.setmDuration(600);
            mScroller.set(vp, scroller);
        } catch (Exception e) {
        }
    }


    /**
     * 子页面点击退出
     *
     * @return
     */
    public boolean chileClick() {
        boolean b = isMenuOpen;
        if (isMenuOpen)
            moreClick();
        return !b;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImageLoaderUtil.getInstances().clear();
    }
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        //拦截返回键
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK){
            //判断触摸UP事件才会进行返回事件处理
            if (event.getAction() == KeyEvent.ACTION_UP) {
                onBackPressed();
            }
            //只要是返回事件，直接返回true，表示消费掉
            return true;
        }
        return super.dispatchKeyEvent(event);
    }
    /**
     * 退出标识
     */
    private boolean exitflag;
    /**
     * 返回按键事件
     */
    @Override
    public void onBackPressed() {
        if (chileClick()) {
            if (exitflag == true) {
                mApplication.exitApp();
                return;
            }
            exitflag = true;
            Toast.makeText(this, "再按一次退出!", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    exitflag = false;
                }
            }, 2000);
        }
    }
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.content_tv:
//                Intent intent= new Intent(HomeMainTabActivity.this,UserCenterActivity1.class);
//                intent.putExtra("title","个人资料");
//                startActivityForResult(intent,REQUESTCODE_RESULT_CODE);
                //Toast.makeText(this, "修改内容", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    /**
     * 适配器
     *
     * @author Administrator
     *
     */
    public class MyPagerAdapter extends PagerAdapter {
        List<View> data;

        MyPagerAdapter(List<View> listView) {
            data = listView;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return data.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            ((ViewPager) container).addView(data.get(position));
            return data.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // ((ViewPager) container).removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            // TODO Auto-generated method stub
            return arg0 == arg1;
        }
    };
    /**
     * 菜单栏是否显示
     */
    private boolean isMenuOpen;
    private boolean isStarAnima;
    public void rl_moreClick(){
        // showShortToast("点击侧滑栏滑出");
        isStarAnima = false;
        if (!isMenuOpen) {
            morell.setBackgroundColor(Color.parseColor("#00000000"));
            morell.setVisibility(View.VISIBLE);
            isMenuOpen = true;
            morell.setClickable(true);
            vp.setCurrentItem(0);
            setData();
        } else {
            isMenuOpen = false;
            //设置不可点击
            morell.setClickable(false);
            vp.setCurrentItem(1);
        }
    }
    public void moreClick() {
        isStarAnima = false;
        if (!isMenuOpen) {
            morell.setBackgroundColor(Color.parseColor("#00000000"));
            morell.setVisibility(View.VISIBLE);
            isMenuOpen = true;
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    morell.setClickable(true);
                    vp.setCurrentItem(0);
                }
            }, 100);

        } else {
            isMenuOpen = false;
            morell.setClickable(false);
            vp.setCurrentItem(1);
        }
    }
    /**
     * 监听器，实现了创意动画
     *
     * @author Administrator
     *
     */
    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        int nowpage;
        int v2oldR = -180, v1oldR = -135, v3oldR = -225;
        float v1oldS = 0.74f, v3oldS = 0.74f;

        @Override
        public void onPageScrollStateChanged(int arg0) {
            // 状态有三个0空闲，1是正在滑行中，2目标加载完毕
            // Log.v("song", arg0 + "加载完毕");
            if (arg0 == 0 || arg0 == 1)
                isStarAnima = true;
            if (arg0 == 0 && nowpage == 1 && isMenuOpen == false) {
                morell.setVisibility(View.GONE);
                isMenuOpen = false;
            }
            if (arg0 == 0) {
                v2oldR = -180;
                v1oldR = -135;
                v3oldR = -225;
                v1oldS = 0.74f;
                v3oldS = 0.74f;
                // 更新头像
//                if (isChangeIcon) {
//                    isChangeIcon = false;
//                    listAdapter.notifyDataSetChanged();
//                    //setChildMsg();
//                }
            }

        }
        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            /*
             * arg0 :往右滑动时 表示当前页面 （0→1 显示0）往回滑动时，显示滑动后页面（1→0 显示0）
             * arg1:当前页面偏移的百分比，往右滑0-1 ；往左滑1-0； arg2:当前页面偏移的像素位置 可以拿来设置动画
             */

            morell.setBackgroundColor(Color.argb(
                    (int) (128 * (1 - arg0 - arg1)), 0x00, 0x00, 0x00));
        }
        @Override
        public void onPageSelected(int arg0) {
            // Log.v("song", arg0 + "当前叶卡");
            if ((nowpage = arg0) == 1){
                isMenuOpen = false;
            }
        }
    }

    //此界面需要读写权限
    public void showPermissionCONTENT() {
        PermissionUtils.requestPermission(HomeMainTabActivity.this, PermissionUtils.CODE_WRITE_EXTERNAL_STORAGE,
                mPermissionGrant);
    }

    private PermissionUtils.PermissionGrant mPermissionGrant = new PermissionUtils.PermissionGrant() {
        @Override
        public void onPermissionGranted(int requestCode) {
            switch (requestCode) {
                case PermissionUtils.CODE_WRITE_EXTERNAL_STORAGE:
                    Utils.setImageLoaderUtilInit();
                    initViewPage();
                    break;
                default:
                    break;
            }
        }
    };

    private final int REQUESTCODE_PERMISSIONS_CODE= 0;
    private final int REQUESTCODE_RESULT_CODE= 1;
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUESTCODE_PERMISSIONS_CODE:
                showPermissionCONTENT();
                break;
            case REQUESTCODE_RESULT_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    //刷新数据
                    setData();
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
