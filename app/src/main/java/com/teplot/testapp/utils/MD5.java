package com.teplot.testapp.utils;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {

	private static String TAG = "MD5";
	public static String stringToMD5(String string) {
		byte[] hash;

		try {
			hash = MessageDigest.getInstance("MD5").digest(
					string.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			//Logger.e(TAG, e.getMessage());
			Log.e(TAG, e.getMessage());
			return "";
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			//Logger.e(TAG, e.getMessage());
			Log.e(TAG, e.getMessage());
			return "";
		}

		StringBuilder hex = new StringBuilder(hash.length * 2);
		for (byte b : hash) {
			if ((b & 0xFF) < 0x10)
				hex.append("0");
			hex.append(Integer.toHexString(b & 0xFF));
		}

		return hex.toString();
	}

	protected static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
	protected static MessageDigest messagedigest = null;
	static {
		try {
			messagedigest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			///Logger.e(TAG, "messagedigest初始化失败"+e.getMessage());
			Log.e(TAG, "messagedigest初始化失败"+e.getMessage());
			//System.out.println("MD5-----FileUtil messagedigest初始化失败");
		}
	}

	public static String getFileMD5String(File file) {

		try {
			FileInputStream in = new FileInputStream(file);
			@SuppressWarnings("resource")
			DigestInputStream digestInputStream = new DigestInputStream(in,
					messagedigest);
			// read的过程中进行MD5处理，直到读完文件
			byte[] buffer = new byte[1024 * 4];
			while (digestInputStream.read(buffer) > 0)
				;
			// FileChannel ch = in.getChannel();
			// MappedByteBuffer byteBuffer =
			// ch.map(FileChannel.MapMode.READ_ONLY,
			// 0,
			// file.length());//大文件方法 貌似有釋放問題
			// messagedigest.update(byteBuffer);
			return bufferToHex(messagedigest.digest());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//Logger.e(TAG, e.getMessage());
			Log.e(TAG, e.getMessage());
			return "";
		}
	}

	public static String getMD5String(String s) {
		return getMD5String(s.getBytes());
	}

	public static String getMD5String(byte[] bytes) {
		messagedigest.update(bytes);
		return bufferToHex(messagedigest.digest());
	}

	private static String bufferToHex(byte bytes[]) {
		return bufferToHex(bytes, 0, bytes.length);
	}

	private static String bufferToHex(byte bytes[], int m, int n) {
		StringBuffer stringbuffer = new StringBuffer(2 * n);
		int k = m + n;
		for (int l = m; l < k; l++) {
			appendHexPair(bytes[l], stringbuffer);
		}
		return stringbuffer.toString();
	}

	private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
		char c0 = hexDigits[(bt & 0xf0) >> 4];
		char c1 = hexDigits[bt & 0xf];
		stringbuffer.append(c0);
		stringbuffer.append(c1);
	}

	public static boolean checkPassword(String password, String md5PwdStr) {
		String s = getMD5String(password);
		return s.equals(md5PwdStr);
	}

	public static void main(String[] args) throws IOException {
		long begin = System.currentTimeMillis();

		File big = new File("f:\\tservice 03.01.rar");
		String md5 = getFileMD5String(big);

		long end = System.currentTimeMillis();
//		System.out.println("MD5-----md5:" + md5);
//		System.out.println("MD5-----time:" + ((end - begin)) + "ms");

	}

}
