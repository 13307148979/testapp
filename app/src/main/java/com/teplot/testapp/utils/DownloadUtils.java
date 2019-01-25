package com.teplot.testapp.utils;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.view.Gravity;
import android.widget.Toast;


import java.io.File;


public class DownloadUtils {
    //下载器
    private DownloadManager downloadManager;
    //上下文
    private Context mContext;
    //下载的ID
    private long downloadId;
    private int firstTime = -1;
    private String nameFile;
    public  DownloadUtils(Context context){
        this.mContext = context;
    }

   private String fileStr;
    //下载apk
    public void downloadAPK(String url,String file, String name) {
        firstTime = -1;
        fileStr = file;
        nameFile = name;
        //创建下载任务
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        //移动网络情况下是否允许漫游
        request.setAllowedOverRoaming(false);

        //在通知栏中显示，默认就是显示的
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        request.setVisibleInDownloadsUi(true);// 设置下载可见
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);//下载完成后通知栏任然可见
        //request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
       // request.
        request.setTitle("悦农通");
        request.setDescription("客户端下载");
        request.setVisibleInDownloadsUi(true);

        //设置下载的路径
        request.setDestinationInExternalPublicDir(file , name);

        //获取DownloadManager
        downloadManager = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
        //将下载请求加入下载队列，加入下载队列后会给该任务返回一个long型的id，通过该id可以取消任务，重启任务、获取下载的文件等等
        downloadId = downloadManager.enqueue(request);

        //注册广播接收者，监听下载状态
        mContext.registerReceiver(receiver,
                new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    //广播监听下载的各个状态
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            checkStatus();
        }
    };

    //检查下载状态
    private void checkStatus() {
        DownloadManager.Query query = new DownloadManager.Query();
        //通过下载的id查找
        query.setFilterById(downloadId);
        Cursor c = downloadManager.query(query);
        if (c.moveToFirst()) {
            int status = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
            switch (status) {
                //下载暂停
                case DownloadManager.STATUS_PAUSED:
                    break;
                //下载延迟
                case DownloadManager.STATUS_PENDING:
                    break;
                //正在下载
                case DownloadManager.STATUS_RUNNING:
                    break;
                  //下载完成
                case DownloadManager.STATUS_SUCCESSFUL:
                  //下载完成安装APK
                    checkIsAndroidO(fileStr,nameFile);
                    break;
                 //下载失败
                case DownloadManager.STATUS_FAILED:
                    Toast.makeText(mContext,"客户端下载失败，请重新下载！",Toast.LENGTH_LONG).show();
                   // showLongCenterToast("下载失败，请重新下载！");
                    break;
            }
        }
        c.close();
    }
    private void showLongCenterToast(String text) {
        Toast toast = Toast.makeText(mContext, text, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
    /**
     * 判断是否是8.0系统,是的话需要获取此权限，判断开没开，没开的话处理未知应用来源权限问题,否则直接安装
     * <uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES"/>
     */
    private void checkIsAndroidO(String folderPath,String fileName) {
        File appFile = FileUtils.getFile(folderPath,fileName);
        if (Build.VERSION.SDK_INT >= 26) {
            boolean b = mContext.getPackageManager().canRequestPackageInstalls();
            //Toast.makeText(mContext,"是否为8.0版本"+b,Toast.LENGTH_LONG).show();
            if (b) {
                installApk(appFile);//安装应用的逻辑(写自己的就可以)
            } else {
                //请求安装未知应用来源的权限
                //跳转设置开启允许安装
                if (firstTime==-1){
                    firstTime = 1;
                   // Intent intent = new Intent(mContext, InstallApkActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
//                    mContext.startActivity(intent);
                }
                return;
            }
        } else {
            installApk(appFile);
        }
    }
    private void installApk(File appFile) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        // 由于没有在Activity环境下启动Activity,设置下面的标签
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //版本在7.0以上是不能直接通过uri访问的
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {

            //参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
            Uri apkUri = FileProvider.getUriForFile(mContext, "com.teplot.testapp.fileprovider", appFile);;
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            intent.setAction(Intent.ACTION_VIEW);
            intent.putExtra("return-data", false);
        } else {
            intent.setDataAndType(Uri.fromFile(appFile),
                    "application/vnd.android.package-archive");
        }
        mContext.startActivity(intent);
    }
}
