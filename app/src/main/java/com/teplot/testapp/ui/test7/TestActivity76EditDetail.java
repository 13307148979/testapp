package com.teplot.testapp.ui.test7;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.DragAndDropPermissions;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.teplot.testapp.R;
import com.teplot.testapp.apps.AppContext;
import com.teplot.testapp.apps.AppData;
import com.teplot.testapp.base.BaseActivity;
import com.teplot.testapp.been.details.FaceData;
import com.teplot.testapp.been.details.FacePersonData;
import com.teplot.testapp.been.details.FacePersonDetailData;
import com.teplot.testapp.been.details.OCROtherData;
import com.teplot.testapp.been.result.FaceDataInfoObj;
import com.teplot.testapp.been.result.FaceDetailDataObj;
import com.teplot.testapp.common.ConfireDlgBottom;
import com.teplot.testapp.common.SingleChoiceListDlg;
import com.teplot.testapp.common.TopBar;
import com.teplot.testapp.db.FacePersonDB;
import com.teplot.testapp.gridview.GridViewAdapter;
import com.teplot.testapp.gridview.GridViewItem;
import com.teplot.testapp.gridview.SendGridView;
import com.teplot.testapp.utils.FileUtil;
import com.teplot.testapp.utils.FileUtils;
import com.teplot.testapp.utils.ImageUtil;
import com.teplot.testapp.utils.JsonUtil;
import com.teplot.testapp.utils.MD5;
import com.teplot.testapp.utils.PermissionUtils;
import com.teplot.testapp.utils.RealPathFromUriUtils;
import com.teplot.testapp.utils.SortUtils;
import com.teplot.testapp.utils.StringUtil;
import com.teplot.testapp.utils.Utils;
import com.teplot.testapp.utils.ViewSetWhithAndHeightUtils;
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


