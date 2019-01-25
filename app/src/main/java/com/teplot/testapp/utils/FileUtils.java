package com.teplot.testapp.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class FileUtils {


	/**
	 * 创建新的文件
	 * 
	 * @param folderPath
	 * @param fileName
	 * @return
	 */
	public static File createFile(String folderPath, String fileName) {
		if (!FileUtil.checkSDCardExists())
			return null;
		if (fileName == null)
			return null;
		File destDir = new File(Environment.getExternalStorageDirectory()
				.toString() + folderPath);
		if (!destDir.exists()) {
			destDir.mkdirs();
		}
		File f = new File(destDir, fileName);
		try {
			if (!f.exists())
				f.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return f;
	}
	
	/**
	 * get文件
	 * 
	 * @param folderPath
	 * @param fileName
	 * @return
	 */
	public static File getFile(String folderPath, String fileName) {
		if (!FileUtil.checkSDCardExists())
			return null;
		if (fileName == null)
			return null;
		File destDir = new File(Environment.getExternalStorageDirectory()
				.toString() + folderPath);
		if (!destDir.exists()) {
			destDir.mkdirs();
		}
		return new File(destDir, fileName);
	}
	/**
	 * 追加方式写入文件
	 * 
	 * @param f
	 * @return
	 */
	public static boolean writerFile(File f, String s) {
		if (f == null)
			return false;
		RandomAccessFile raf = null;
		try {
			raf = new RandomAccessFile(f, "rw");
			raf.seek(f.length());
			raf.write(s.getBytes("utf-8"));
			return true;

		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (raf != null)
					raf.close();
			} catch (IOException e) {
			}
		}

	}
	/**
	 * 追加方式写入文件
	 * 
	 * @param f
	 * @return
	 */
	public static boolean writerFile(File f, byte s[]) {
		if (f == null)
			return false;
		RandomAccessFile raf = null;
		try {
			raf = new RandomAccessFile(f, "rw");
			raf.seek(f.length());
			raf.write(s);
			return true;

		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (raf != null)
					raf.close();
			} catch (IOException e) {
			}
		}
	}


	/**
	 * 新建目录
	 * 
	 * @param directoryName
	 * @return
	 */
	public static File createDirectory(String directoryName) {
		File newPath = null;
		if (FileUtil.checkSDCardExists()) {
			File path = Environment.getExternalStorageDirectory();
			newPath = new File(path.toString() + directoryName);
			if (!newPath.exists())
				newPath.mkdirs();
		}
		return newPath;
	}
	

	public static File createDirectory(Context ct, String directoryName) {
		File path = (ct == null ? Environment.getExternalStorageDirectory()
				: ct.getFilesDir());
		File newPath = new File(path.toString() + directoryName);
		if (!newPath.exists()) {
			newPath.mkdirs();
		}
		return newPath;
	}

}
