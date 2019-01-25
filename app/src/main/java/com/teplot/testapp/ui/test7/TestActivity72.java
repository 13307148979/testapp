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
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.teplot.testapp.R;
import com.teplot.testapp.apps.AppData;
import com.teplot.testapp.base.BaseActivity;
import com.teplot.testapp.been.details.FaceData;
import com.teplot.testapp.been.details.OCROtherData;
import com.teplot.testapp.been.details.WeiZhiData;
import com.teplot.testapp.been.result.FaceListDataObj;
import com.teplot.testapp.been.result.FaceShapeDataObj;
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
import com.teplot.testapp.utils.Utils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;
import top.zibin.luban.OnRenameListener;


public class TestActivity72 extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test71);
        setImmersionBarNon();
        initHeadView();
        initView();
    }

    private void initHeadView() {
        mTopBar = new TopBar(this);
        mTopBar.setPathName("多人脸检测(多头)");
    }

    private ImageView img_iv,img_iv2;

    private TextView mLocation_address_tv0;

    private LinearLayout ll_choose;
    private void initView(){
        ll_choose = (LinearLayout) findViewById(R.id.ll_choose);
        ll_choose.setVisibility(View.GONE);
        mLocation_address_tv0 = (TextView) findViewById(R.id.location_address_tv0);
        mLocation_address_tv0.setText(targets[0]);

        img_iv = (ImageView) findViewById(R.id.img_iv);
        img_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cacheFile!=null){
                    Bitmap bitmap = ImageUtil.getLoacalBitmap(cacheFile.getAbsolutePath());
                    Utils.showImgDlgBitmapPhotoView2(TestActivity72.this,bitmap);
                }
            }
        });
        img_iv2 = (ImageView) findViewById(R.id.img_iv2);
        img_iv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cacheFile!=null){
                   // Bitmap bitmap = ImageUtil.getLoacalBitmap(cacheFile.getAbsolutePath());
                    Utils.showImgDlgBitmapPhotoView2(TestActivity72.this,bitmap2);
                }
            }
        });

    }

    private int[] chooseIDs = {0,0,0,0,0,0,0};
    private boolean[] isChoose = new boolean[7];
    private void showPosReportListDlg(final String[] strings,final String title,final int choose,final TextView bt) {
        if (strings != null && strings.length > 0) {
            SingleChoiceListDlg dlg = new SingleChoiceListDlg.Builder(
                    TestActivity72.this)
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

    private String[] targets = {"正常","大脸模式"};
//    private String[] targetUrls = {
//            AppData.URL_OCR_DRIVERLICENSEOCR,AppData.URL_OCR_BIZLICENSEOCR,
//            AppData.URL_OCR_CREDITCARDOCR,
//            AppData.URL_OCR_HANDWRITINGOCR,AppData.URL_OCR_PLATEOCR,AppData.URL_OCR_BCOCR};
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.choose_img_tv:
                showConfire();
                break;
//            case R.id.location_address_tv0:
//                showPosReportListDlg(targets,"OCR类别选择",1,mLocation_address_tv0);
//                break;
            case R.id.submit_send_bt:
                if (cacheFile!=null){
                    getDataImg0();
                }else {
                    showShortCenterToast("请上传图片");
                }
                break;
        }
    }

    private void showGetData(){
        switch ( chooseIDs[1]){
            case 0:
                getDataImg0();
                break;
        }
    }
    private String text;
    private  Bitmap bitmap2;
    private void getDataImg0(){
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

        //map.put("mode",mode+"");

        map.put("image",image64);

        String url = SortUtils.formatUrlParam(map, "utf-8", true);

        String url2 = url+"&app_key="+AppData.TENCENT_AI_APP_KEY;
        showOut("====getDataFenCi=url=="+cacheFile.getAbsolutePath());

        String md5Url = MD5.stringToMD5(url2);

        Log.d("getDataFanYi","====getDataFenCi=image64=="+image64);
       // showOut("====getDataFanYi=sourceId=="+sourceId+"====targetId=====>"+targetId);

        OkHttpUtils
                .post()
                .url(AppData.URL_FACE_DETECTMULTIFACE)
                .addParams("app_id",AppData.TENCENT_AI_APP_ID)
                .addParams("time_stamp",totalSeconds+"")
                .addParams("nonce_str","fa577ce340859f9fe")
                .addParams("sign",md5Url.toUpperCase())
               // .addParams("type","1")
                //.addParams("text",text)
               // .addParams("mode",mode+"")

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
                        FaceShapeDataObj result = (FaceShapeDataObj) JsonUtil.jsonToBean(response,FaceShapeDataObj.class);
                       // String target_text = result.getData().getTarget_text();
                        if (result.getRet()==0){
                            List<WeiZhiData> data = result.getData().getFace_list();
                            Bitmap bitmap = ImageUtil.getLoacalBitmap(cacheFile.getAbsolutePath());
                             bitmap2 = ImageUtil.drawPointFaceShapeToBitmapLine(bitmap,data);
                            img_iv2.setImageBitmap(bitmap2);

                        }else {
                            showShortCenterToast(result.getMsg());
                        }
                    }
                });
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

            ImageUtil.saveImage(TestActivity72.this,
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
        PermissionUtils.requestPermission(TestActivity72.this, PermissionUtils.CODE_CAMERA, mPermissionGrant);
    }
    private int num;
    public void showPermissionCONTENT(int i) {
        num = i;
        PermissionUtils.requestPermission(TestActivity72.this, PermissionUtils.CODE_WRITE_EXTERNAL_STORAGE,
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
