package com.teplot.testapp.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v4.util.LruCache;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * 图片异步加载类
 * 
 * @author jantly
 * 
 * 
 */
public class ImageLoaderUtil {

	private static ImageLoaderUtil imageLoader;
	// applicationContext
	private Context context;

	// 内存缓存
	static final int MEM_CACHE_DEFAULT_SIZE = 5 * 1024 * 1024;
	// 一级内存缓存基于 LruCache
	private LruCache<String, Bitmap> memCache;

	// 文件缓存
	/**
	 * 文件缓存保存目录
	 */
	private File cacheDir;
	// 查找路径
	private Map<String,Boolean> findPaths = new HashMap<String,Boolean>();

	//默认图片id
	private int defaultPicResId;
	//默认缩放
	private int requiredSize = 100;

	public ImageLoaderUtil(Context context) {

	}

	/**
	 * 初始化方法（初始化各种缓存配置），推荐在Application中调用
	 *
	 * @param context
	 */
	public static void init(Context context, int defaultPicResId, String cachePath) {
		imageLoader = new ImageLoaderUtil();
		imageLoader.initMemoryCache();
		imageLoader.initFileCache(context,cachePath);
		imageLoader.context = context;
		imageLoader.defaultPicResId = defaultPicResId;
	}

	/**
	 * 获取ImageLoader实例
	 *
	 * @return
	 */
	public static ImageLoaderUtil getInstances() {
		if (null == imageLoader) {
			
		}
		return imageLoader;
	}



	public ImageLoaderUtil () {

	}


	/**
	 * 从 url 加载图片
	 * 
	 * @param imageView
	 * @param url
	 */
	public void loadImage(String url,ImageView imageView) {
		//System.out.println("下载地址为"+url);
		loadImage(url, imageView, requiredSize, null, defaultPicResId , false);
	}
	public void loadImageCorner(String url,ImageView imageView,int pixels) {
		//System.out.println("下载地址为"+url);
		loadImage(url, imageView, requiredSize, null, defaultPicResId , false,pixels);
	}
	public void loadImage(String url, ImageView imageView, int requiredSize) {
		loadImage(url, imageView, requiredSize, null, defaultPicResId , false);
	}

	public void loadImage(String url,ImageView imageView, OnImageLoaderListener listener) {
		loadImage(url, imageView, requiredSize, null, defaultPicResId , false);
	}
	public void loadImage(String url, OnImageLoaderListener listener) {
		loadImage(url, requiredSize, listener, defaultPicResId , false);
	}
	public void loadImage(String url, ImageView imageView, OnImageLoaderListener listener,int defaultPicResId) {
		loadImage(url, imageView, requiredSize, null, defaultPicResId , false);
	}
	public void loadImage(String url,int requiredSize, OnImageLoaderListener listener,
						  int defaultPicResId, boolean isOnlyMonmery) {
		if (StringUtil.isEmpty(url))
			return;
		String urlTag = getIdentityCode(url, requiredSize);

		if (StringUtil.isEmpty(url)) {
			return;
		}
		// 先从内存缓存中查找
		Bitmap bitmap = getMemory(urlTag);
		if (bitmap != null) {
			if (null != listener) {
				listener.onFinishedImageLoader(null, bitmap);
			}
		} else {

			if (isOnlyMonmery)
				return;

			ImageToLoad imageToLoad = new ImageToLoad(url,null, listener);
			new ImageDownloadTask(imageToLoad, requiredSize).execute();
		}
	}
	public void loadImage(String url, ImageView imageView, int requiredSize, OnImageLoaderListener listener, int defaultPicResId, boolean isOnlyMonmery
	) {
		if (StringUtil.isEmpty(url))
			return;
		String urlTag = getIdentityCode(url, requiredSize);

		if (imageView.getTag() != null
				&& !imageView.getTag().equals(urlTag)) {
			return;
		}

		if (StringUtil.isEmpty(url)) {
			if (defaultPicResId > 0)
				imageView.setImageResource(defaultPicResId);
			return;
		}
		// 先从内存缓存中查找
		Bitmap bitmap = getMemory(urlTag);
		if (bitmap != null) {
			imageView.setTag(urlTag);
			imageView.setImageBitmap(bitmap);
			if (null != listener) {
				listener.onFinishedImageLoader(imageView, bitmap); 
			}
		} else {

			if (defaultPicResId == 0) {
				imageView.setImageResource(this.defaultPicResId);
			}
			if (isOnlyMonmery)
				return;

			imageView.setTag(urlTag);
			ImageToLoad imageToLoad = new ImageToLoad(url,imageView, listener);
			new ImageDownloadTask(imageToLoad, requiredSize).execute();
		}
	}
	public void loadImage(String url, ImageView imageView, int requiredSize, OnImageLoaderListener listener, int defaultPicResId, boolean isOnlyMonmery
	,int pixels) {
		if (StringUtil.isEmpty(url))
			return;
		String urlTag = getIdentityCode(url, requiredSize);

		if (imageView.getTag() != null
				&& !imageView.getTag().equals(urlTag)) {
			return;
		}

		if (StringUtil.isEmpty(url)) {
			if (defaultPicResId > 0)
				imageView.setImageResource(defaultPicResId);
			return;
		}
		// 先从内存缓存中查找
		Bitmap bitmap = getMemory(urlTag);
		if (bitmap != null) {
			imageView.setTag(urlTag);
			Bitmap bitmap2 = ImageUtil.toRoundCorner(bitmap,pixels);
			imageView.setImageBitmap(bitmap2);
			if (null != listener) {
				listener.onFinishedImageLoader(imageView, bitmap);
			}
		} else {

			if (defaultPicResId == 0) {
				imageView.setImageResource(this.defaultPicResId);
			}
			if (isOnlyMonmery)
				return;

			imageView.setTag(urlTag);
			ImageToLoad imageToLoad = new ImageToLoad(url,pixels,imageView, listener);
			new ImageDownloadTask(imageToLoad, requiredSize).execute();
		}
	}
	/**
	 * 图片加载对象的封装
	 */
	private class ImageToLoad {
		public String url;
		public ImageView imageView;
		public OnImageLoaderListener onImageLoaderListener;
		public int currentSize;
		public int totalSize;
		public Bitmap bitmap;
		private int pixels;

