package com.teplot.testapp.utils;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.teplot.testapp.R;
import com.teplot.testapp.been.details.FaceData;
import com.teplot.testapp.been.details.FaceShapeData;
import com.teplot.testapp.been.details.ImgShiBieData;
import com.teplot.testapp.been.details.WeiZhiData;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class StringUtil {

	private static String TAG = "StringUtil";
	public static boolean isEmpty(String s) {
		if (isNull(s))
			return true;
		else if (s.trim().equalsIgnoreCase(""))
			return true;
		else
			return false;
	}

	public static boolean isNull(String s) {
		if (s == null)
			return true;
		else if (s.equalsIgnoreCase("null"))
			return true;
		else
			return false;
	}

	public static String trimNull(String s) {
		if (isNull(s))
			return "";
		else
			return s.trim();
	}

	public static String trimZero(String s) {
		if (isNull(s))
			return "0";
		else
			return s.trim();
	}

	/**
	 * String 转为 int
	 */
	public static int string2int(String str) {
		if (StringUtil.isEmpty(str))
			return 0;
		try {
			if (str.indexOf(".") > -1)
				str = str.substring(0, str.indexOf("."));
			return Integer.parseInt(str);
		} catch (Exception e) {
			//Logger.e(TAG, e.getMessage());
			Log.e(TAG, e.getMessage());
			return 0;
		}
	}

	/**
	 * String 转为 long
	 */
	public static long string2long(String str) {
		if (StringUtil.isEmpty(str))
			return 0;
		try {
			if (str.indexOf(".") > -1)
				str = str.substring(0, str.indexOf("."));
			return Long.parseLong(str);
		} catch (Exception e) {
			//Logger.e(TAG, e.getMessage());
			Log.e(TAG, e.getMessage());
			return 0;
		}
	}

	/**
	 * String 转为 double
	 */
	public static double string2double(String sIn) {
		double dOut;
		if ((trimNull(sIn)).trim().equals("")) {
			dOut = 0;
		} else {
			dOut = isNumeric(sIn) ? Double.parseDouble(sIn) : 0;
		}
		return dOut;
	}

	/**
	 * String 转为 float
	 */
	public static float string2float(String sIn) {
		float dOut;
		if ((trimNull(sIn)).trim().equals("")) {
			dOut = 0;
		} else {
			dOut = isNumeric(sIn) ? Float.parseFloat(sIn) : 0;
		}
		return dOut;
	}
	/**
	 * String 转为 String[]
	 */
	public static String[] string2array1(String strIn, String strSpc) {
		if (strIn == null)
			strIn = "";
		else
			strIn = strIn.trim();
		return strIn.split(strSpc);
	}

	/**
	 * 以";"分割字符串  得到String[] 
	 * @param strIn
	 * @return
	 */
	public static String[] string2array(String strIn) {
		return string2array1(strIn, ";");
	}

	/**
	 * String 转为 String[][]
	 */
	public static String[][] string2array2(String strIn, String strSpc1, String strSpc2) {
		int intLength, intLength2;
		String[] arrIn = string2array1(strIn, strSpc1);
		intLength = arrIn.length;
		if (intLength > 0)
			intLength2 = string2array1(arrIn[0], strSpc2).length;
		else
			intLength2 = 0;
		String[][] arrOut = new String[intLength][intLength2];
		for (int i = 0; i < intLength; i++) {
			arrOut[i] = string2array1(arrIn[i], strSpc2);
		}
		return arrOut;
	}

	/**
	 * 字符串替换函数
	 */
	public static String strReplace(String sAll, String sOld, String sNew) {
		int iT = 0;
		String sF = null, sH = null;
		// 如果新串中包括旧串,不让替多只让替少
		if (sNew.indexOf(sOld) != -1)
			return sAll;

		if (sAll == null || sOld == null || sNew == null)
			return sAll;
		iT = sAll.indexOf(sOld);
		while (iT != -1) {
			sF = sAll.substring(0, iT);
			sH = sAll.substring(iT + sOld.length());
			sAll = sF + sNew + sH;
			iT = sAll.indexOf(sOld);
		}
		return sAll;
	}

	/**
	 * 判断是否是数字 如：232.55
	 */
	public static boolean isNumeric(String str) {
		if (str == null)
			return false;
		int dom = 0;
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) != '.') {
				if (i == 0 && str.substring(0, 1).equalsIgnoreCase("-"))
					continue;
				if (str.charAt(i) > '9' || str.charAt(i) < '0') {
					return false;
				}
			} else {
				dom++;
				if (dom > 1)
					return false;
			}
		}
		return true;
	}

	/**
	 * 判断是否全是数字
	 * 123
	 * 这里是整数的判断
	 * @param str  
	 * @return
	 */
	public static boolean isAllNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}
	/**
	 * 判断String的首位是否是数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isFirstNumeric(String str) {
		String s = str.substring(0, 1);
		Pattern pattern = Pattern.compile("[0-9]");
		Matcher isNum = pattern.matcher(s);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	/**
	 * 发送同步短信的16位字符串数据
	 * @param phoneNumber   长度一定小于16
	 * @return
	 */
	public static String sendSynSms(String phoneNumber) {
		
		StringBuffer sb = new StringBuffer();
		sb.append(phoneNumber);
		//电话号码的长度
		int phoneStr = phoneNumber.length();
		//剩余电话号码的长度
		int fStr  = 16 - phoneStr;
		for (int i = 0; i < fStr; i++) {
			sb.append("F");
		}
		
		return sb.toString();
	}
	
	/**
	 * 判断email格式是否正确
	 * 
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email) {
		Pattern emailer = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
		if (email == null || email.trim().length() == 0)
			return false;
		return emailer.matcher(email).matches();
	}

	/**
	 * 判断是否为手机号码
	 * 
	 * @param mobiles
	 * @return
	 */
	public static boolean isMobileNumber(String mobiles) {
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}

	/**
	 * 验证号码 手机号 固话 18电信集团号 均可
	 */
	public static boolean isPhoneNumberValid(String phoneNumber) {
		boolean isValid = false;
		String expression = "(((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8})"
				+ "|(0(\\d{3}|\\d{2})(\\d{8}|\\d{7}))|(\\d{8}|\\d{7})" + "|(18)";
		CharSequence inputStr = phoneNumber;
		Pattern pattern = Pattern.compile(expression);
		Matcher matcher = pattern.matcher(inputStr);
		if (matcher.matches()) {
			isValid = true;
		}
		return isValid;
	}
	/**
	 * 验证号码 手机号 固话 18电信集团号及13位的物联卡   均可
	 * */
	public static boolean isPhoneNumberValid2(String phoneNumber) {
		boolean isValid = false;
		/*String expression = "(1[3578]\\d{9})" +"|14[57]\\d{8}"+
				"|(0(\\d{3}|\\d{2})(\\d{8}|\\d{7}))|(\\d{8}|\\d{7})" +
				"|(18)";*/
		//170号段还未卖，是虚拟运营商的号段
		String expression1 = "((1[3-8])\\d{9})";
//				+"|(0(\\d{3}|\\d{2})(\\d{8}|\\d{7}))|(\\d{8}|\\d{7})" +
//				"|(18)|\\d{13}|\\d{12}";
		///^(0|86|17951)?(13[0-9]|15[012356789]|17[0678]|18[0-9]|14[57])[0-9]{8}$/
		CharSequence inputStr = phoneNumber;
		Pattern pattern = Pattern.compile(expression1);
		Matcher matcher = pattern.matcher(inputStr);
		if (matcher.matches()) {
			isValid = true;
		}
		return isValid;
	}
	/**
	 * 保留x位小数的M
	 * 
	 * @param
	 *            l:要转化的比特数 int i:保留的位数
	 * @return
	 */
	public static String formatM(long l, int i) {

		if (l <= 0)
			return "0.0M";
		double d = l / 1024 / 1024.0;
		long d1 = Math.round(d * Math.pow(10, i));
		double s1 = d1 * 1.0 / Math.pow(10, i);
		return s1 + "M";
	}

	/**
	 * 
	 * 去除回车和换行符
	 * 
	 * @param str
	 * @return
	 */
	public static String replaceBlank2(String str) {
		if (str != null) { 
			String a = strReplace(str, "\\r", "");
			str = strReplace(a, "\\n", "");
		}
		return replaceNull(str);
	}
	
	/**
	 *  转义
	 * @param content
	 * @param fzFlag 反转义
	 * @return
	 */
	public static String zy(String content,boolean fzFlag) {
		if (fzFlag) {
			content = content.replaceAll("<#", "(");
			content = content.replaceAll("#>", ")");
			content = content.replaceAll("\\$\\$", "|");
		} else {
			content = content.replaceAll("\\(", "<#");
			content = content.replaceAll("\\)", "#>");
			content = content.replaceAll("\\|", "\\$\\$");
		}
		return content;
	}

	/**
	 * 
	 * 转义
	 * 
	 * @param str
	 * @return
	 */
	public static String changeMsg(String str) {
		if (str != null) { 
			String c = replaceBlank2(str);
			str = zy(c,false);
		}
		return replaceNull(str);
	}
	/**
	 * 
	 * 逆转义
	 * 
	 * @param str
	 * @return
	 */
	public static String changeoverMsg(String str) {
		if (str != null) { 
			String c = replaceBlank2(str);
			str = zy(c,true);;
		}
		return replaceNull(str);
	}

	/**
	 * 
	 * 替换null为""
	 * 
	 * @param str
	 * @return
	 */
	public static String replaceNull(String str) {
		String dest = "";
		if (str != null) {
			Pattern p = Pattern.compile("null");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("\"" + "\"");
		}
		return dest;
	}
	//=========================================
	public static String status(String result){
		String[] str1 =string2array1(result, ",");
    	String[] str2 =string2array1(str1[0], ":");
		return str2[1];	
	}
	/**
	 * 截取字符串
	 * @param orignal
	 * @param count
	 * @return
	 */
	public static String split(String orignal, int count) {
	    // count 表示多少个字节
	    // 1个中文字符是2个字节
	    char[] b = orignal.toCharArray();
	    StringBuilder sb = new StringBuilder(count);
	    for (int i = 0; i < b.length; i++) {
	        if (count <= 0) {
	            break;
	        }
	     String temp = String.valueOf(b[i]);
	        if (temp.getBytes().length > 1) {
	            count -= 2;
	            if (count < 0) {
	                break;
	            }
	        } else {
	            count--;
	        }
	        sb.append(temp);
	    }
	    return sb.toString();
	}

	
	
	//另加的=====================================================//
	/**
	 * 将byte的bit字符串转换成十六进制的字符串
	 * @param byteStr
	 * @return
	 */
