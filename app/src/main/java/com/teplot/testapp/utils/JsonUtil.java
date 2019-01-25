package com.teplot.testapp.utils;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * Gson类库的封装工具类，专门负责解析json数据</br>
 * 内部实现了Gson对象的单例
 * @author xl
 * @version 1.0
 * @since 2012-9-18
 */
public class JsonUtil {

	public static Gson gson = null;
	private static String TAG = "JsonUtil";


	private JsonUtil() {

	}

	static {
		if (gson == null) {
			GsonBuilder builder = new GsonBuilder();   

			builder.excludeFieldsWithoutExposeAnnotation();
			builder.serializeNulls();

			//去除String为null的字段
			//builder.registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory());
			//builder.registerTypeAdapter(String.class, new StringConverter());
			
			gson = builder.create(); 
		}
	}

	public static class NullStringToEmptyAdapterFactory<T> implements TypeAdapterFactory {
	    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {

	        Class<T> rawType = (Class<T>) type.getRawType();
	        if (rawType != String.class) {
	            return null;
	        }
	        return (TypeAdapter<T>) new StringAdapter();
	    }
	}

	public static class StringAdapter extends TypeAdapter<String> {
	    public String read(JsonReader reader) throws IOException {
	        if (reader.peek() == JsonToken.NULL) {
	        	
	            reader.nextNull();
	            return "";
	        }
	        return reader.nextString();
	    }
	    public void write(JsonWriter writer, String value) throws IOException {
	        if (value == null) {
	            writer.nullValue();
	            return;
	        }
	        writer.value(value);
	    }
	}
	/**
	 * 将对象转换成json格式
	 * 
	 * @param ts
	 * @return
	 */
	public static String objectToJson(Object ts) {
		String jsonStr = "";
		if (gson != null) {
			jsonStr = gson.toJson(ts);
		}
		return jsonStr;
	}

	/**
	 * 将对象转换成json格式(并自定义日期格式)
	 * 
	 * @param ts
	 * @return
	 */
	public static String objectToJsonDateSerializer(Object ts,
			final String dateformat) {
		String jsonStr = null;
		gson = new GsonBuilder()
				.registerTypeHierarchyAdapter(Date.class,
						new JsonSerializer<Date>() {
							public JsonElement serialize(Date src,
									Type typeOfSrc,
									JsonSerializationContext context) {
								SimpleDateFormat format = new SimpleDateFormat(
										dateformat);
								return new JsonPrimitive(format.format(src));
							}
						}).setDateFormat(dateformat).create();
		if (gson != null) {
			jsonStr = gson.toJson(ts);
		}
		return jsonStr;
	}

	/**
	 * 将json格式转换成list对象
	 * 
	 * @param jsonStr
	 * @return
	 */
	public static List<?> jsonToList(String jsonStr) {
		List<?> objList = null;
		if (gson != null) {
			Type type = new TypeToken<List<?>>() {
			}.getType();
			objList = gson.fromJson(jsonStr, type);
		}
		return objList;
	}
	
	/**
	 * 将json格式转换成list对象，并准确指定类型
	 * @param jsonStr
	 * @param type
	 * @return
	 */
	public static List<?> jsonToList(String jsonStr, Type type) {
		List<?> objList = null;
		if (gson != null) {
			objList = gson.fromJson(jsonStr, type);
		}
		return objList;
	}

	/**
	 * 将json格式转换成map对象
	 * 
	 * @param jsonStr
	 * @return
	 */
	public static Map<?, ?> jsonToMap(String jsonStr) {
		Map<?, ?> objMap = null;
		if (gson != null) {
			Type type = new TypeToken<Map<?, ?>>() {
			}.getType();
			objMap = gson.fromJson(jsonStr, type);
		}
		return objMap;
	}

	/**
	 * 将json转换成bean对象
	 * 
	 * @param jsonStr
	 * @return
	 */
	public static Object jsonToBean(String jsonStr, Class<?> cl) {
		Object obj = null;
		if (gson != null) {
			try {
				obj = gson.fromJson(jsonStr, cl);
			} catch (JsonSyntaxException e) {
				e.printStackTrace();
				//Logger.e(TAG, e.getMessage());
				Log.e(TAG, e.getMessage());
			}
		}
		return obj;
	}

	/**
	 * 将json转换成bean对象
	 * 
	 * @param jsonStr
	 * @param cl
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T jsonToBeanDateSerializer(String jsonStr, Class<T> cl,
			final String pattern) {
		Object obj = null;
		gson = new GsonBuilder()
				.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
					public Date deserialize(JsonElement json, Type typeOfT,
							JsonDeserializationContext context)
							throws JsonParseException {
						SimpleDateFormat format = new SimpleDateFormat(pattern);
						String dateStr = json.getAsString();
						try {
							return format.parse(dateStr);
						} catch (ParseException e) {
							e.printStackTrace();
							//Logger.e(TAG, e.getMessage());
							Log.e(TAG, e.getMessage());
						}
						return null;
					}
				}).setDateFormat(pattern).create();
		if (gson != null) {
			obj = gson.fromJson(jsonStr, cl);
		}
		return (T) obj;
	}

	/**
	 * 根据
	 * 
	 * @param jsonStr
	 * @param key
	 * @return
	 */
	public static Object getJsonValue(String jsonStr, String key) {
		Object rulsObj = null;
		Map<?, ?> rulsMap = jsonToMap(jsonStr);
		if (rulsMap != null && rulsMap.size() > 0) {
			rulsObj = rulsMap.get(key);
		}
		return rulsObj;
	}
	
	public static class StringConverter implements JsonSerializer<String>, 
	JsonDeserializer<String> { 
		public JsonElement serialize(String src, Type typeOfSrc, 
				JsonSerializationContext context) { 
			if ( src == null ) { 
				return new JsonPrimitive(""); 
			} else { 
				return new JsonPrimitive(src.toString());
			}
		} 
		public String deserialize(JsonElement json, Type typeOfT, 
				JsonDeserializationContext context) 
						throws JsonParseException { 
			return json.getAsJsonPrimitive().getAsString(); 
		} 
	}
}