		public ImageToLoad(String u, ImageView i, OnImageLoaderListener listener) {
			url = u;
			imageView = i;
			this.onImageLoaderListener = listener;
		}
		public ImageToLoad(String u,int ps, ImageView i, OnImageLoaderListener listener) {
			url = u;
			imageView = i;
			pixels = ps;
			this.onImageLoaderListener = listener;
		}
	}


	static final int IMAGE_LOADER_PROCESS = 0x01;

	class ImageDownloadTask extends AsyncTask<Object, Integer, Bitmap> {

		private ImageToLoad imageToLoad;
		private int requiredSize;

		public ImageDownloadTask(ImageToLoad imageToLoad, int requiredSize) {
			this.imageToLoad = imageToLoad;
			this.requiredSize = requiredSize;
		}

		@Override
		protected Bitmap doInBackground(Object... params) {
			Bitmap bitmap = null;
			File f = getFile(imageToLoad.url, true);
			if (f.exists() && f.length() > 0) {
                //bitmap = ImageUtil.getBitmapByPath(f.getAbsolutePath());
				bitmap = ImageUtil.zoomBitmap(f, requiredSize);
			} else if (imageToLoad.url.contains("http://")){
				bitmap = downloadUrlToStream(f, imageToLoad);
			}
			return bitmap;
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			super.onPostExecute(result);
			if (result != null) {
				String urlTag = getIdentityCode(imageToLoad.url, requiredSize);
				putMemory(urlTag, result);

				// 通过 tag 来防止图片错位
				if (imageToLoad.imageView != null
						&& imageToLoad.imageView.getTag() != null
						&& imageToLoad.imageView.getTag().equals(urlTag)) {
					if (imageToLoad.onImageLoaderListener != null) {
						imageToLoad.onImageLoaderListener.onFinishedImageLoader(imageToLoad.imageView,result);
					} else {
						if (imageToLoad.pixels!=0){
							Bitmap bitmap2 = ImageUtil.toRoundCorner(result,imageToLoad.pixels);
							imageToLoad.imageView.setImageBitmap(bitmap2);
						}else {
							imageToLoad.imageView.setImageBitmap(result);
						}

					}
				}
			}
		}

