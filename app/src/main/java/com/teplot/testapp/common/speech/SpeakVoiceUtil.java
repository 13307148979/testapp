package com.teplot.testapp.common.speech;

import android.content.Context;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;


import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import com.teplot.testapp.R;

/**
 * 语音合成
 */
public class SpeakVoiceUtil {

	private static Toast mToast;
	// 缓冲进度
	private static int mPercentForBuffering = 0;
	// 播放进度
	private static int mPercentForPlaying = 0;
	// 语音合成对象
	private SpeechSynthesizer mTts;
	// 默认发音人
	private String voicer = "xiaoyan";

	public static Context mContext;
	public SpeakVoiceUtil(Context context,String voicer){
		this.mContext = context;
		this.voicer = voicer;
		mTts = SpeechSynthesizer.createSynthesizer(context, mTtsInitListener);
	}
	
	public void speak(String strTextToSpeech){
		mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
		mTts.setParameter( SpeechConstant.ENGINE_MODE, SpeechConstant.MODE_AUTO);
		
//		//设置合成语速
//		mTts.setParameter(SpeechConstant.SPEED, mSharedPreferences.getString("speed_preference", "50"));
//		//设置合成音调
//		mTts.setParameter(SpeechConstant.PITCH, mSharedPreferences.getString("pitch_preference", "50"));
//		//设置合成音量
//		mTts.setParameter(SpeechConstant.VOLUME, mSharedPreferences.getString("volume_preference", "50"));
		mTts.setParameter( SpeechConstant.VOICE_NAME, voicer);
		//final String strTextToSpeech = "科大讯飞，让世界聆听我们的声音";
		mTts.startSpeaking( strTextToSpeech, mTtsListener);
	}
	/**
	 * 初始化监听。
	 */
	public static InitListener mTtsInitListener = new InitListener() {
		@Override
		public void onInit(int code) {
			Log.d("SpeakVoiceUtil", "InitListener init() code = " + code);
			if (code != ErrorCode.SUCCESS) {
        		showTip("初始化失败,错误码："+code);
        	} else {
				// 初始化成功，之后可以调用startSpeaking方法
        		// 注：有的开发者在onCreate方法中创建完合成对象之后马上就调用startSpeaking进行合成，
        		// 正确的做法是将onCreate中的startSpeaking调用移至这里
			}		
		}
	};
	/**
	 * 合成回调监听。
	 */
	public static SynthesizerListener mTtsListener = new SynthesizerListener() {

		@Override
		public void onSpeakBegin() {
			showTip("开始播放");
		}

		@Override
		public void onSpeakPaused() {
			showTip("暂停播放");
		}

		@Override
		public void onSpeakResumed() {
			showTip("继续播放");
		}

		@Override
		public void onBufferProgress(int percent, int beginPos, int endPos,
				String info) {
			// 合成进度
			mPercentForBuffering = percent;
			showTip(String.format(mContext.getString(R.string.tts_toast_format),
					mPercentForBuffering, mPercentForPlaying));
		}

		@Override
		public void onSpeakProgress(int percent, int beginPos, int endPos) {
			// 播放进度
			mPercentForPlaying = percent;
			showTip(String.format(mContext.getString(R.string.tts_toast_format),
					mPercentForBuffering, mPercentForPlaying));
		}

		@Override
		public void onCompleted(SpeechError error) {
			if (error == null) {
				showTip("播放完成");
			} else if (error != null) {
				showTip(error.getPlainDescription(true));
			}
		}

		@Override
		public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
			// 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
			// 若使用本地能力，会话id为null
			//	if (SpeechEvent.EVENT_SESSION_ID == eventType) {
			//		String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
			//		Log.d(TAG, "session id =" + sid);
			//	}
		}
	};
	private static void showTip(final String str) {
		mToast.setText(str);
		mToast.show();
	}
}