public class TestActivity76EditDetail extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test76_edit_detail);
        setImmersionBarNon();
        initHeadView();
        initView();
        initNameView();
        initTagView();

        initGridView();

        //getDetailData();
        initData();
    }


    private String person_id;

    private View home_bar_view;
    private ImageView mBackIv;
    private TextView mPathNameTv;
    private void initHeadView() {
        Intent intent = getIntent();
        person_id = intent.getStringExtra("person_id");

        home_bar_view =(View)findViewById(R.id.home_bar_view);
        Utils.initStatusBarHeight(TestActivity76EditDetail.this,home_bar_view);

        mBackIv = (ImageView) findViewById(R.id.iv_topbar_back);
        mPathNameTv = (TextView) findViewById(R.id.tv_pathName);

        mBackIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = contentTextTv.getText().toString();
                String tag = contentTextTv2.getText().toString();
                saveData(name,tag);
            }
        });
        mPathNameTv.setText("个体详情");
    }

    @Override
    public void onBackPressed() {
        String name = contentTextTv.getText().toString();
        String tag = contentTextTv2.getText().toString();
        saveData(name,tag);
    }

    private TextView count_img_tv;

    private void initView(){
        count_img_tv = (TextView) findViewById(R.id.count_img_tv);
        count_img_tv.setText("0/20");
    }

    private GridViewAdapter gvAdapter;
    private SendGridView mGridView;

    private int numLoc;
    private void initGridView() {
        gvAdapter=new GridViewAdapter(this, new ArrayList<GridViewItem>());
        mGridView = (SendGridView) findViewById(R.id.img_gridview);
        mGridView.setAdapter(gvAdapter);


        gvAdapter.setOnItemDetailClickListener(new GridViewAdapter.onItemDetailListener() {
            @Override
            public void onClick(int i) {
                String face_id = gvAdapter.getItem(i).getId();
                //删除
                if (i>0){
                    showShortCenterToast("删除图片");
                    delDataImg(face_id,i);
                }else {
                    showShortCenterToast("首张图片不可删除！");
                }

            }
        });

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                int num = gvAdapter.getCount();

                Bitmap bitmap = gvAdapter.getItem(arg2).getBitmap();
                String face_id = gvAdapter.getItem(arg2).getId();

                numLoc = num -1;
                if ( arg2 == (num -1) ){
                    if (numLoc>=20){
                        showShortCenterToast("图片上传最多20张");
                    }else {
                        showConfire();
                    }
                }else {
                    getDataFaceInfo(face_id,bitmap);
                }
            }
        });
    }

    private void getDataFaceInfo(String face_id, final Bitmap bitmap){
        showProdialog(null, "数据提交中,请稍后...", null);
        long totalMilliSeconds = System.currentTimeMillis();
        long totalSeconds = totalMilliSeconds / 1000;

        Map<String, String> map = new HashMap<String, String>();
        map.put("app_id", AppData.TENCENT_AI_APP_ID);
        map.put("time_stamp", totalSeconds+"");
        map.put("nonce_str", "fa577ce340859f9fe");

        map.put("face_id",face_id);


        String url = SortUtils.formatUrlParam(map, "utf-8", true);

        String url2 = url+"&app_key="+AppData.TENCENT_AI_APP_KEY;


        String md5Url = MD5.stringToMD5(url2);

        OkHttpUtils
                .post()
                .url(AppData.URL_FACE_GETFACEINFO)
                .addParams("app_id",AppData.TENCENT_AI_APP_ID)
                .addParams("time_stamp",totalSeconds+"")
                .addParams("nonce_str","fa577ce340859f9fe")
                .addParams("sign",md5Url.toUpperCase())

                .addParams("face_id",face_id)

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

                        FaceDataInfoObj result = (FaceDataInfoObj) JsonUtil.jsonToBean(response,FaceDataInfoObj.class);
                        // String target_text = result.getData().getTarget_text();
                        if (result.getRet()==0){
                            FaceData data = result.getData().getFace_info();
                            Bitmap bitmapInfo = ImageUtil.drawPointFaceInfoToBitmap(bitmap,data);
                            //图片放大
                            Utils.showImgDlgBitmapPhotoView2(TestActivity76EditDetail.this,bitmapInfo);
                        }else {
                            showShortCenterToast(result.getMsg());
                        }
                    }
                });
    }

    private void initGridData(String[] fileNames){
        ArrayList<GridViewItem> gvInfoList;
        GridViewItem gvInfo;
        gvInfoList = new ArrayList<GridViewItem>();

        for (int i = 1 ;i<fileNames.length;i+=2){

            File file = FileUtil.getFile2(TestActivity76EditDetail.this, AppData.PICTURE_PATH,fileNames[i]);
            Bitmap bitmap = ImageUtil.getLoacalBitmap(file.getAbsolutePath());

            gvInfo = new GridViewItem(bitmap, R.drawable.cancel, "1",fileNames[i],fileNames[i-1]);
            gvInfoList.add(gvInfo);
        }
        //0
        gvInfo = new GridViewItem(R.drawable.add_pictures, R.drawable.cancel,"1",null,"0");
        gvInfoList.add(gvInfo);

        gvAdapter.setAllItem(gvInfoList);

        count_img_tv.setText((gvAdapter.getCount()-1)+"/20");

    }
    private void getDetailData(){
        showProdialog(null, "获取数据中,请稍后...", null);
        long totalMilliSeconds = System.currentTimeMillis();
        long totalSeconds = totalMilliSeconds / 1000;
        long session_id = totalSeconds+123456;
       // String image64  = ImageUtil.imageToBase64(cacheFile.getAbsolutePath());

        Map<String, String> map = new HashMap<String, String>();
        map.put("app_id", AppData.TENCENT_AI_APP_ID);
        map.put("time_stamp", totalSeconds+"");
        // map.put("type","1");
        map.put("nonce_str", "fa577ce340859f9fe");
        //map.put("text",text);

        map.put("person_id",person_id);


        String url = SortUtils.formatUrlParam(map, "utf-8", true);

        String url2 = url+"&app_key="+AppData.TENCENT_AI_APP_KEY;
//        showOut("====getDataFenCi=url=="+cacheFile.getAbsolutePath());

        String md5Url = MD5.stringToMD5(url2);

       // Log.d("getDataFanYi","====getDataFenCi=image64=="+image64);
        // showOut("====getDataFanYi=sourceId=="+sourceId+"====targetId=====>"+targetId);

        OkHttpUtils
                .post()
                .url(AppData.URL_FACE_GETINFO)
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

//                        String imgStr =  cacheFile.getName();
                        showOut("-----获取到的json数据1--LoginActivity--getDataFanYi----"+response);
                        FaceDetailDataObj result = (FaceDetailDataObj) JsonUtil.jsonToBean(response,FaceDetailDataObj.class);
                        // String target_text = result.getData().getTarget_text();
                        if (result.getRet()==0){

//                            FacePersonDetailData  data = result.getData();
//                            List<String> face_ids = data.getFace_ids();
//                            if (face_ids!=null&&face_ids.size()>0){
//                                String faceId = face_ids.get(0);
//                                //请求数据
//                            }else {
//                                //无图片数据
//                            }
                            initData();

                        }else {
                            showShortCenterToast(result.getMsg());
                        }
                    }
                });
    }

    private int group_id;

    private void initData(){

        FacePersonData facePersonData = mApplication.getFacePersonMsg(person_id);

        String name = facePersonData.getPerson_name();
        String tag = facePersonData.getTag();
        group_id = facePersonData.getGroup_ids();

        String[] fileNames = StringUtil.getAgeFaceImgFiles(facePersonData.getImage_list());
       // count_img_tv.setText((fileNames.length/2)+"/20");
        contentTextTv.setText(name);
        contentTextTv2.setText(tag);
        initGridData(fileNames);
    }

    private EditText contentTextTv;
    private TextView contentNumTv;
    private int maxNum= 10;
    private void initNameView(){

        //灾情、隐患内容
        contentTextTv = (EditText) findViewById(R.id.content_text_tv);
        contentNumTv = (TextView) findViewById(R.id.content_num_tv);
        contentNumTv.setText(0+"/"+maxNum);
        Utils.showTextViewNUM(contentNumTv, contentTextTv, maxNum);
        //Utils.showEditViewInScallview(contentTextTv);
    }

    private EditText contentTextTv2;
    private TextView contentNumTv2;
    private int maxNum2= 200;
    private void initTagView(){

        //灾情、隐患内容
        contentTextTv2 = (EditText) findViewById(R.id.content_text_tv2);
        contentNumTv2 = (TextView) findViewById(R.id.content_num_tv2);
        contentNumTv2.setText(0+"/"+maxNum2);
        Utils.showTextViewNUM(contentNumTv2, contentTextTv2, maxNum2);
        //Utils.showEditViewInScallview(contentTextTv);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit_send_bt:
                //if (cacheFile!=null){
                    String name = contentTextTv.getText().toString();
                    String tag = contentTextTv2.getText().toString();
                    if (!StringUtil.isEmpty(name)){
                       getDataImg0(name,tag);
                    }else {
                        showShortCenterToast("名字不能为空");
                    }
//                }else {
//                    showShortCenterToast("请上传图片");
//                }
                break;
        }
    }
    private void getDataImg0(final String name, final String tag){
        showProdialog(null, "数据提交中,请稍后...", null);
        long totalMilliSeconds = System.currentTimeMillis();
        long totalSeconds = totalMilliSeconds / 1000;
        long session_id = totalSeconds+123456;
        //String image64  = ImageUtil.imageToBase64(cacheFile.getAbsolutePath());

        Map<String, String> map = new HashMap<String, String>();
        map.put("app_id", AppData.TENCENT_AI_APP_ID);
        map.put("time_stamp", totalSeconds+"");
        // map.put("type","1");
        map.put("nonce_str", "fa577ce340859f9fe");
        //map.put("text",text);

        map.put("person_id",person_id);
        map.put("person_name",name);
        map.put("tag",tag);

        //map.put("image",image64);

        String url = SortUtils.formatUrlParam(map, "utf-8", true);

        String url2 = url+"&app_key="+AppData.TENCENT_AI_APP_KEY;
       // showOut("====getDataFenCi=url=="+cacheFile.getAbsolutePath());

        String md5Url = MD5.stringToMD5(url2);

        //Log.d("getDataFanYi","====getDataFenCi=image64=="+image64);
        // showOut("====getDataFanYi=sourceId=="+sourceId+"====targetId=====>"+targetId);

        OkHttpUtils
                .post()
                .url(AppData.URL_FACE_SETINFO)
                .addParams("app_id",AppData.TENCENT_AI_APP_ID)
                .addParams("time_stamp",totalSeconds+"")
                .addParams("nonce_str","fa577ce340859f9fe")
                .addParams("sign",md5Url.toUpperCase())

               // .addParams("group_ids",chooseIDs[1]+"")
                .addParams("person_id",person_id)
                .addParams("person_name",name)
                .addParams("tag",tag)

                // .addFile("image", cropFile.getName(), cropFile)//test_vidio.mp4
               // .addParams("image",image64)

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

//                        String imgStr =  cacheFile.getName();
//                        showOut("-----获取到的json数据1--LoginActivity--getDataFanYi----"+response
//                                +"==imgStr=>"+imgStr);
                        FaceDetailDataObj result = (FaceDetailDataObj) JsonUtil.jsonToBean(response,FaceDetailDataObj.class);
                        // String target_text = result.getData().getTarget_text();
                        if (result.getRet()==0){

                            saveData(name,tag);

                        }else {
                            showShortCenterToast(result.getMsg());
                        }
                    }
                });
    }
    private String getImgDatas(){
        StringBuffer stringBuffer = new StringBuffer();
        int count = gvAdapter.getCount()-1;
        for (int i= 0;i< count;i++){
            String id = gvAdapter.getItem(i).getId();
            String filePath = gvAdapter.getItem(i).getFilePath();
            stringBuffer.append(id+","+filePath);
            if (i<count-1){
                stringBuffer.append(",");
            }
           // showOut("==gvAdapter.getItem(i).getId()==>"+id+"==filePath===>"+filePath);
        }
        return stringBuffer.toString();
    }
    private void saveData( String name,  String tag){

        FacePersonData uInfo = new FacePersonData();
        uInfo.setTag(tag);
        uInfo.setPerson_name(name);
        uInfo.setPerson_id(person_id);
        uInfo.setGroup_ids(group_id);
        uInfo.setImage_list(getImgDatas());

        //保存用户信息
        FacePersonDB mySQLiteOpenHelper = new FacePersonDB(TestActivity76EditDetail.this);
        mySQLiteOpenHelper.updateFacePerson(uInfo);
        mySQLiteOpenHelper.close();
        finishResult();
    }

    private void delDataImg(String face_id, final int position){
        showProdialog(null, "数据提交中,请稍后...", null);
        long totalMilliSeconds = System.currentTimeMillis();
        long totalSeconds = totalMilliSeconds / 1000;
        long session_id = totalSeconds+123456;
       // String image64  = ImageUtil.imageToBase64(cacheFile.getAbsolutePath());

        Map<String, String> map = new HashMap<String, String>();
        map.put("app_id", AppData.TENCENT_AI_APP_ID);
        map.put("time_stamp", totalSeconds+"");
        // map.put("type","1");
        map.put("nonce_str", "fa577ce340859f9fe");
        //map.put("text",text);

        map.put("person_id",person_id);
        map.put("face_ids",face_id);

//        map.put("images",image64);

        String url = SortUtils.formatUrlParam(map, "utf-8", true);

        String url2 = url+"&app_key="+AppData.TENCENT_AI_APP_KEY;
       // showOut("====getDataFenCi=url=="+cacheFile.getAbsolutePath());

        String md5Url = MD5.stringToMD5(url2);

       // Log.d("getDataFanYi","====getDataFenCi=image64=="+image64);
        // showOut("====getDataFanYi=sourceId=="+sourceId+"====targetId=====>"+targetId);

        OkHttpUtils
                .post()
                .url(AppData.URL_FACE_DELFACE)
                .addParams("app_id",AppData.TENCENT_AI_APP_ID)
                .addParams("time_stamp",totalSeconds+"")
                .addParams("nonce_str","fa577ce340859f9fe")
                .addParams("sign",md5Url.toUpperCase())

                .addParams("person_id",person_id)
                .addParams("face_ids",face_id)

                // .addFile("image", cropFile.getName(), cropFile)//test_vidio.mp4
               // .addParams("images",image64)

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

//                        String imgStr =  cacheFile.getName();
                        showOut("-----获取到的json数据1--LoginActivity--getDataFanYi----"+response);

                        FaceDetailDataObj result = (FaceDetailDataObj) JsonUtil.jsonToBean(response,FaceDetailDataObj.class);
                        // String target_text = result.getData().getTarget_text();
                        if (result.getRet()==0){
                            gvAdapter.removeItem(position);

                            count_img_tv.setText((gvAdapter.getCount()-1)+"/20");
                        }else {
                            showShortCenterToast(result.getMsg());
                        }
                    }
                });
    }
    private String text;
    private  Bitmap bitmap2;
    private void sendFileDataImg(final String tag){
        showProdialog(null, "数据提交中,请稍后...", null);
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

        map.put("person_id",person_id);
        map.put("tag",tag);

        map.put("images",image64);

        String url = SortUtils.formatUrlParam(map, "utf-8", true);

        String url2 = url+"&app_key="+AppData.TENCENT_AI_APP_KEY;
        showOut("====getDataFenCi=url=="+cacheFile.getAbsolutePath());

        String md5Url = MD5.stringToMD5(url2);

        Log.d("getDataFanYi","====getDataFenCi=image64=="+image64);
       // showOut("====getDataFanYi=sourceId=="+sourceId+"====targetId=====>"+targetId);

        OkHttpUtils
                .post()
                .url(AppData.URL_FACE_ADDFACE)
                .addParams("app_id",AppData.TENCENT_AI_APP_ID)
                .addParams("time_stamp",totalSeconds+"")
                .addParams("nonce_str","fa577ce340859f9fe")
                .addParams("sign",md5Url.toUpperCase())

                .addParams("person_id",person_id)
                .addParams("tag",tag)

               // .addFile("image", cropFile.getName(), cropFile)//test_vidio.mp4
               .addParams("images",image64)

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

                        String imgStr =  cacheFile.getName();
                        showOut("-----获取到的json数据1--LoginActivity--getDataFanYi----"+response
                                +"==imgStr=>"+imgStr);
                        FaceDetailDataObj result = (FaceDetailDataObj) JsonUtil.jsonToBean(response,FaceDetailDataObj.class);
                       // String target_text = result.getData().getTarget_text();
                        if (result.getRet()==0){


                            String  faceId = result.getData().getFace_ids().get(0);
                            Bitmap bitmap = ImageUtil.getLoacalBitmap(cacheFile.getAbsolutePath());
                            // setAllItem1(bitmap,resultStr.getPath());
                            gvAdapter.addItem(addGvInfo(bitmap,faceId),numLoc);

                            count_img_tv.setText((gvAdapter.getCount()-1)+"/20");
//                            Utils.saveSharedPreferences(TestActivity76EditDetail.this,"group_ids",group_ids);
//                            FacePersonDetailData data= result.getData();
//
//
//                            FacePersonData uInfo = new FacePersonData();
//
//                            uInfo.setImage_list(imgStr);
//                            //保存用户信息
//                            FacePersonDB mySQLiteOpenHelper = new FacePersonDB(TestActivity76EditDetail.this);
//                            mySQLiteOpenHelper.updateFacePerson(uInfo);
//                            mySQLiteOpenHelper.close();

                        }else {
                            showShortCenterToast(result.getMsg());
                        }
                    }
                });
    }
    private GridViewItem addGvInfo(Bitmap bitmap,String id){
        GridViewItem gvInfo  = new GridViewItem(bitmap, R.drawable.cancel,"1",cacheFile.getName(),id);
        return  gvInfo;
    }
    private String showListData(List<OCROtherData> item_list){

        String content;
        if (item_list!=null&& item_list.size()>0){
            StringBuffer stringBuffer = new StringBuffer();
            for (OCROtherData data:item_list){
                stringBuffer.append(data.getItem()+"："+data.getItemstring()+"\n");
            }
            content = stringBuffer.toString();
        }else {
            content = "暂无";
        }
        return content;
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

            ImageUtil.saveImage(TestActivity76EditDetail.this,
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
//                            Bitmap bitmap = ImageUtil.getLoacalBitmap(cacheFile.getAbsolutePath());
//                            img_iv.setImageBitmap(bitmap);
                            //new Handler().postDelayed(runnable,400);

                            String tag = contentTextTv2.getText().toString();
                            sendFileDataImg(tag);
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
        PermissionUtils.requestPermission(TestActivity76EditDetail.this, PermissionUtils.CODE_CAMERA, mPermissionGrant);
    }
    private int num;
    public void showPermissionCONTENT(int i) {
        num = i;
        PermissionUtils.requestPermission(TestActivity76EditDetail.this, PermissionUtils.CODE_WRITE_EXTERNAL_STORAGE,
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