		private Bitmap downloadUrlToStream(File f,ImageToLoad imageToLoad) {
			InputStream is = null;
			OutputStream os = null;

			try {
				if (f.exists()) {
					f.delete();
					f.createNewFile();
				}

				os = new FileOutputStream(f);
				URL imageUrl = new URL(imageToLoad.url);
				HttpURLConnection conn = (HttpURLConnection) imageUrl
						.openConnection();
				conn.setConnectTimeout(30000);
				conn.setReadTimeout(30000);
				conn.setInstanceFollowRedirects(true);
				is = conn.getInputStream();

				imageToLoad.totalSize = conn.getContentLength();
				int buffer_size = 1024;
				byte[] bytes = new byte[buffer_size];
				for (; ; ) {
					int count = is.read(bytes, 0, buffer_size);

					if (count == -1) {
						break;
					}
					os.write(bytes, 0, count);

					if (null != imageToLoad.onImageLoaderListener) { 
						Message msg = new Message();
						imageToLoad.currentSize += count;
						msg.arg1 = IMAGE_LOADER_PROCESS;
						msg.obj = imageToLoad;
						imageToLoad.onImageLoaderListener.handler.sendMessage(msg);
					}
				}

				//return ImageUtil.getBitmapByPath(f.getAbsolutePath());
				return ImageUtil.zoomBitmap(f, requiredSize);
			} catch (Exception ex) {
				return null;
			} finally {
				try {
					if (is != null) {
						is.close();
					}
					if (os != null) {
						os.close();
					}
				} catch (final IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 清空文件缓存或内存缓存
	 */
	public void clear() {
		//clearMemory();
		//clearFile();
	}

/////////////////////////////////////////////////////////////

	public void initMemoryCache () {
		memCache = new LruCache<String, Bitmap>(MEM_CACHE_DEFAULT_SIZE) {
			@Override
			protected int sizeOf(String key, Bitmap bitmap) {
				return bitmap.getByteCount();
			}
		};
	}

	/**
	 * 从内存缓存中拿
	 *
	 * @param url
	 */
	public Bitmap getMemory(String url) {
		return memCache.get(url);
	}

	/**
	 * 加入到内存缓存中
	 *
	 * @param url
	 * @param bitmap
	 */
	public void putMemory(String url, Bitmap bitmap) {
		memCache.put(url, bitmap);
	}

	/**
	 * 移除缓存
	 *
	 * @param url
	 */
	public void removeMemory(String url) {
		if (url != null) {
			Bitmap bm = memCache.remove(url);
			if (bm != null)
				bm.recycle();
		}
	}

	public void clearMemory () {
		if (memCache.size() > 0)
			memCache.evictAll();
	}

/////////////////////////////////////////////////////////////////////////

	public void initFileCache(Context context, String cachePath) {
		// 如果有SD卡则在SD卡中建一个目录存放缓存的图片
		// 没有SD卡就放在系统的缓存目录中
		if (android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED)) {
			cacheDir = new File(cachePath);
		} else {
			cacheDir = context.getCacheDir();
		}
		if (!cacheDir.exists()) {
			cacheDir.mkdirs();
		}
	}

	public void addFindPath (String path) {
		findPaths.put(path, false);
	}

	public void addFindPath (String path, boolean nameIsHashCode) {
		findPaths.put(path, nameIsHashCode);
	}

	/**
	 * 获取指定文件缓存
	 *
	 * @param url
	 * @return
	 */
	public File getFile(String url, boolean findOther) {
		// 将url的hashCode作为缓存的文件名
		String filename = String.valueOf(url.hashCode());
		// Another possible solution
		// String filename = URLEncoder.encode(url);
		File f = new File(cacheDir, filename);
		if (findOther) {
			if (!f.exists() && findPaths.size() > 0) {
				for (Map.Entry<String,Boolean>  entry : findPaths.entrySet()) {
					String name = filename;
					if (entry.getValue()) {
						name = FileUtil.getFileName(url);
					}
					File f1 = new File(entry.getKey(), name);
					if (f1.exists()) {
						f = f1;
						break;
					}
				}
			}
		}
		return f;
	}

	/**
	 * 移除缓存
	 *
	 * @param url
	 */
	public void removeFile(String url) {
		if (url != null) {
			String filename = String.valueOf(url.hashCode());
			// Another possible solution
			// String filename = URLEncoder.encode(url);
			File f = new File(cacheDir, filename);
			f.delete();
		}
	}
	/**
	 * 清除文件缓存
	 */
	public void clearFile() {
		File[] files = cacheDir.listFiles();
		if (files == null) {
			return;
		}

		for (File f : files) {
			f.delete();
		}
	}

	////////////////////////////////////////////////

	private static final String DIVIDER = "_";

	public String getIdentityCode(String uri ,int requiredSize) {
		return uri + DIVIDER + requiredSize;
	}

	public String getUriFromIdentityCode(String indentityCode) {
		return indentityCode.substring(0, indentityCode.lastIndexOf(DIVIDER));
	}

	/**
	 * 图片加载监听器
	 */
	public abstract static class OnImageLoaderListener {

		public Handler handler;

		public OnImageLoaderListener () {

		}

		public OnImageLoaderListener (Handler handler) {
			this.handler = handler;
		}
		/**
		 * 用于在加载过程中回调更新UI进度
		 *
		 * @param imageView
		 * @param currentSize
		 * @param totalSize
		 */
		public void onProgressImageLoader(ImageView imageView, int currentSize, int totalSize) {

		}

		/**
		 * 完成加载后回调
		 *
		 * @param imageView 要加载ImageView
		 * @param bitmap    加载的图片
		 */
		public abstract void onFinishedImageLoader(ImageView imageView, Bitmap bitmap);
	}
}
