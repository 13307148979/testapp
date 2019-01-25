package com.teplot.testapp.apps;

import android.os.Build;
import android.os.Environment;

public class AppData {
	
	public static float DENSITY;
	public static int HEIGTH, WIGTH;
	public static final int SDK_INT = Build.VERSION.SDK_INT;
	// net data
	public static final int HTTP_CB_START = -51;
	public static final int HTTP_CB_EXP = -50;
	public static final int HTTP_CB_COP = -49;
	public static final int HTTP_CB_PROGRESS = -48;
	// 数据库名称
	public static final String DATABASE_NAME = "farmers1.db";
	// 数据库版本号
	public static final int DATABASE_VERSION = 1;

	public static  String STRING_APP_NAME = "ynt-1.0.apk";

    //文件相关路径
	public static final String SD_PATH =  Environment.getExternalStorageDirectory().getAbsolutePath();
	public static final String BASE_PATH = "/apptest";
	public static final String PICTURE_PATH = "/apptest/cache/picture";//缓存图片目录
	public static final String VIDIO_PATH = "/apptest/cache/vidio";//视频目录

	public static String STRING_UUID = "";
	public static String STRING_WXSEND_TYPE = "";
	// APP_ID 替换为你的应用从官方网站申请到的合法appID
	public static String APP_ID = "wx2cf77c1fec3bcc54";//MD5值 ： a77d11037ffc581b7ce61084e3bf6700//测试 2793834a61f47a445bc5a199d2c8e53f
	public static String WX_APP_SECRET = "42d90efbfd37ef119e0678cbb8d90775";
	public static String QQ_APP_ID = "1108068120";//SHA1值：E1:0A:A9:96:F6:AA:D2:9E:42:9D:89:5C:9A:DF:86:3E:D8:A4:51:75

	public static String TENCENT_AI_APP_ID = "2111212696";
	public static String TENCENT_AI_APP_KEY = "lI1vTDtyaclbmDNo";


    //public static final String BASE_URL = "http://120.78.209.238:50010/v1/";
	public static final String BASE_URL = "https://api.ai.qq.com/fcgi-bin/nlp/";

	public static final String URL_LOGIN = BASE_URL+"user/login";
	//================================首页===============================
   //基础文本分析--分词
	public static final String URL_NLP_WORDSEG= BASE_URL+"nlp_wordseg";
	//词性
	public static final String URL_NLP_WORDPOS= BASE_URL+"nlp_wordpos";
	//专有名词
	public static final String URL_NLP_WORDNER= BASE_URL+"nlp_wordner";
	//同义词
	public static final String URL_NLP_WORDSYN= BASE_URL+"nlp_wordsyn";
    //文本翻译（AI Lab）
    public static final String URL_NLP_TEXTTRANS= BASE_URL+"nlp_texttrans";
	//文本翻译（翻译君
	public static final String URL_NLP_TEXTTRANSLATE= BASE_URL+"nlp_texttranslate";
	//图片翻译
	public static final String URL_NLP_IMAGETRANSLATE= BASE_URL+"nlp_imagetranslate";

	//语义解析>意图成分
	public static final String URL_NLP_WORDCOM= BASE_URL+"nlp_wordcom";
	//语义解析>情感分析
	public static final String URL_NLP_TEXTPOLAR= BASE_URL+"nlp_textpolar";
	//语义解析>情感分析
	public static final String URL_NLP_TEXTCHAT= BASE_URL+"nlp_textchat";

	//图片识别
	public static final String URL_VISION_IMGTOTEXT= "https://api.ai.qq.com/fcgi-bin/vision/vision_imgtotext";
	public static final String URL_IMAGE_TAG= "https://api.ai.qq.com/fcgi-bin/image/image_tag";
	public static final String URL_IMAGE_FUZZY= "https://api.ai.qq.com/fcgi-bin/image/image_fuzzy";
	public static final String URL_IMAGE_FOOD= "https://api.ai.qq.com/fcgi-bin/image/image_food";
	public static final String URL_VISION_SCENER= "https://api.ai.qq.com/fcgi-bin/vision/vision_scener";
	public static final String URL_VISION_OBJECTR= "https://api.ai.qq.com/fcgi-bin/vision/vision_objectr";

