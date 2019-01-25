package com.teplot.testapp.utils;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTimeUtil {

	public final static String FORMAT_YEAR = "yyyy";
	public final static String FORMAT_MONTH_DAY = "MM月dd日";

	public final static String FORMAT_DATE = "yyyy-MM-dd";
	public final static String FORMAT_TIME = "HH:mm";
	public final static String FORMAT_MONTH_DAY_TIME = "MM月dd日  HH時mm分";
	public final static String FORMAT_YEAR_MONTH_DAY_TIME = "yyyy年MM月dd日  HH時mm分";
	public final static String FORMAT_YEAR_MONTH_DAY_TIME2 = "yyyy.MM.dd  HH時mm分";
	public final static String FORMAT_YEAR_MONTH_DAY_TIME3 = "yyyy.MM.dd";
	public final static String FORMAT_YEAR_MONTH_DAY_TIME4 = "yyyy年MM月dd日";

	public final static String FORMAT_DATE_TIME1 = "yyyy-MM-dd HH:mm";
	public final static String FORMAT_DATE_TIME = "yyyy-MM-dd HH:mm:ss";

	private static final int YEAR = 365 * 24 * 60 * 60;// 年--秒
	private static final int MONTH = 30 * 24 * 60 * 60;// 月
	private static final int DAY = 24 * 60 * 60;// 天
	private static final int HOUR = 60 * 60;// 小时
	private static final int MINUTE = 60;// 分钟

	/**
	 * 根据时间戳获取描述性时间，如3分钟前，1天前
	 * 
	 * @param time 将时间格式为 FORMAT_DATE_TIME 改成
	 *            时间戳 单位为毫秒
	 * @return 时间字符串
	 */
	public static String getDescriptionTimeFromTimestamp(String time,String formate){
		long timestamp = stringToLong(time, formate);
		return getDescriptionTimeFromTimestamp(timestamp);
	}
	/**
	 * 根据时间戳获取描述性时间，如3分钟前，1天前
	 * 
	 * @param timestamp
	 *            时间戳 单位为毫秒
	 * @return 时间字符串
	 */
	public static String getDescriptionTimeFromTimestamp(long timestamp) {

		long currentTime = System.currentTimeMillis();
		long timeGap = (currentTime - timestamp) / 1000;// 与现在时间相差秒数
		//System.out.println("TimeUtil-----timeGap: " + timeGap);
		String timeStr = null;
		if (timeGap < MINUTE) {
			timeStr = "刚刚";
		} else if (timeGap < HOUR) {
			timeStr = timeGap / MINUTE + "分钟前";
		} else if (timeGap < DAY) {

			if (timeGap % HOUR / MINUTE > 30)
				timeStr = timeGap / HOUR + "个半小时前";
			else
				timeStr = timeGap / HOUR + "个小时前";
		} else if (timeGap < MONTH) {
			// 天数不按照满24小时算 现实生活是按照隔了多少天来算的
			try {
				String[] arr = getNowFormatTime(FORMAT_DATE_TIME).split(" ")[1]
						.split(":");
				timeGap = timeGap - Integer.valueOf(arr[0]) * 3600
						- Integer.valueOf(arr[1]) * 60
						- Integer.valueOf(arr[2]);
				timeStr = timeGap / DAY + 1 + "天前";
			} catch (Exception e) {
				timeStr = timeGap / DAY + "天前";
			}
		} else if (timeGap < YEAR) {

			if (timeGap % MONTH / DAY > 15)
				timeStr = timeGap / MONTH + "个半月前";
			else
				timeStr = timeGap / MONTH + "个月前";
		} else {
			timeStr = timeGap / YEAR + "年前";
		}
		return timeStr;
	}

	public static String getDescriptionTimeFromTimestamp2(String time,String formate){
		if (StringUtil.isEmpty(time)){
			return "";
		}else {
			long timestamp = stringToLong(time, formate);
			return getDescriptionTimeFromTimestamp2(timestamp);
		}
	}
	public static String getDescriptionTimeFromTimestamp2(long timestamp) {

		long currentTime = System.currentTimeMillis();
		long timeGap = (currentTime - timestamp) / 1000;// 与现在时间相差秒数
		//System.out.println("TimeUtil-----timeGap: " + timeGap);
		String timeStr = null;
		if (timeGap < MINUTE) {
			timeStr = "刚刚";
		} else if (timeGap < HOUR) {
			timeStr = timeGap / MINUTE + "分钟前";
		}else {
			timeStr = getChatTime2(timestamp);
			//timeStr = timeGap / YEAR + "年前";
		}
		return timeStr;
	}

	public static String getDescriptionTimeFromTimestamp3(String time,String formate){
		if (StringUtil.isEmpty(time)){
			return "";
		}else {
			long timestamp = stringToLong(time, formate);
			return getDescriptionTimeFromTimestamp3(timestamp);
		}
	}
	public static String getDescriptionTimeFromTimestamp3(long timestamp) {

		long currentTime = System.currentTimeMillis();
		long timeGap = (currentTime - timestamp) / 1000;// 与现在时间相差秒数
		//System.out.println("TimeUtil-----timeGap: " + timeGap);
		String timeStr = null;
		if (timeGap < MINUTE) {
			timeStr = "刚刚";
		} else if (timeGap < HOUR) {
			timeStr = timeGap / MINUTE + "分钟前";
		}else if (timeGap < DAY) {

			if (timeGap % HOUR / MINUTE > 30)
				timeStr = timeGap / HOUR + "个半小时前";
			else
				timeStr = timeGap / HOUR + "小时前";
		}else {
			timeStr = getChatTime2(timestamp);
			//timeStr = timeGap / YEAR + "年前";
		}
		return timeStr;
	}
	public static String getChatTime2(long timesamp) {
		long clearTime = timesamp;// *1000;
		String result = "";
		SimpleDateFormat sdf = new SimpleDateFormat("dd");
		Date today = new Date(System.currentTimeMillis());
		Date otherDay = new Date(clearTime);
		int temp = Integer.parseInt(sdf.format(today))
				- Integer.parseInt(sdf.format(otherDay));

		sdf = new SimpleDateFormat("yyyy");
		int tempYear = Integer.parseInt(sdf.format(today))
				- Integer.parseInt(sdf.format(otherDay));
		sdf = new SimpleDateFormat("MM");
		int tempMouth = Integer.parseInt(sdf.format(today))
				- Integer.parseInt(sdf.format(otherDay));
		//System.out.println("tempYear的值为===》"+tempYear);
		//tempYear = 0 ;为今年，其他不为今年
		if (tempYear != 0)
			return getTime2(clearTime);//直接显示年月日
		switch (temp) {
			case 0:
				if (tempMouth==0){
					result = "今天 " + getHourAndMin(clearTime);
				}else {
					result = getTime(clearTime);
				}
				break;
			case 1:
				if (tempMouth==0){
					result = "昨天 " + getHourAndMin(clearTime);
				}else {
					result = getTime(clearTime);
				}
				break;
			case 2:
				if (tempMouth==0){
					result = "前天 " + getHourAndMin(clearTime);
				}else {
					result = getTime(clearTime);
				}
				break;
			default:
				result = getTime(clearTime);
				break;
		}
//		switch (temp) {
//			case 0:
//				result = "今天 " + getHourAndMin(clearTime);
//				break;
//			case 1:
//				result = "昨天 " + getHourAndMin(clearTime);
//				break;
//			case 2:
//				result = "前天 " + getHourAndMin(clearTime);
//				break;
//			default:
//				result = getTime(clearTime);
//				break;
//		}
		return result;
	}
	/**
	 * 获取当前日期的指定格式的字符串
	 * 
	 * @param format
	 *            指定的日期时间格式，若为null或""则使用指定的格式"yyyy-MM-dd HH:mm"
	 * @return
	 */
	public static String getNowFormatTime(String format) {

		if (TextUtils.isEmpty(format))
			return new SimpleDateFormat(FORMAT_DATE_TIME).format(new Date());
		else
			return new SimpleDateFormat(format).format(new Date());

	}

	/**
	 * 获取当前日期的星期
	 * 1-星期一..7-星期天
	 * @return
	 */
	public static int getNowWeek() {
		return getWeek(System.currentTimeMillis());
	}

	/**
	 * 获取时间类型为long下的星期数
	 * @param l  long类型的时间
	 * @return   1-星期一..7-星期天
	 */
	public static int getWeek(long l) {
		l-=86400*1000;//减一天 出来的数据 
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(l);
		return cal.get(Calendar.DAY_OF_WEEK);
	}
	/**
	 * date类型转换为String类型
	 * @param data    Date类型的时间
	 * @param formatType   时间格式如：yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String dateToString(Date data, String formatType) {
		return new SimpleDateFormat(formatType).format(data);
	}
	/**
	 * 判断选择的时间是否大于当前时间
	 * @param chooseDate
	 * @param timenow
	 * @return
	 */
	public static boolean compareDateString(String chooseDate, String timenow) {

		long date  = stringToLong(chooseDate, "yyyy-MM-dd");
		long time  = stringToLong(timenow, "yyyy-MM-dd");
		//System.out.println("date======>"+date+"-=====time=====>"+time);
		if (date > time) {	
			return true;	
		}
		return false;	
	}
	public static boolean compareDateString(String chooseDate, String timenow,String formatType) {

		long date  = stringToLong(chooseDate, formatType);
		long time  = stringToLong(timenow, formatType);
		//System.out.println("date======>"+date+"-=====time=====>"+time);
		if (date > time) {	
			return true;	
		}
		return false;	
	}

	/**
	 * string类型转换为date类型
	 * @param strTime     string类型的时间
	 * @param formatType   时间类型格式：如yyyy-MM-dd HH:mm:ss
	 * 
	 * strTime的时间格式必须要与formatType的时间格式一致
	 * @return
	 */
	public static Date stringToDate(String strTime, String formatType) {
		SimpleDateFormat formatter = new SimpleDateFormat(formatType);

		Date date = null;
		try {
			date = formatter.parse(strTime);
		} catch (ParseException e) {
			e.printStackTrace();
			date = new Date();
		}
		return date;
	}

	/**
	 * long转换为Date类型
	 * @param currentTime  要转换的long类型的时间
	 * @param formatType   要转换的时间格式yyyy-MM-dd HH:mm:ss
	 * //yyyy年MM月dd日 HH时mm分ss秒
	 * @return
	 */
	public static Date longToDate(long currentTime, String formatType) {
		Date dateOld = new Date(currentTime);
		String sDateTime = dateToString(dateOld, formatType); 
		Date date = stringToDate(sDateTime, formatType); 
		return date;
	}
	/**
	 * date类型转换为long类型
	 * 
	 * @param date    date类型的时间
	 * @return
	 */
	public static long dateToLong(Date date) {
		return date.getTime();
	}

	/**
	 * long类型转换为String类型
	 * @param currentTime  要转换的long类型的时间
	 * @param formatType   时间格式yyyy-MM-dd HH:mm:ss或其他
	 * @return
	 */
	public static String longToString(long currentTime, String formatType) {
		String strTime = "";
		strTime = dateToString(new Date(currentTime), formatType); // date类型转成String
		return strTime;
	}

	/**
	 *  string类型转换为long类型
	 * @param strTime      String类型的时间
	 * @param formatType   时间格式yyyy-MM-dd HH:mm:ss或其他
	 * 
	 * strTime的时间格式和formatType的时间格式必须一致
	 * @return
	 */
	public static long stringToLong(String strTime, String formatType) {
		Date date = stringToDate(strTime, formatType); // String类型转成date类型
		if (date == null) {
			return 0;
		} else {
			long currentTime = dateToLong(date); // date类型转成long类型
			return currentTime;
		}
	}
	/**
	 * 将时间格式为formatType的strTime 转化为时间格式为formatType2的String
	 * @param strTime
	 * @param formatType
	 * @param formatType2
	 */
 
	public static String stringToFormatString(String strTime, String formatType, String formatType2){

		if (!StringUtil.isEmpty(strTime)){
			long time = stringToLong(strTime, formatType);
			if (time<0) {
				return null;
			}else {
				String strTime2 = longToString(time, formatType2);
				return strTime2;
			}
		}else {
			return "";
		}
	}
	/**
	 * 将long类型的时间转化为"MM-dd HH:mm"类型
	 * @param time 毫秒时间
	 * @return
	 */
	public static String getTime(long time) {
		SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm");
		return format.format(new Date(time));
	}
	public static String getTime2(long time) {
		SimpleDateFormat format = new SimpleDateFormat(FORMAT_DATE);
		return format.format(new Date(time));
	}
	/**
	 * 将long类型的时间转化为"HH:mm"类型
	 * @param time 毫秒时间
	 * @return
	 */
	public static String getHourAndMin(long time) {
		SimpleDateFormat format = new SimpleDateFormat("HH:mm");
		return format.format(new Date(time));
	}
	// 取得现在年份
	public static int getNowYearDate( String formatType) {
		int year = 2015;
		try {
			SimpleDateFormat yearDate = new SimpleDateFormat(formatType);
			year = Integer.parseInt(yearDate.format(new Date()));
		} catch (Exception e) {
		}

		return year;
	}
	/**
	 * 获取聊天时间
	 * 
	 * @Title: getChatTime
	 * @Description: TODO
	 * @param @param timesamp
	 * @param @return
	 * @return String
	 * @throws
	 */
	public static String getChatTime(long timesamp) {
		long clearTime = timesamp;// *1000;
		String result = "";
		SimpleDateFormat sdf = new SimpleDateFormat("dd");
		Date today = new Date(System.currentTimeMillis());
		Date otherDay = new Date(clearTime);
		int temp = Integer.parseInt(sdf.format(today))
				- Integer.parseInt(sdf.format(otherDay));

		sdf = new SimpleDateFormat("MM");
		int tempMouth = Integer.parseInt(sdf.format(today))
				- Integer.parseInt(sdf.format(otherDay));

		if (tempMouth != 0)
			return getTime(clearTime);

		switch (temp) {
		case 0:
			result = "今天 " + getHourAndMin(clearTime);
			break;
		case 1:
			result = "昨天 " + getHourAndMin(clearTime);
			break;
		case 2:
			result = "前天 " + getHourAndMin(clearTime);
			break;
		default:
			result = getTime(clearTime);
			break;
		}
		return result;
	}
	// 取得长型时间
	public static String getNowDay() {
		return new SimpleDateFormat("dd").format(new Date());
	}

	/**
	 * 获取友好时间 月-日 时:分
	 */
	public static String getFirendTime(String time,String formatType) {
		long clearTime = stringToLong(time, formatType);// *1000;
		return getFirendTime(clearTime);
	}
	/**
	 * 获取友好时间 月-日 时:分
	 */
	public static String getFirendTime(long timesamp) {
		long clearTime = timesamp;// *1000;
		String result = "";

		Date today = longToDate(System.currentTimeMillis(),"yyyy-MM-dd");
		Date otherDay = longToDate(clearTime,"yyyy-MM-dd");;

		long timeMs = today.getTime()-otherDay.getTime();
		int temp = (int) (timeMs/(24*60*60*1000));

		switch (temp) {
		case 0:
			result = "今天 " + getHourAndMin(clearTime);
			break;
		case 1:
			result = "昨天 " + getHourAndMin(clearTime);
			break;
		case 2:
			result = "前天 " + getHourAndMin(clearTime);
			break;
		default:
			result = getTime(clearTime);
			break;
		}
		return result;
	}
	/**
	 * 
	 * @param timesamp
	 * @return
	 */
	public static String longFormarStr(long timesamp) {
		if (timesamp < 0)
			timesamp = -timesamp;
		String time = "";
		timesamp = timesamp / 1000;
		long d = timesamp / DAY;
		if (d > 0)
			time += d + "天";
		long h = timesamp % DAY / HOUR;
		if (h > 0) {
			time += h + "小时";
		}
		long m = timesamp % HOUR / MINUTE;
		if (m > 0) {
			time += m + "分钟";
		}
		return time;
	}

	public static int getLastDayOfMonth(int year, int month) {
		if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8
				|| month == 10 || month == 12) {
			return 31;
		}
		if (month == 4 || month == 6 || month == 9 || month == 11) {
			return 30;
		}
		if (month == 2) {
			if (isLeapYear(year)) {
				return 29;
			} else {
				return 28;
			}
		}
		return 0;
	}

	/**
	 * 判断是否为闰年
	 * @param year
	 * @return
	 */
	public static boolean isLeapYear(int year) {
		return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
	}

	/**
	 * 取得今天星期
	 * @return
	 */
	public static String getWeekOfDateCN() {
		int week = getNowWeek();//今天的星期
		String[] weekDaysName = { "星期一", "星期二", "星期三", "星期四", "星期五",
				"星期六","星期日"};
		return weekDaysName[(week-1)];
	}
	public static String getWeekOfDateCN(String date,String formatType) {//日期 2018-21-21
		long time = stringToLong(date ,formatType);
		int week = getWeek(time);//今天的星期
		String[] weekDaysName = { "星期一", "星期二", "星期三", "星期四", "星期五",
				"星期六","星期日"};
		return weekDaysName[(week-1)];
	}
	public static String getWeekOfDateCN2(String date,String formatType) {//日期 2018-21-21
		long time = stringToLong(date ,formatType);
		int week = getWeek(time);//今天的星期

		String[] weekDaysName = { "周一", "周二", "周三", "周四", "周五",
				"周六","周日"};
		String weekStr;
		if (getNowWeek()==week){
			weekStr = "今天";
		}else if ((getNowWeek()-1)==week){
			weekStr = "昨天";
		}else  if ((getNowWeek()+1)==week){
			weekStr = "明天";
		}else {
			weekStr = weekDaysName[(week-1)];
		}
		return weekStr;
	}




	/**
	 * 获取最近通话时间描述
	 * 
	 * @param timesamp
	 * @return
	 */
	public static String getCallLogTime(long timesamp) {
		long clearTime = timesamp;// *1000;
		String result = "";
		SimpleDateFormat sdf = new SimpleDateFormat("dd");
		Date today = new Date(System.currentTimeMillis());
		Date otherDay = new Date(clearTime);

		int temp = Integer.parseInt(sdf.format(today))
				- Integer.parseInt(sdf.format(otherDay));
		sdf = new SimpleDateFormat("MM");

		int tempMouth = Integer.parseInt(sdf.format(today))
				- Integer.parseInt(sdf.format(otherDay));
		if (tempMouth != 0)
			return longToString(clearTime, "MM/dd");

		switch (temp) {
		case 0:
			result = getHourAndMin(clearTime);
			break;
		case 1:
			result = "昨天 ";
			break;
		case 2:
			result = "前天 ";
			break;

		default:
			result = longToString(clearTime, "MM/dd");
			break;
		}
		return result;
	}
}