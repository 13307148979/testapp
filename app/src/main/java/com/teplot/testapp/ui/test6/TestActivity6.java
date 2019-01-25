package com.teplot.testapp.ui.test6;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.teplot.testapp.R;
import com.teplot.testapp.adapter.Test6ImgAdapter;
import com.teplot.testapp.apps.AppData;
import com.teplot.testapp.base.BaseActivity;
import com.teplot.testapp.been.details.FanYiData;
import com.teplot.testapp.been.details.ImgData;
import com.teplot.testapp.been.details.OCROtherData;
import com.teplot.testapp.been.result.OCRDataObj;
import com.teplot.testapp.been.result.OCROtherDataObj1;
import com.teplot.testapp.common.ConfireDlgBottom;
import com.teplot.testapp.common.SingleChoiceListDlg;
import com.teplot.testapp.common.TopBar;
import com.teplot.testapp.utils.FileUtils;
import com.teplot.testapp.utils.ImageUtil;
import com.teplot.testapp.utils.JsonUtil;
import com.teplot.testapp.utils.MD5;
import com.teplot.testapp.utils.PermissionUtils;
import com.teplot.testapp.utils.RealPathFromUriUtils;
import com.teplot.testapp.utils.SortUtils;
import com.teplot.testapp.utils.StringUtil;
import com.teplot.testapp.utils.Utils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;
import top.zibin.luban.OnRenameListener;


