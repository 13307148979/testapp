package com.teplot.testapp.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.math.BigDecimal;

public class FileUtil {

	// ////////////////SD卡////////////////////

	public static final String CHARSET = "utf-8";
	private static String TAG = "FileUtil";

	/**
	 * 检测SD卡是否存在
	 * 
	 * @return
	 */
	public static boolean checkSDCardExists() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			return true;
		} else
			return false;
	}
	/**
	 * 写文本文件 在Android系统中，文件保存在 /data/data/PACKAGE_NAME/files 目录下
	 *
	 * @param context
	 * @param fileName
	 */
	public static void write( Context context,String fileName, String content) {
		if (content == null)
			content = "";

		try {
			FileOutputStream fos = context.openFileOutput(fileName,
					Context.MODE_PRIVATE);
			fos.write(content.getBytes());

			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
			//Logger.e(TAG, e.getMessage());
			Log.e(TAG, e.getMessage());
		}
	}
	/**
	 * 读取文本文件
	 *
	 * @param //context
	 * @param //fileName
	 * @return
	 */
	/*public static String read(Context context, String fileName) {
		String savePath = context.getCacheDir().getAbsolutePath() + "/" + AppData.BASE_PATH 
				+ "/Log"+"/"+fileName;
		// File savedir = new File(savePath);
		try {
			// FileInputStream in = context.openFileInput(savePath);
			File file = new File(savePath);
			FileInputStream in = new FileInputStream(file);

			return readInStream(in);
		} catch (Exception e) {
			e.printStackTrace();
			//Logger.e(TAG, e.getMessage());
			Log.e(TAG, e.getMessage());
		}
		return "";
	}*/

	public static String readInStream(InputStream inStream) {
		try {
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			byte[] buffer = new byte[512];
			int length = -1;
			while ((length = inStream.read(buffer)) != -1) {
				outStream.write(buffer, 0, length);
			}

			outStream.close();
			inStream.close();
			return outStream.toString();
		} catch (IOException e) {
			Log.i("FileTest", e.getMessage());
			//Logger.e(TAG, e.getMessage());
		}
		return null;
	}

	/**
	 * 获取应用程序缓存文件夹下的指定目录
	 * @param context
	 * @param dir
	 * @return
	 */
	public static String getAppCache(Context context, String dir) {
		String savePath = context.getCacheDir().getAbsolutePath() + "/" + dir + "/";
		File savedir = new File(savePath);
		if (!savedir.exists()) {
			savedir.mkdirs();
		}
		savedir = null;
		return savePath;
	}
	/**
	 * 创建文件夹
	 * @param context
	 * @param dir
	 * @return
	 */
	public static File creatAppCacheFile(Context context, String dir) {
		String savePath = context.getCacheDir().getAbsolutePath() + "/" + dir;
		Log.d("savePath============>", savePath);
		File savedir = new File(savePath);
		if (!savedir.exists()) {
			savedir.mkdirs();
		}
		return savedir;
	}
	public static File creatAppCacheFile2(Context context, String dir) {
		String savePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + dir;
		Log.d("savePath============>", savePath);
		File savedir = new File(savePath);
		if (!savedir.exists()) {
			savedir.mkdirs();
		}
		return savedir;
	}
	/**
	 * 获取应用程序缓存文件夹下的指定目录
	 * @param context
	 * @param dir
	 * @return
	 */
	public static String getSDCardCache(Context context, String dir) {
		String savePath = context.getExternalCacheDir().getAbsolutePath() + "/" + dir + "/";
		File savedir = new File(savePath);
		if (!savedir.exists()) {
			savedir.mkdirs();
		}
		savedir = null;
		return savePath;
	}

	// ///////////////文件/////////////////////
	/**
	 * 根据文件绝对路径获取文件名
	 * @param filePath
	 * @return
	 */
	public static String getFileName( String filePath ){
		if( StringUtil.isEmpty(filePath))
			return "";
		if (filePath.contains(File.separator))
			return filePath.substring( filePath.lastIndexOf( File.separator ) + 1 );
		return filePath;
	}

	/**
	 * 根据文件的绝对路径获取文件名但不包含扩展名
	 * 
	 * @param filePath
	 * @return
	 */
	public static String getFileNameNoFormat(String filePath) {
		if (StringUtil.isEmpty(filePath)) {
			return "";
		}
		int point = filePath.lastIndexOf('.');
		return filePath.substring(filePath.lastIndexOf(File.separator) + 1,
				point);
	}

	/**
	 * 获取文件扩展名
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getFileFormat(String fileName) {
		if (StringUtil.isEmpty(fileName))
			return "";
		int point = fileName.lastIndexOf('.');
		return fileName.substring(point + 1);
	}

	/**
	 * 检查文件是否存在
	 * 
	 * @param filePath
	 * @return
	 */
	public static boolean checkFileExists(String filePath) {
		boolean status;
		if (checkSDCardExists()) {
			File newPath = new File(Environment.getExternalStorageDirectory()
					.toString() + filePath);
			status = newPath.exists();
			if (status)
				status = newPath.length() > 0;
		} else {
			status = false;
		}
		return status;
	}

	/**
	 * 创建新的目录及文件
	 * 
	 * @param folderPath
	 *            目录:/xx/xxx
	 * @param fileName
	 *            文件名: xxx
	 * @return
	 */
	public static File createFile(Context context,String folderPath, String fileName) {

		File f = getFile(context,folderPath, fileName);
		//File f = creatAppCacheFile(dir);//
		try {
			if (!f.exists())
				f.createNewFile();
		} catch (Exception e) {
			e.printStackTrace();
			//Logger.e(TAG, e.getMessage());
			Log.e(TAG, e.getMessage());
			return null;
		}
		return f;
	}
	public static File createFile2(Context context,String folderPath, String fileName) {

		File f = getFile2(context,folderPath, fileName);
		//File f = creatAppCacheFile(dir);//
		try {
			if (!f.exists())
				f.createNewFile();
		} catch (Exception e) {
			e.printStackTrace();
			//Logger.e(TAG, e.getMessage());
			Log.e(TAG, e.getMessage());
			return null;
		}
		return f;
	}

	/**
	 * 创建新的目录,不创建文件
	 * 
	 * @param folderPath
	 *            目录:/xx/xxx
	 * @param fileName
	 *            文件名: xxx
	 * @return
	 */
	public static File getFile(Context context, String folderPath, String fileName) {
		File destDir = creatAppCacheFile(context,folderPath);
		File f = new File(destDir, fileName);
		return f;
	}
	public static File getFile2(Context context, String folderPath, String fileName) {
		File destDir = creatAppCacheFile2(context,folderPath);
		File f = new File(destDir, fileName);
		return f;
	}

	/**
	 * 新建目录
	 * 
	 * @param directoryName
	 *            /xxx/xxx...
	 * @return
	 */
	public static File createDirectory(String directoryName) {
		File newPath = null;

		if (!directoryName.startsWith(File.separator)) {
			directoryName = File.separator + directoryName;
		}
		File path = Environment.getExternalStorageDirectory();
		newPath = new File(path.toString() + directoryName);
		if (!newPath.exists())
			newPath.mkdirs();
		return newPath;
	}

	/**
	 * 读取文件
	 * 
	 * @param f
	 * @return 返回字符串
	 * @throws IOException
	 */
	public static String readFile(File f) {
		if (f == null)
			return null;
		FileInputStream fis = null;
		BufferedReader br = null;

		try {

			fis = new FileInputStream(f);
			br = new BufferedReader(new InputStreamReader(fis, CHARSET));
			StringBuilder sb = new StringBuilder();
			char[] c = new char[1024];
			int len;
			while ((len = br.read(c)) != -1) {
				sb.append(c, 0, len);
			}
			return sb.toString();

		} catch (IOException e) {
			e.printStackTrace();
			//Logger.e(TAG, e.getMessage());
			Log.e(TAG, e.getMessage());
			return null;

		} finally {
			try {
				if (br != null)
					br.close();
				if (fis != null)
					fis.close();
			} catch (IOException e) {
				//Logger.e(TAG, e.getMessage());
				Log.e(TAG, e.getMessage());
			}
		}
	}

	/**
	 * 追加方式写入字符串文件
	 * 
	 * @param f
	 * @return 布尔值
	 */
	public static boolean writerRAFFile(File f, String s) {
		if (f == null)
			return false;
		RandomAccessFile raf = null;
		try {
			raf = new RandomAccessFile(f, "rw");
			raf.seek(f.length());
			raf.write(s.getBytes(CHARSET));

			return true;
		} catch (IOException e) {
			e.printStackTrace();
			//Logger.e(TAG, e.getMessage());
			Log.e(TAG, e.getMessage());
			return false;
		} finally {
			try {
				if (raf != null)
					raf.close();
			} catch (IOException e) {
				//Logger.e(TAG, e.getMessage());
				Log.e(TAG, e.getMessage());
			}
		}

	}

	/**
	 * 写入字符串文件
	 * 
	 * @param f
	 * @return 布尔值
	 */
	public static boolean writerFile(File f, String s) {
		if (f == null)
			return false;
		FileOutputStream fos = null;
		BufferedWriter bw = null;
		try {
			fos = new FileOutputStream(f);
			bw = new BufferedWriter(new OutputStreamWriter(fos, CHARSET));
			bw.write(s);
			bw.close();
			// fos.write(s.getBytes(CHARSET));

			return true;
		} catch (IOException e) {
			e.printStackTrace();
			//Logger.e(TAG, e.getMessage());
			Log.e(TAG, e.getMessage());
			return false;
		} finally {
			try {
				if (bw != null)
					bw.close();
				if (fos != null)
					fos.close();
			} catch (IOException e) {
				//Logger.e(TAG, e.getMessage());
				Log.e(TAG, e.getMessage());
			}
		}

	}

	/**
	 * 删除目录所有文件
	 */
	public static void deleteAllFile(String filePath) {
		deleteAllFile(createDirectory(filePath));
	}

	/**
	 * 删除目录所有文件
	 */
	public static void deleteAllFile(final File f) {

		new Thread(new Runnable() {

			@Override
			public void run() {
				if (f != null) {
					dele(f);
				}
			}

			public void dele(File files) {
				for (File file : files.listFiles())
					if (file.isDirectory()) {
						dele(file);//file.delete();//子目录删除
					} else
						file.delete();
				//files.delete();// 包括父目录也删除
			}

		}).start();
	}
	/**
	 * 删除文件
	 * 
	 * @param filePath
	 * @return
	 */
	public static boolean deleteFile(String filePath) {
		return deleteFile(null, filePath);
	}

	public static void deleteFile2(String filePath) {
		File file = new File(filePath);
		if (file.exists()) { // 判断文件是否存在
			if (file.isFile()) { // 判断是否是文件
				file.delete(); // delete()方法 你应该知道 是删除的意思;
			} else if (file.isDirectory()) { // 否则如果它是一个目录
				deleteFile(filePath);
			}
			file.delete();
		} else {
			//System.out.println("文件不存在！"+"\n");
		}
	}
	public static boolean deleteFile(Context ct, String filePath) {
		boolean status;
		SecurityManager checker = new SecurityManager();
		if (!filePath.equals("")) {
			File path = (ct == null ? Environment.getExternalStorageDirectory()
					: ct.getFilesDir());
			File newPath = new File(path.toString() + filePath);
			checker.checkDelete(newPath.toString());
			if (newPath.isFile()) {
				try {
					newPath.delete();
					status = true;
				} catch (SecurityException se) {
					se.printStackTrace();
					status = false;
				}
			} else
				status = false;
		} else
			status = false;
		return status;
	}

	public static String getDir(double f) {
		if (f<1024) {		
			return saveScale(f,2)+"B";	
		}else if(f < 1024*1024 ) {
			f/=1024;
			return saveScale(f,2)+"KB";
		}else if(f < 1024*1024*1024){
			f/=(1024*1024);
			return saveScale(f,2)+"M";
		}else {
			f/=(1024*1024*1024);
			return saveScale(f,2)+"G";
		}	
	}

	/**
	 * 
	 * @param f		 double值
	 * @param scale  保留的小数位
	 * @return
	 */
	public static double saveScale(double f,int scale){
		BigDecimal b = new BigDecimal(f);
		double f1 = b.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
		return f1;	
	}

	public static double getDirSize(File file) {     
		//判断文件是否存在     
		if (file.exists()) {     
			//如果是目录则递归计算其内容的总大小    
			if (file.isDirectory()) {     
				File[] children = file.listFiles();     
				double size = 0;     
				for (File f : children)     
					size += getDirSize(f);     
				return size;     
			} else {//如果是文件则直接返回其大小,以“b”为单位   
				double size = (double) file.length(); 
				return size;     
			}     
		} else {     
			//System.out.println("文件或者文件夹不存在，请检查路径是否正确！");
			return 0.0;     
		}     
	}     
}