//	public static String byteHexStr(String byteStr){
//		int byteINt = Integer.valueOf(byteStr, 2);
//		String hxInt = ConversionUtil.intToStringHex(byteINt,2);
//		return hxInt;
//	}
	
	/**
	 * 用utf-8表示String
	 * @param s
	 * @return
	 */
	public static String toUtf8String(String s) {  
	    if (s == null || s.equals("")) {  
	        return null;  
	    }  
	    StringBuffer sb = new StringBuffer();  
	    try {  
	        char c;  
	        for (int i = 0; i < s.length(); i++) {  
	            c = s.charAt(i);  
	            if (c >= 0 && c <= 255) {  
	                sb.append(c);  
	            } else {  
	                byte[] b;  
	                b = Character.toString(c).getBytes("utf-8");  
	                for (int j = 0; j < b.length; j++) {  
	                    int k = b[j];  
	                    if (k < 0)  
	                        k += 256;  
	                    sb.append("%" + Integer.toHexString(k).toUpperCase());  
	                }  
	            }  
	        }  
	    } catch (Exception e) {  
	        e.printStackTrace();  
	    }  
	    return sb.toString();  
	}
	/**
	 * 用utf-8表示String
	 * @param s
	 * @return
	 */
	public static String toGbkString(String s) {
		if (s == null || s.equals("")) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		try {
			char c;
			for (int i = 0; i < s.length(); i++) {
				c = s.charAt(i);
				if (c >= 0 && c <= 255) {
					sb.append(c);
				} else {
					byte[] b;
					b = Character.toString(c).getBytes("gbk");
					for (int j = 0; j < b.length; j++) {
						int k = b[j];
						if (k < 0)
							k += 256;
						sb.append("%" + Integer.toHexString(k).toUpperCase());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	//判断输入号码是否在3-13位
	public static boolean isPhoneLen(int phoneLen) {
			if (phoneLen<=13 && phoneLen>=3) {
				return true;
			}
			return false;
	}

	public static String getSexString(int sex) {
		String sexString = "男";
		if (sex == 1) {
			sexString = "女";
		}
		return sexString;
	}

	public static String getExpressionString(int expression) {
		String sexString ;
		if (expression <= 0) {
			sexString = "没有笑容";
		}else if (expression <= 50){
			sexString = "微笑";
		}else {
			sexString = "大笑";
		}
		return sexString;
	}

	//情感描述
	public static String getTypePolarString(int polar) {
       String name = "";
		switch (polar){
			case -1:
				name = "负面";
				break;
			case 0:
				name = "中性";
				break;
			case 1:
				name = "正面";
				break;
			default:
				name = "正面";
				break;
		}
		return name;
	}
    //情感描述
    public static String getTypeComTokensString(int com_type) {
        String name = "";
        switch (com_type){
            case 0:
                name = "未知";
                break;
            case 1:
                name = "歌词";
                break;
            case 2:
                name = "下载地址";
                break;
            case 3:
                name = "乐器";
                break;
            case 4:
                name = "歌曲";
                break;
            case 5:
                name = "人名";
                break;
            case 6:
                name = "时间";
                break;
            case 7:
                name = "地点";
                break;
            case 8:
                name = "风格";
                break;
            case 9:
                name = "数字";
                break;
            case 10:
                name = "视频";
                break;
            case 11:
                name = "民族";
                break;
            case 12:
                name = "专辑";
                break;
            case 13:
                name = "序数词";
                break;
            case 14:
                name = "综艺";
                break;
            case 15:
                name = "乐队";
                break;
            case 16:
                name = "景点";
                break;
            case 17:
                name = "电影";
                break;
            case 18:
                name = "电视剧";
                break;
            case 19:
                name = "百科";
                break;
            case 34:
                name = "股票名称";
                break;
            case 35:
                name = "股票代码";
                break;
            case 36:
                name = "指数";
                break;
            case 37:
                name = "价格";
                break;
            case 38:
                name = "行情";
                break;
            case 40:
                name = "山";
                break;
            case 41:
                name = "湖";
                break;
            case 42:
                name = "是否";
                break;
            case 43:
                name = "餐馆";
                break;
            case 44:
                name = "菜名";
                break;
            case 45:
                name = "儿歌";
                break;
            case 46:
                name = "故事";
                break;
            case 47:
                name = "相声";
                break;
            case 48:
                name = "评书";
                break;
            case 49:
                name = "有声内容";
                break;
            case 128:
                name = "类别词";
                break;
            case 129:
                name = "关系词";
                break;
            case 130:
                name = "省略词";
                break;
            default:
                name = "未知";
                break;
        }
        return name;
    }
    //意图描述
    public static String getTypeIntentString(int intent) {
        String name = "";
        switch (intent){
            case 0:
                name = "未知";
                break;
            case 1:
                name = "天气";
                break;
            case 2:
                name = "音乐";
                break;
            case 3:
                name = "股票";
                break;
            case 4:
                name = "新闻";
                break;
            default:
                name = "未知";
                break;
        }
        return name;
    }

	public static String getIMAGE_TAGString(List<ImgShiBieData> list) {

		StringBuffer stringBuffer = new StringBuffer();
		for (int i = 0 ;i<list.size();i++){

			String tag_name = list.get(i).getTag_name();
			stringBuffer.append(tag_name);
			if (i<list.size()-1){
				stringBuffer.append("、");
			}
		}
		return stringBuffer.toString();
	}

	public static String getVISION_SCENERString(List<ImgShiBieData> list) {

		StringBuffer stringBuffer = new StringBuffer();
		for (int i = 0 ;i<list.size();i++){

			int label_id = list.get(i).getLabel_id();
			String  label_confd= list.get(i).getLabel_confd();
			if ("0.1".compareTo(label_confd)<0){
				String label_str = TencentAIStringUtils.getTypeLabelString(label_id);
				stringBuffer.append(label_str);
				stringBuffer.append("、");
//				if (i<list.size()-1){
//
//				}
			}

		}
		return stringBuffer.toString();
	}
	public static String getIMAGE_TERRORISMString(List<ImgShiBieData> list) {

		StringBuffer stringBuffer = new StringBuffer();
		for (int i = 0 ;i<list.size();i++){

			String tag_name = list.get(i).getTag_name();
			String  label_confd= list.get(i).getTag_confidence_f();
			if ("0.1".compareTo(label_confd)<0){
				String label_str = getTagNameString(tag_name);
				stringBuffer.append(label_str);
				stringBuffer.append("、");
//				if (i<list.size()-1){
//
//				}
			}

		}
		return stringBuffer.toString();
	}

	public static String getVISION_PORNString(List<ImgShiBieData> list) {

		StringBuffer stringBuffer = new StringBuffer();


		String content = "图片类别：正常图片、";
		int type = -1;
		for (int i = 0 ;i<list.size();i++){
			int normal = list.get(0).getTag_confidence();
			String tag_name = list.get(i).getTag_name();
			String  label_confd= list.get(i).getTag_confidence_f();
			int  tagConfidence= list.get(i).getTag_confidence();
			if (type==-1&&tag_name.equals("porn")||tag_name.equals("hot")){
				if (tag_name.equals("porn")&&tagConfidence>83){
					type=1;
					content = "图片类别：色情图片\n色情部位：";
				}else if (tag_name.equals("hot")&&tagConfidence>normal){
					type=1;
					content = "图片类别：性感图片\n性感部位：";
				}else {
					content = "图片类别：正常图片、";
				}
			}
			if (!tag_name.equals("normal")&&!tag_name.equals("hot")&&
					!tag_name.equals("porn")&&tagConfidence>0&&"0.1".compareTo(label_confd)<0){
				String label_str = getTagNameHuangString(tag_name);
				if (!StringUtil.isEmpty(label_str)){
					stringBuffer.append(label_str);
					stringBuffer.append("、");
				}
			}

		}
		if (type==-1){
			return content;
		}else {
			return content+stringBuffer.toString();
		}
	}

    public static String getVISION_OBJECTRString(List<ImgShiBieData> list,String[] strings) {

        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0 ;i<list.size();i++){

            int label_id = list.get(i).getLabel_id();
            String  label_confd= list.get(i).getLabel_confd();
            if ("0.1".compareTo(label_confd)<0){
				String label_str = TencentAIStringUtils2.getLabelString(label_id,strings);
				if (!StringUtil.isEmpty(label_str)){
					stringBuffer.append(label_str);
					stringBuffer.append("、");
				}
//				if (i<list.size()-1){
//
//				}
			}
        }
        return stringBuffer.toString();
    }


	//从小向大排序
	public static void bubbleSort(float array[]) {
		float t = 0;
		for (int i = 0; i < array.length - 1; i++)
			for (int j = 0; j < array.length - 1 - i; j++)
				if (array[j] > array[j + 1]) {
					t = array[j];
					array[j] = array[j + 1];
					array[j + 1] = t;
				}
	}

	public static  String phone34(String phone){
		int len = phone.length();
		String phoneNew ;
		if (len<=3){
			phoneNew  = phone;
		}else if (len<=7){
			phoneNew = phone.substring(0,3)+" "+phone.substring(3);
		}else if (len<=11){
			phoneNew = phone.substring(0,3)+" "+phone.substring(3,7)+" "+phone.substring(7);
		}else {
			phoneNew = phone.substring(0,3)+" "+phone.substring(3,7)+" "+phone.substring(7,11)+" "+phone.substring(11);
		}
		return  phoneNew;
	}
	public static String getTagNameHuangString(String tag_name) {
		if (!isEmpty(tag_name)){
			if (tag_name.equals("female-genital")) {
				tag_name = "女性阴部";
			}else if (tag_name.equals("female-breast")){
				tag_name = "女性胸部";
			}
			else if (tag_name.equals("male-genital")){
				tag_name = "男性阴部";
			}
			else if (tag_name.equals("pubes")){
				tag_name = "阴毛";
			}
			else if (tag_name.equals("anus")){
				tag_name = "肛门";
			}
			else if (tag_name.equals("sex")){
				tag_name = "性行为";
			}
			else if (tag_name.equals("normal_hot_porn")){
				tag_name = "";
			}
		}else {
			tag_name = "暂无";
		}
		return tag_name;
	}

	public static String getTagNameString(String tag_name) {
		if (!isEmpty(tag_name)){
			if (tag_name.equals("terrorists")) {
				tag_name = "恐怖分子";
			}else if (tag_name.equals("knife")){
				tag_name = "刀";
			}
			else if (tag_name.equals("guns")){
				tag_name = "枪支";
			}
			else if (tag_name.equals("blood")){
				tag_name = "血液";
			}
			else if (tag_name.equals("fire")){
				tag_name = "火";
			}
			else if (tag_name.equals("flag")){
				tag_name = "旗帜";
			}
			else if (tag_name.equals("crowd")){
				tag_name = "人群";
			}
			else if (tag_name.equals("normalarmy")){
				tag_name = "武装非恐怖分子";
			}
			else if (tag_name.equals("ship")){
				tag_name = "舰船";
			}
			else if (tag_name.equals("aircraft")){
				tag_name = "飞机";
			}
			else if (tag_name.equals("cannon")){
				tag_name = "火炮";
			}
			else if (tag_name.equals("armoredcar")){
				tag_name = "装甲车";
			}
			else if (tag_name.equals("other_weapon")){
				tag_name = "其他武器";
			}
		}else {
			tag_name = "暂无";
		}
		return tag_name;
	}

	public static String getWeatherString(String weathertype) {
		if (!isEmpty(weathertype)){
			if (weathertype.equals("CLEAR_DAY")) {
				weathertype = "晴天";
			}else if (weathertype.equals("CLEAR_NIGHT")){
				weathertype = "晴夜";
			}
			else if (weathertype.equals("PARTLY_CLOUDY_DAY")){
				weathertype = "多云";
			}
			else if (weathertype.equals("PARTLY_CLOUDY_NIGHT")){
				weathertype = "多云";
			}
			else if (weathertype.equals("CLOUDY")){
				weathertype = "阴";
			}
			else if (weathertype.equals("RAIN")){
				weathertype = "雨";
			}
			else if (weathertype.equals("SNOW")){
				weathertype = "雪";
			}
			else if (weathertype.equals("WIND")){
				weathertype = "风";
			}
			else if (weathertype.equals("HAZE")){
				weathertype = "雾霾沙尘";
			}
		}else {
			weathertype = "晴天";
		}
		return weathertype;
	}
	public static float  getMaxdString( float maxd2) {
		if (maxd2<=0.3f){
			maxd2 = 0.3f;
		}else if (maxd2<=0.6f){
			maxd2 = 0.6f;
		}else if (maxd2<=1.2f){
			maxd2 = 1.2f;
		}
		return maxd2;
	}
	public static List<WeiZhiData> getFaceShapList(List<FaceShapeData>  data) {

		List<WeiZhiData> list = new ArrayList<WeiZhiData>();

		for (FaceShapeData faceShapeData:data){
            List<WeiZhiData> face_profile = faceShapeData.getFace_profile();
            List<WeiZhiData> left_eye = faceShapeData.getLeft_eye();
            List<WeiZhiData> right_eye = faceShapeData.getRight_eye();
            List<WeiZhiData> left_eyebrow = faceShapeData.getLeft_eyebrow();
            List<WeiZhiData> right_eyebrow = faceShapeData.getRight_eyebrow();
            List<WeiZhiData> mouth = faceShapeData.getMouth();
            List<WeiZhiData> nose = faceShapeData.getNose();

            list.addAll(face_profile);
            list.addAll(left_eye);
            list.addAll(right_eye);
            list.addAll(left_eyebrow);
            list.addAll(left_eyebrow);
            list.addAll(right_eyebrow);
            list.addAll(mouth);
            list.addAll(nose);
        }
		return list;
	}
	public static String getAgeFaceFailFlag(int fail_flag) {

		String  failName = "无";
		switch ( fail_flag){
			case 0:
				failName = "无";
				break;
			case 1:
				failName = "第一张";
				break;
			case 2:
				failName = "第二张";
				break;
		}
		return failName;
	}

	public static String getAgeFaceImgFile(String imgs) {

		String[] strs = string2array1(imgs,",");
		String imgFile;
		if (strs!=null && strs.length>0){
			imgFile = strs[1];
		}else {
			imgFile = "";
		}
		return imgFile;
	}
	public static String[] getAgeFaceImgFiles(String imgs) {

		String[] strs = string2array1(imgs,",");

		return strs;
	}

	public static int getWeatherColorId(String weathertype) {

		int colorId = R.drawable.clear;
		if (!isEmpty(weathertype)){
			if (weathertype.equals("CLEAR_DAY")) {
				colorId = R.drawable.clear;
			}else if (weathertype.equals("CLEAR_NIGHT")){
				colorId = R.drawable.clear_night;
			}
			else if (weathertype.equals("PARTLY_CLOUDY_DAY")){
				colorId =  R.drawable.cloudy;
			}
			else if (weathertype.equals("PARTLY_CLOUDY_NIGHT")){
				colorId =  R.drawable.clear_night22;;
			}
			else if (weathertype.equals("CLOUDY")){
				colorId =  R.drawable.overcast;
			}
			else if (weathertype.equals("RAIN")){
				colorId =  R.drawable.rain;
			}
			else if (weathertype.equals("SNOW")){
				colorId =  R.drawable.snow;
			}
			else if (weathertype.equals("WIND")){
				//weathertype = "风";
				colorId =  R.drawable.wind;
			}
			else if (weathertype.equals("HAZE")){
				//weathertype = "雾霾沙尘";
				colorId =  R.drawable.haze;
			}
		}else {
			colorId = R.drawable.clear;
		}
		return colorId;
	}

	public static int getWeatherColorId2(String weathertype) {

		int colorId = R.drawable.clear2;
		if (!isEmpty(weathertype)){
			if (weathertype.equals("CLEAR_DAY")) {
				colorId = R.drawable.clear2;
			}else if (weathertype.equals("CLEAR_NIGHT")){
				colorId = R.drawable.clear_night12;
			}
			else if (weathertype.equals("PARTLY_CLOUDY_DAY")){
				colorId =  R.drawable.cloudy2;
			}
			else if (weathertype.equals("PARTLY_CLOUDY_NIGHT")){
				colorId =  R.drawable.clear_night2;;
			}
			else if (weathertype.equals("CLOUDY")){
				colorId =  R.drawable.overcast2;
			}
			else if (weathertype.equals("RAIN")){
				colorId =  R.drawable.rain2;
			}
			else if (weathertype.equals("SNOW")){
				colorId =  R.drawable.snow2;
			}
			else if (weathertype.equals("WIND")){
				//weathertype = "风";
				colorId =  R.drawable.wind2;
			}
			else if (weathertype.equals("HAZE")){
				//weathertype = "雾霾沙尘";
				colorId =  R.drawable.haze2;
			}
		}else {
			colorId = R.drawable.clear2;
		}
		return colorId;
	}
}