public class TestActivity6 extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test6);
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
    private Test6ImgAdapter adapterList;
    private void initListView(){
        listview_main = (ListView) findViewById(R.id.listview_main);
        adapterList = new Test6ImgAdapter(TestActivity6.this,new ArrayList<ImgData>());
        listview_main.setAdapter(adapterList);

        adapterList.setOnItemDetailClickListener(new Test6ImgAdapter.onItemDetailListener() {
            @Override
            public void onClick(int i) {
                ImgData data = adapterList.getItem(i);
                Bitmap bitmap = data.getBitmap();
                Utils.showImgDlgBitmapPhotoView2(TestActivity6.this,bitmap);
            }
        });
    }
    private ImageView img_iv;

    private TextView mLocation_address_tv0,mLocation_address_tv7;

    private void initView(){

        mLocation_address_tv7 = (TextView) findViewById(R.id.location_address_tv7);
        mLocation_address_tv7.setText(sources[0]);

        mLocation_address_tv0 = (TextView) findViewById(R.id.location_address_tv0);
        mLocation_address_tv0.setText(targets[0]);

        img_iv = (ImageView) findViewById(R.id.img_iv);
        img_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cacheFile!=null){
                    Bitmap bitmap = ImageUtil.getLoacalBitmap(cacheFile.getAbsolutePath());
                    Utils.showImgDlgBitmapPhotoView2(TestActivity6.this,bitmap);
                }
            }
        });
    }

    private int[] chooseIDs = {0,0,0,0,0,0,0};
    private boolean[] isChoose = new boolean[7];
    private void showPosReportListDlg(final String[] strings,final String title,final int choose,final TextView bt) {
        if (strings != null && strings.length > 0) {
            SingleChoiceListDlg dlg = new SingleChoiceListDlg.Builder(
                    TestActivity6.this)
                    .setTitle(title)
                    .setSingleChoiceItems(strings, chooseIDs[choose],
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dlg,
                                                    int position) {
                                    isChoose[choose] = true;
                                    chooseIDs[choose] = position;
                                    if (choose==1){
                                        adapterList.removeAllItem();
                                        showData(position);
                                    }
                                    bt.setText(strings[position]);
                                    dlg.dismiss();
                                }
                            }).create();
            dlg.show();
        }
    }

    private String[] sources = {"黛紫","岩井","粉嫩","错觉","暖阳",  "浪漫"   ,"蔷薇","睡莲", "糖果玫瑰","新叶",
            "尤加利","墨","玫瑰初雪","樱桃布丁","白茶","甜薄荷","樱红","圣代","莫斯科","冲绳",
            "粉碧","地中海","首尔","佛罗伦萨","札幌","栀子","东京","昭和","自然","清逸",
            "染","甜美"};

    private String[] setSources(int count){
        sources = new String[count];
        for (int i = 1;i<count+1;i++){
            sources[i-1] = i+"";
        }
        return sources;
    }
    private void showData(int position){
        chooseIDs[0] = 0;
        switch (position){
            case 0:
                sources = new String[]{"黛紫","岩井","粉嫩","错觉","暖阳",  "浪漫"   ,"蔷薇","睡莲", "糖果玫瑰","新叶",
                        "尤加利","墨","玫瑰初雪","樱桃布丁","白茶","甜薄荷","樱红","圣代","莫斯科","冲绳",
                        "粉碧","地中海","首尔","佛罗伦萨","札幌","栀子","东京","昭和","自然","清逸",
                        "染","甜美"};
                mLocation_address_tv7.setText(sources[0]);
                break;
            case 1:
                sources = setSources(65);
                mLocation_address_tv7.setText(sources[0]);
                break;
            case 2:
                sources = new String[]{
                        "日系妆-芭比粉",  "日系妆-清透","日系妆-烟灰","日系妆-自然","日系妆-樱花粉",
                        "日系妆-原宿红","韩妆-闪亮","韩妆-粉紫","韩妆-粉嫩","韩妆-自然",
                        "韩妆-清透","韩妆-大地色", "韩妆-玫瑰","裸妆-自然","裸妆-清透",
                        "裸妆-桃粉","裸妆-橘粉","裸妆-春夏","裸妆-秋冬", "主题妆-经典复古",
                        "主题妆-性感混血","主题妆-炫彩明眸","主题妆-紫色魅惑"};
                mLocation_address_tv7.setText(sources[0]);
                break;
            case 3:
                sources = new String[]{
                        "埃及妆","巴西土著妆","灰姑娘妆","恶魔妆","武媚娘妆",
                        "星光薰衣草","花千骨","僵尸妆","爱国妆","小胡子妆",
                        "美羊羊妆","火影鸣人妆", "刀马旦妆","泡泡妆","桃花妆",
                        "女皇妆","权志龙","撩妹妆","印第安妆","印度妆",
                        "萌兔妆","大圣妆"};
                mLocation_address_tv7.setText(sources[0]);
                break;
            case 4:
                sources = new String[]{
                        "NewDay","欢乐球吃球1","Bonvoyage","Enjoy","ChickenSpring",
                        "ChristmasShow","ChristmasSnow","CircleCat","CircleMouse","CirclePig",
                        "Comicmn","CuteBaby","Envolope","Fairytale","GoodNight",
                        "HalloweenNight","LovelyDay","Newyear2017","PinkSunny","KIRAKIRA",
                        "欢乐球吃球2","SnowWhite","SuperStar","WonderfulWork","Cold",
                        "狼人杀守卫","狼人杀猎人","狼人杀预言家","狼人杀村民","狼人杀女巫",
                        "狼人杀狼人"};
                mLocation_address_tv7.setText(sources[0]);
                break;
            case 5:
                sources = new String[]{"全部"};
                mLocation_address_tv7.setText(sources[0]);
                break;
        }
    }
    private String[] targets = {
            "图片滤镜（天天P图）","图片滤镜（AI Lab）","人脸美妆",
            "人脸变妆", "大头贴","颜龄检测"};
