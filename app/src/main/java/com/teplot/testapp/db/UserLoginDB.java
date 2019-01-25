package com.teplot.testapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.teplot.testapp.been.details.LoginInfo;

public class UserLoginDB extends DBHelper{

    public UserLoginDB(Context context) {
        super(context);
    }
    /**
     * 更新或创建用户信息
     */
    public boolean updateUser(LoginInfo uInfo) {

        if (uInfo == null)
            return false;
        Cursor c = null;
        try {
            SQLiteDatabase db = getReadableDatabase();
            c =  db.query(DBInfo.Table.LOGIN_INFO_TB,
                    null, "id = ?", new String[]{uInfo.getId()+""},
                    null, null, null);
            int numRows = c.getCount();
            ContentValues values = new ContentValues();
            if (numRows > 0) {
                values.put("id", uInfo.getId());
                values.put("account", uInfo.getAccount());
                values.put("userName", uInfo.getUserName());

                values.put("telephone", uInfo.getTelephone());
                values.put("email", uInfo.getEmail());
                values.put("icon", uInfo.getIcon());

                values.put("status", uInfo.getStatus());
                values.put("password", uInfo.getPassword());
                values.put("registerTime", uInfo.getRegisterTime());

                values.put("sex", uInfo.getSex());
                values.put("sign", uInfo.getSign());
                values.put("weiXin", uInfo.getWeiXin());
                values.put("address", uInfo.getAddress());
                values.put("realNameStatus", uInfo.getRealNameStatus());

                values.put("weather_info", uInfo.getWeather_info());
                values.put("new_message", uInfo.getNew_message());
                values.put("disturb_mode", uInfo.getDisturb_mode());
                values.put("disturb_time_start", uInfo.getDisturb_time_start());
                values.put("disturb_time_end", uInfo.getDisturb_time_end());

                values.put("areaId", uInfo.getAreaId());
                values.put("areaName", uInfo.getAreaName());

                SQLiteDatabase db2 = getWritableDatabase();
                db2.update(DBInfo.Table.LOGIN_INFO_TB, values,
                        "id = ?", new String[]{uInfo.getId()+""});
            } else {
                values.put("id", uInfo.getId());
                values.put("account", uInfo.getAccount());
                values.put("userName", uInfo.getUserName());

                values.put("telephone", uInfo.getTelephone());
                values.put("email", uInfo.getEmail());
                values.put("icon", uInfo.getIcon());

                values.put("status", uInfo.getStatus());
                values.put("password", uInfo.getPassword());
                values.put("registerTime", uInfo.getRegisterTime());

                values.put("sex", uInfo.getSex());
                values.put("sign", uInfo.getSign());
                values.put("weiXin", uInfo.getWeiXin());
                values.put("address", uInfo.getAddress());
                values.put("realNameStatus", uInfo.getRealNameStatus());

                values.put("weather_info", uInfo.getWeather_info());
                values.put("new_message", uInfo.getNew_message());
                values.put("disturb_mode", uInfo.getDisturb_mode());
                values.put("disturb_time_start", uInfo.getDisturb_time_start());
                values.put("disturb_time_end", uInfo.getDisturb_time_end());

                values.put("areaId", uInfo.getAreaId());
                values.put("areaName", uInfo.getAreaName());
                SQLiteDatabase db2 = getWritableDatabase();
                db2.insert(DBInfo.Table.LOGIN_INFO_TB, null, values);
            }
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            if (c != null)
                c.close();
        }
        return true;
    }
    /**
     * 获取数据库中登陆用户信息
     */
    public LoginInfo getUser() {
        LoginInfo userinfo = new LoginInfo();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.query(DBInfo.Table.LOGIN_INFO_TB,
                    null, null, null,
                    null, null, null);

            int numRows = cursor.getCount();
            cursor.moveToFirst();
            if (numRows > 0) {
                userinfo.setId(cursor.getInt(0));

                userinfo.setAccount(cursor.getString(1));
                userinfo.setUserName(cursor.getString(2));
                userinfo.setTelephone(cursor.getString(3));
                userinfo.setEmail(cursor.getString(4));

                userinfo.setIcon(cursor.getString(5));
                userinfo.setStatus(cursor.getString(6));
                userinfo.setPassword(cursor.getString(7));
                userinfo.setRegisterTime(cursor.getString(8));

                userinfo.setSex(cursor.getString(9));
                userinfo.setSign(cursor.getString(10));
                userinfo.setWeiXin(cursor.getString(11));
                userinfo.setAddress(cursor.getString(12));
                userinfo.setRealNameStatus(cursor.getString(13));

                userinfo.setWeather_info(cursor.getString(14));
                userinfo.setNew_message(cursor.getString(15));
                userinfo.setDisturb_mode(cursor.getString(16));
                userinfo.setDisturb_time_start(cursor.getString(17));
                userinfo.setDisturb_time_end(cursor.getString(18));

                userinfo.setAreaId(cursor.getString(19));
                userinfo.setAreaName(cursor.getString(20));
            }
        } catch (Exception e) {
            e.printStackTrace();
           // System.out.println("获取数据库用户信息出错");
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return userinfo;
    }
    /**
     * 删除表
     */
    public void delTable() {
        try {
            SQLiteDatabase db = getWritableDatabase();
            db.delete(DBInfo.Table.LOGIN_INFO_TB, null, null);
        } catch (Exception e) {

        }
    }
}