	//图片审核
	public static final String URL_IMAGE_TERRORISM= "https://api.ai.qq.com/fcgi-bin/image/image_terrorism";
	public static final String URL_VISION_PORN= "https://api.ai.qq.com/fcgi-bin/vision/vision_porn";

	//OCR
	public static final String URL_OCR_IDCARDOCR= "https://api.ai.qq.com/fcgi-bin/ocr/ocr_idcardocr";
	public static final String URL_OCR_DRIVERLICENSEOCR= "https://api.ai.qq.com/fcgi-bin/ocr/ocr_driverlicenseocr";
	public static final String URL_OCR_BIZLICENSEOCR= "https://api.ai.qq.com/fcgi-bin/ocr/ocr_bizlicenseocr";
	public static final String URL_OCR_CREDITCARDOCR= "https://api.ai.qq.com/fcgi-bin/ocr/ocr_creditcardocr";
	public static final String URL_OCR_HANDWRITINGOCR= "https://api.ai.qq.com/fcgi-bin/ocr/ocr_handwritingocr";
	public static final String URL_OCR_PLATEOCR= "https://api.ai.qq.com/fcgi-bin/ocr/ocr_plateocr";
	public static final String URL_OCR_BCOCR= "https://api.ai.qq.com/fcgi-bin/ocr/ocr_bcocr";

	//图片特效
	public static final String URL_PTU_IMGFILTER= "https://api.ai.qq.com/fcgi-bin/ptu/ptu_imgfilter";
	public static final String URL_VISION_IMGFILTER= "https://api.ai.qq.com/fcgi-bin/vision/vision_imgfilter";
	public static final String URL_PTU_FACECOSMETIC= "https://api.ai.qq.com/fcgi-bin/ptu/ptu_facecosmetic";
	public static final String URL_PTU_FACEDECORATION= "https://api.ai.qq.com/fcgi-bin/ptu/ptu_facedecoration";
	public static final String URL_PTU_FACESTICKER= "https://api.ai.qq.com/fcgi-bin/ptu/ptu_facesticker";
	public static final String URL_PTU_FACEAGE= "https://api.ai.qq.com/fcgi-bin/ptu/ptu_faceage";

	//人脸识别
	public static final String URL_FACE_DETECTFACE= "https://api.ai.qq.com/fcgi-bin/face/face_detectface";
	public static final String URL_FACE_DETECTMULTIFACE= "https://api.ai.qq.com/fcgi-bin/face/face_detectmultiface";
	public static final String URL_FACE_DETECTCROSSAGEFACE= "https://api.ai.qq.com/fcgi-bin/face/face_detectcrossageface";
	public static final String URL_FACE_FACESHAPE= "https://api.ai.qq.com/fcgi-bin/face/face_faceshape";
	public static final String URL_FACE_FACECOMPARE= "https://api.ai.qq.com/fcgi-bin/face/face_facecompare";


	public static final String URL_FACE_NEWPERSON= "https://api.ai.qq.com/fcgi-bin/face/face_newperson";
	public static final String URL_FACE_GETPERSONIDS= "https://api.ai.qq.com/fcgi-bin/face/face_getpersonids";

	public static final String URL_FACE_DELPERSON= "https://api.ai.qq.com/fcgi-bin/face/face_delperson";
	public static final String URL_FACE_GETINFO= "https://api.ai.qq.com/fcgi-bin/face/face_getinfo";
	public static final String URL_FACE_ADDFACE= "https://api.ai.qq.com/fcgi-bin/face/face_addface";
	public static final String URL_FACE_DELFACE= "https://api.ai.qq.com/fcgi-bin/face/face_delface";
	public static final String URL_FACE_SETINFO= "https://api.ai.qq.com/fcgi-bin/face/face_setinfo";

	public static final String URL_FACE_GETFACEINFO= "https://api.ai.qq.com/fcgi-bin/face/face_getfaceinfo";

	public static final String URL_FACE_FACEVERIFY= "https://api.ai.qq.com/fcgi-bin/face/face_faceverify";

	public static final String URL_FACE_FACEIDENTIFY= "https://api.ai.qq.com/fcgi-bin/face/face_faceidentify";

}