//    private String[] targetUrls = {
//            AppData.URL_OCR_DRIVERLICENSEOCR,AppData.URL_OCR_BIZLICENSEOCR,
//            AppData.URL_OCR_CREDITCARDOCR,
//            AppData.URL_OCR_HANDWRITINGOCR,AppData.URL_OCR_PLATEOCR,AppData.URL_OCR_BCOCR};
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.choose_img_tv:
                showConfire();
                break;
            case R.id.location_address_tv7:
                showPosReportListDlg(sources,"特效效果选择",0,mLocation_address_tv7);
                break;
            case R.id.location_address_tv0:
                showPosReportListDlg(targets,"特效类别选择",1,mLocation_address_tv0);
                break;
            case R.id.submit_send_bt:
                if (cacheFile!=null){
                    showGetData();
                }else {
                    showShortCenterToast("请上传图片");
                }
                break;
        }
    }

    private void showGetData(){
        switch ( chooseIDs[1]){
            case 0:
                getDataImg0((chooseIDs[0]+1),AppData.URL_PTU_IMGFILTER);
                break;
            case 1:
                getDataImg1((chooseIDs[0]+1),AppData.URL_VISION_IMGFILTER);
                break;
            case 2:
                getDataImg2();
                break;
            case 3:
                getDataImg3();
                break;
            case 4:
                getDataImg4();
                break;
            case 5:
                getDataImg5();
                break;
//            case 6:
//                getDataImg2(AppData.URL_OCR_BCOCR);
//                break;
        }
    }

    private void getDataImg0(final int type, String urlPost){
        showProdialog(null, "图片识别中,请稍后...", null);
        long totalMilliSeconds = System.currentTimeMillis();
        long totalSeconds = totalMilliSeconds / 1000;
        long session_id = totalSeconds+123456;
        String image64  = ImageUtil.imageToBase64(cacheFile.getAbsolutePath());

        Map<String, String> map = new HashMap<String, String>();
        map.put("app_id", AppData.TENCENT_AI_APP_ID);
        map.put("time_stamp", totalSeconds+"");
       // map.put("type","1");
        map.put("nonce_str", "fa577ce340859f9fe");
        //map.put("text",text);

        map.put("filter",type+"");

        map.put("image",image64);

        String url = SortUtils.formatUrlParam(map, "utf-8", true);

        String url2 = url+"&app_key="+AppData.TENCENT_AI_APP_KEY;
        showOut("====getDataFenCi=url=="+cacheFile.getAbsolutePath());

        String md5Url = MD5.stringToMD5(url2);

        Log.d("getDataFanYi","====getDataFenCi=image64=="+image64);
       // showOut("====getDataFanYi=sourceId=="+sourceId+"====targetId=====>"+targetId);

        OkHttpUtils
                .post()
                .url(urlPost)
                .addParams("app_id",AppData.TENCENT_AI_APP_ID)
                .addParams("time_stamp",totalSeconds+"")
                .addParams("nonce_str","fa577ce340859f9fe")
                .addParams("sign",md5Url.toUpperCase())
               // .addParams("type","1")
                //.addParams("text",text)
                .addParams("filter",type+"")

               // .addFile("image", cropFile.getName(), cropFile)//test_vidio.mp4
               .addParams("image",image64)

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
                        OCRDataObj result = (OCRDataObj) JsonUtil.jsonToBean(response,OCRDataObj.class);
                       // String target_text = result.getData().getTarget_text();
                        if (result.getRet()==0){
                            // text = "图片内容：\n"+showListData(result.getData().getItem_list());
                            String image = result.getData().getImage();
                            Bitmap bitmap = ImageUtil.stringtoBitmap(image);
                            ImgData data = new ImgData(sources[type-1],bitmap);
                            adapterList.addItem(data);
                            listview_main.setSelection(adapterList.getCount()-1);
                        }else {
                            showShortCenterToast(result.getMsg());
                        }
                    }
                });
    }

    private void getDataImg1(final int type, String urlPost){
        showProdialog(null, "图片识别中,请稍后...", null);
        long totalMilliSeconds = System.currentTimeMillis();
        long totalSeconds = totalMilliSeconds / 1000;
        long session_id = totalSeconds+123456;
        String image64  = ImageUtil.imageToBase64(cacheFile.getAbsolutePath());

        Map<String, String> map = new HashMap<String, String>();
        map.put("app_id", AppData.TENCENT_AI_APP_ID);
        map.put("time_stamp", totalSeconds+"");
        // map.put("type","1");
        map.put("nonce_str", "fa577ce340859f9fe");
        map.put("session_id",session_id+"");

        map.put("filter",type+"");

        map.put("image",image64);

        String url = SortUtils.formatUrlParam(map, "utf-8", true);

        String url2 = url+"&app_key="+AppData.TENCENT_AI_APP_KEY;
        showOut("====getDataFenCi=url=="+cacheFile.getAbsolutePath());

        String md5Url = MD5.stringToMD5(url2);

        Log.d("getDataFanYi","====getDataFenCi=image64=="+image64);
        // showOut("====getDataFanYi=sourceId=="+sourceId+"====targetId=====>"+targetId);

        OkHttpUtils
                .post()
                .url(urlPost)
                .addParams("app_id",AppData.TENCENT_AI_APP_ID)
                .addParams("time_stamp",totalSeconds+"")
                .addParams("nonce_str","fa577ce340859f9fe")
                .addParams("sign",md5Url.toUpperCase())
                // .addParams("type","1")
                .addParams("session_id",session_id+"")
                .addParams("filter",type+"")

                // .addFile("image", cropFile.getName(), cropFile)//test_vidio.mp4
                .addParams("image",image64)

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
                        OCRDataObj result = (OCRDataObj) JsonUtil.jsonToBean(response,OCRDataObj.class);
                        // String target_text = result.getData().getTarget_text();
                        if (result.getRet()==0){
                            // text = "图片内容：\n"+showListData(result.getData().getItem_list());
                            String image = result.getData().getImage();
                            Bitmap bitmap = ImageUtil.stringtoBitmap(image);
                            ImgData data = new ImgData(sources[type-1],bitmap);
                            adapterList.addItem(data);
                            listview_main.setSelection(adapterList.getCount()-1);
                        }else {
                            showShortCenterToast(result.getMsg());
                        }
                    }
                });
    }


    private void getDataImg2(){
        showProdialog(null, "图片识别中,请稍后...", null);
        long totalMilliSeconds = System.currentTimeMillis();
        long totalSeconds = totalMilliSeconds / 1000;
        long session_id = totalSeconds+123456;
        String image64  = ImageUtil.imageToBase64(cacheFile.getAbsolutePath());

        Map<String, String> map = new HashMap<String, String>();
        map.put("app_id", AppData.TENCENT_AI_APP_ID);
        map.put("time_stamp", totalSeconds+"");
        // map.put("type","1");
        map.put("nonce_str", "fa577ce340859f9fe");
       // map.put("session_id",session_id+"");

        map.put("cosmetic",(chooseIDs[0]+1)+"");

        map.put("image",image64);

        String url = SortUtils.formatUrlParam(map, "utf-8", true);

        String url2 = url+"&app_key="+AppData.TENCENT_AI_APP_KEY;
        showOut("====getDataFenCi=url=="+cacheFile.getAbsolutePath());

        String md5Url = MD5.stringToMD5(url2);

        Log.d("getDataFanYi","====getDataFenCi=image64=="+image64);
        // showOut("====getDataFanYi=sourceId=="+sourceId+"====targetId=====>"+targetId);

        OkHttpUtils
                .post()
                .url(AppData.URL_PTU_FACECOSMETIC)
                .addParams("app_id",AppData.TENCENT_AI_APP_ID)
                .addParams("time_stamp",totalSeconds+"")
                .addParams("nonce_str","fa577ce340859f9fe")
                .addParams("sign",md5Url.toUpperCase())
                // .addParams("type","1")
               // .addParams("session_id",session_id+"")
                .addParams("cosmetic",(chooseIDs[0]+1)+"")

                // .addFile("image", cropFile.getName(), cropFile)//test_vidio.mp4
                .addParams("image",image64)

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
                        OCRDataObj result = (OCRDataObj) JsonUtil.jsonToBean(response,OCRDataObj.class);
                        // String target_text = result.getData().getTarget_text();
                        if (result.getRet()==0){
                            // text = "图片内容：\n"+showListData(result.getData().getItem_list());
                            String image = result.getData().getImage();
                            Bitmap bitmap = ImageUtil.stringtoBitmap(image);
                            ImgData data = new ImgData(sources[chooseIDs[0]],bitmap);
                            adapterList.addItem(data);
                            listview_main.setSelection(adapterList.getCount()-1);
                        }else {
                            showShortCenterToast(result.getMsg());
                        }
                    }
                });
    }

    private void getDataImg3(){
        showProdialog(null, "图片识别中,请稍后...", null);
        long totalMilliSeconds = System.currentTimeMillis();
        long totalSeconds = totalMilliSeconds / 1000;
        long session_id = totalSeconds+123456;
        String image64  = ImageUtil.imageToBase64(cacheFile.getAbsolutePath());

        Map<String, String> map = new HashMap<String, String>();
        map.put("app_id", AppData.TENCENT_AI_APP_ID);
        map.put("time_stamp", totalSeconds+"");
        // map.put("type","1");
        map.put("nonce_str", "fa577ce340859f9fe");
        // map.put("session_id",session_id+"");

        map.put("decoration",(chooseIDs[0]+1)+"");

        map.put("image",image64);

        String url = SortUtils.formatUrlParam(map, "utf-8", true);

        String url2 = url+"&app_key="+AppData.TENCENT_AI_APP_KEY;
        showOut("====getDataFenCi=url=="+cacheFile.getAbsolutePath());

        String md5Url = MD5.stringToMD5(url2);

        Log.d("getDataFanYi","====getDataFenCi=image64=="+image64);
        // showOut("====getDataFanYi=sourceId=="+sourceId+"====targetId=====>"+targetId);

        OkHttpUtils
                .post()
                .url(AppData.URL_PTU_FACEDECORATION)
                .addParams("app_id",AppData.TENCENT_AI_APP_ID)
                .addParams("time_stamp",totalSeconds+"")
                .addParams("nonce_str","fa577ce340859f9fe")
                .addParams("sign",md5Url.toUpperCase())
                // .addParams("type","1")
                // .addParams("session_id",session_id+"")
                .addParams("decoration",(chooseIDs[0]+1)+"")

                // .addFile("image", cropFile.getName(), cropFile)//test_vidio.mp4
                .addParams("image",image64)

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
                        OCRDataObj result = (OCRDataObj) JsonUtil.jsonToBean(response,OCRDataObj.class);
                        // String target_text = result.getData().getTarget_text();
                        if (result.getRet()==0){
                            // text = "图片内容：\n"+showListData(result.getData().getItem_list());
                            String image = result.getData().getImage();
                            Bitmap bitmap = ImageUtil.stringtoBitmap(image);
                            ImgData data = new ImgData(sources[chooseIDs[0]],bitmap);
                            adapterList.addItem(data);
                            listview_main.setSelection(adapterList.getCount()-1);
                        }else {
                            showShortCenterToast(result.getMsg());
                        }
                    }
                });
    }

    private void getDataImg4(){
        showProdialog(null, "图片识别中,请稍后...", null);
        long totalMilliSeconds = System.currentTimeMillis();
        long totalSeconds = totalMilliSeconds / 1000;
        long session_id = totalSeconds+123456;
        String image64  = ImageUtil.imageToBase64(cacheFile.getAbsolutePath());

        Map<String, String> map = new HashMap<String, String>();
        map.put("app_id", AppData.TENCENT_AI_APP_ID);
        map.put("time_stamp", totalSeconds+"");
        // map.put("type","1");
        map.put("nonce_str", "fa577ce340859f9fe");
        // map.put("session_id",session_id+"");

        map.put("sticker",(chooseIDs[0]+1)+"");

        map.put("image",image64);

        String url = SortUtils.formatUrlParam(map, "utf-8", true);

        String url2 = url+"&app_key="+AppData.TENCENT_AI_APP_KEY;
        showOut("====getDataFenCi=url=="+cacheFile.getAbsolutePath());

        String md5Url = MD5.stringToMD5(url2);

        Log.d("getDataFanYi","====getDataFenCi=image64=="+image64);
        // showOut("====getDataFanYi=sourceId=="+sourceId+"====targetId=====>"+targetId);

        OkHttpUtils
                .post()
                .url(AppData.URL_PTU_FACESTICKER)
                .addParams("app_id",AppData.TENCENT_AI_APP_ID)
                .addParams("time_stamp",totalSeconds+"")
                .addParams("nonce_str","fa577ce340859f9fe")
                .addParams("sign",md5Url.toUpperCase())
                // .addParams("type","1")
                // .addParams("session_id",session_id+"")
                .addParams("sticker",(chooseIDs[0]+1)+"")

                // .addFile("image", cropFile.getName(), cropFile)//test_vidio.mp4
                .addParams("image",image64)

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
                        OCRDataObj result = (OCRDataObj) JsonUtil.jsonToBean(response,OCRDataObj.class);
                        // String target_text = result.getData().getTarget_text();
                        if (result.getRet()==0){
                            // text = "图片内容：\n"+showListData(result.getData().getItem_list());
                            String image = result.getData().getImage();
                            Bitmap bitmap = ImageUtil.stringtoBitmap(image);
                            ImgData data = new ImgData(sources[chooseIDs[0]],bitmap);
                            adapterList.addItem(data);
                            listview_main.setSelection(adapterList.getCount()-1);
                        }else {
                            showShortCenterToast(result.getMsg());
                        }
                    }
                });
    }

    private void getDataImg5(){
        showProdialog(null, "图片识别中,请稍后...", null);
        long totalMilliSeconds = System.currentTimeMillis();
        long totalSeconds = totalMilliSeconds / 1000;
        long session_id = totalSeconds+123456;
        String image64  = ImageUtil.imageToBase64(cacheFile.getAbsolutePath());

        Map<String, String> map = new HashMap<String, String>();
        map.put("app_id", AppData.TENCENT_AI_APP_ID);
        map.put("time_stamp", totalSeconds+"");
        // map.put("type","1");
        map.put("nonce_str", "fa577ce340859f9fe");
        // map.put("session_id",session_id+"");

        //map.put("sticker",(chooseIDs[0]+1)+"");

        map.put("image",image64);

        String url = SortUtils.formatUrlParam(map, "utf-8", true);

        String url2 = url+"&app_key="+AppData.TENCENT_AI_APP_KEY;
        showOut("====getDataFenCi=url=="+cacheFile.getAbsolutePath());

        String md5Url = MD5.stringToMD5(url2);

        Log.d("getDataFanYi","====getDataFenCi=image64=="+image64);
        // showOut("====getDataFanYi=sourceId=="+sourceId+"====targetId=====>"+targetId);

        OkHttpUtils
                .post()
                .url(AppData.URL_PTU_FACEAGE)
                .addParams("app_id",AppData.TENCENT_AI_APP_ID)
                .addParams("time_stamp",totalSeconds+"")
                .addParams("nonce_str","fa577ce340859f9fe")
                .addParams("sign",md5Url.toUpperCase())
                // .addParams("type","1")
                // .addParams("session_id",session_id+"")
               // .addParams("sticker",(chooseIDs[0]+1)+"")

                // .addFile("image", cropFile.getName(), cropFile)//test_vidio.mp4
                .addParams("image",image64)

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
                        OCRDataObj result = (OCRDataObj) JsonUtil.jsonToBean(response,OCRDataObj.class);
                        // String target_text = result.getData().getTarget_text();
                        if (result.getRet()==0){
                            // text = "图片内容：\n"+showListData(result.getData().getItem_list());
                            String image = result.getData().getImage();
                            Bitmap bitmap = ImageUtil.stringtoBitmap(image);
                            ImgData data = new ImgData(sources[chooseIDs[0]],bitmap);
                            adapterList.addItem(data);
                            listview_main.setSelection(adapterList.getCount()-1);
                        }else {
                            showShortCenterToast(result.getMsg());
                        }
                    }
                });
    }
    //选择图片的方式
    private void showConfire(){
        ConfireDlgBottom confireDlgBottom = new ConfireDlgBottom.
                Builder(this).
                setOneButton("从相册中选择", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //showShortToast("获取相册图片");
                        dialog.dismiss();
                        showPermissionCONTENT(1);
                        // showPermissionCONTENT();
                    }
                }).setTwoButton("拍照", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // showShortToast("获取拍照图片");
                dialog.dismiss();
                showASK_CAMERA();
                ///showPermissionCAMERA();
            }
        }).setThreeButton("取消",null).create();
        confireDlgBottom.show();
    }

    // 拍照临时保存地址
    private Uri imageUri;
    private void getCAMERA(){
        // 拍照原图保存地址
        cacheFile = FileUtils.createFile(AppData.PICTURE_PATH,
                System.currentTimeMillis() + ".jpg");
        if (cacheFile == null) {
            showLongToast("无SD卡");
            return;
        }
        // 拍照原图保存地址
        Intent intent = new Intent(
                MediaStore.ACTION_IMAGE_CAPTURE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            //通过FileProvider创建一个content类型的Uri
            imageUri = FileProvider.getUriForFile(this, "com.teplot.testapp.fileprovider", cacheFile);
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }else {
            imageUri = Uri.fromFile(cacheFile);
        }
        // 调用拍照
        // showShortToast("图片地址为==》"+imageUri);
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);//设置Action为拍照
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);//将拍取的照片保存到指定URI
        startActivityForResult(intent, REQUESTCODE_CAMERA);
    }
    private void getCONTENT(){
        cacheFile = FileUtils.createFile(AppData.PICTURE_PATH,
                System.currentTimeMillis() + ".jpg");
        if (cacheFile == null) {
            showLongToast("无SD卡");
            return;
        }
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUESTCODE_ALBUM);
    }

    // 头像图片
    private final int REQUESTCODE_CAMERA = 0;
    private final int REQUESTCODE_ALBUM = 1;
    private final int REQUESTCODE_IMAGE_CUT = 2;
    private final int DETAIL_RESULT_DATA = 3;
    private final int REQUESTCODE_PERMISSIONS_CODE = 4;
    // 拍照临时保存地址
    private File cacheFile;
    // 有些手机拍好图片会旋转，裁剪出来也是横的，需要处理
    private int degree;
    private final int INTENT_INPUT_NAME = 6;
    private Bitmap bitmap;
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            // 拍照返回
//            case REQUESTCODE_CAMERA:
//                if (resultCode == Activity.RESULT_OK) {
//                    degree = Utils.readPictureDegree(cacheFile.getAbsolutePath());
//                    // showLongToast("degree的值为====》"+degree);
//                    startImageAction(imageUri, 256, 256,
//                            REQUESTCODE_IMAGE_CUT, true);
//                }
//                break;
//
//            // 相册返回
//            case REQUESTCODE_ALBUM:
//                if (resultCode == Activity.RESULT_OK) {
//                    Uri uri = data.getData();
//                    degree = 0;
//                    startImageAction(uri, 256, 256, REQUESTCODE_IMAGE_CUT, true);
//                }
//                break;
//            // 剪裁图片返回
//            case REQUESTCODE_IMAGE_CUT:
//                //删除cache_img
//                FileUtil.deleteFile2(cacheFile.getAbsolutePath());
//                if (resultCode == Activity.RESULT_OK) {
//                    Bitmap bitmap = ImageUtil.getLoacalBitmap(cropFile.getAbsolutePath());
//                    img_iv.setImageBitmap(bitmap);
////                    try {
////                        head_img.setImageBitmap(MediaStore.Images.Media.getBitmap(getContentResolver(),cropUri));
////                    } catch (IOException e) {
////                        e.printStackTrace();
////                    }
//                }
//                break;
//            // 拍照返回
            case REQUESTCODE_CAMERA:
                if (resultCode == Activity.RESULT_OK) {
                    // showLongToast("degree的值为====》"+degree);
                    sendPicture(cacheFile.getAbsolutePath());
                    // sendFile();
                }
                break;

            // 相册返回
            case REQUESTCODE_ALBUM:
                if (resultCode == Activity.RESULT_OK) {
                    if (data != null) {
                        String realPathFromUri = RealPathFromUriUtils.getRealPathFromUri(this, data.getData());
                        sendPicture(realPathFromUri);
                    } else {
                        Toast.makeText(this, "图片损坏，请重新选择", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }
//    private File cropFile;
//    private Uri  cropUri;
    /**
     *
     * @param uri
     *            文件源
     * @param outputX
     * @param outputY
     * @param requestCode
     * @param isCrop
     */
//    private void startImageAction(Uri uri, int outputX, int outputY,
//                                  int requestCode, boolean isCrop) {
//
//        cropFile = FileUtils.createFile(AppData.PICTURE_PATH,
//                "head_img.jpg");
//        if (cropFile == null) {
//            showLongToast("无SD卡");
//            return;
//        }
//        Intent intent = null;
//        if (isCrop) {
//            intent = new Intent("com.android.camera.action.CROP");
//            //showLongToast("isCrop的值为====》"+isCrop);
//            // intent.setClassName("com.android.camera","com.android.camera.CropImage");
//        } else {
//            intent = new Intent(Intent.ACTION_GET_CONTENT, null);
//        }
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//        }
//        cropUri= Uri.fromFile(cropFile);
//        intent.setDataAndType(uri, "image/*");
//        intent.putExtra("crop", "true");
//        intent.putExtra("aspectX", 1);// 剪裁比例
//        intent.putExtra("aspectY", 1);
//        intent.putExtra("outputX", outputX);// 输出大小
//        intent.putExtra("outputY", outputY);
//        intent.putExtra("scale", true);// 允许伸缩
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, cropUri);// 输出到哪个文件
//        intent.putExtra("return-data", true);// false返回uri
//        intent.putExtra("outputFormat", "JPEG");// 输出格式
//        intent.putExtra("noFaceDetection", true); // 没有人脸检测
//        startActivityForResult(intent, requestCode);
//    }
    /**
     * 压缩并发送图片
     *
     * @param filePath
     */
    private void sendPicture(final String filePath) {

        try {
            //这里上传图片数据
            int degree = Utils.readPictureDegree(filePath);
            //这里上传图片数据
            Bitmap map = ImageUtil.getLoacalBitmap(filePath);
            Bitmap bitmap = Utils.rotaingImageView(degree, map);

            ImageUtil.saveImage(TestActivity6.this,
                    AppData.PICTURE_PATH, cacheFile.getName(), bitmap);
            File imgFile= new File(filePath);
            String path = AppData.SD_PATH + AppData.PICTURE_PATH;
            Luban.with(this)
                    .load(imgFile)
                    .ignoreBy(100)
                    .setTargetDir(path)
                    .filter(new CompressionPredicate() {
                        @Override
                        public boolean apply(String path) {
                            return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
                        }
                    })
                    .setRenameListener(new OnRenameListener() {
                        @Override
                        public String rename(String filePath) {
                            return cacheFile.getName();
                        }
                    })
                    .setCompressListener(new OnCompressListener() {
                        @Override
                        public void onStart() {
                            // TODO 压缩开始前调用，可以在方法内启动 loading UI
                        }
                        @Override
                        public void onSuccess(File file) {
                            // TODO 压缩成功后调用，返回压缩后的图片文件
                            Bitmap bitmap = ImageUtil.getLoacalBitmap(cacheFile.getAbsolutePath());
                            img_iv.setImageBitmap(bitmap);
                            //new Handler().postDelayed(runnable,400);
                        }
                        @Override
                        public void onError(Throwable e) {
                            // TODO 当压缩过程出现问题时调用
                            showShortCenterToast("图片压缩失败，请重新上传！");
                        }
                    }).launch();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //权限的获取
    public void showASK_CAMERA() {
        PermissionUtils.requestPermission(TestActivity6.this, PermissionUtils.CODE_CAMERA, mPermissionGrant);
    }
    private int num;
    public void showPermissionCONTENT(int i) {
        num = i;
        PermissionUtils.requestPermission(TestActivity6.this, PermissionUtils.CODE_WRITE_EXTERNAL_STORAGE,
                mPermissionGrant);
    }

    private PermissionUtils.PermissionGrant mPermissionGrant = new PermissionUtils.PermissionGrant() {
        @Override
        public void onPermissionGranted(int requestCode) {
            switch (requestCode) {

                case PermissionUtils.CODE_CAMERA:
                    showPermissionCONTENT(2);
                    break;
                case PermissionUtils.CODE_WRITE_EXTERNAL_STORAGE:
                    switch (num){
                        case 1:
                            //相册选择
                            getCONTENT();
                            break;
                        case 2:
                            //相机选择
                            getCAMERA();
                            break;
                    }
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
       PermissionUtils.requestPermissionsResult(this, requestCode, permissions, grantResults, mPermissionGrant);
    }

}
