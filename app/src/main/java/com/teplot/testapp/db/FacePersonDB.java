package com.teplot.testapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.teplot.testapp.been.details.FacePersonData;
import com.teplot.testapp.been.details.LoginInfo;

import java.util.ArrayList;
import java.util.List;

public class FacePersonDB extends DBHelper{

    public FacePersonDB(Context context) {
        super(context);
    }
    /**
     * 更新或创建用户信息
     */
    public boolean updateFacePerson(FacePersonData uInfo) {

        if (uInfo == null)
            return false;
        Cursor c = null;
        try {
            SQLiteDatabase db = getReadableDatabase();
            c =  db.query(DBInfo.Table.FACEPERSON_INFO_TB,
                    null, "person_id = ?", new String[]{uInfo.getPerson_id()},
                    null, null, null);
            int numRows = c.getCount();
            ContentValues values = new ContentValues();
            if (numRows > 0) {
                values.put("group_ids", uInfo.getGroup_ids());
                values.put("person_id", uInfo.getPerson_id());
                values.put("image_list", uInfo.getImage_list());

                values.put("person_name", uInfo.getPerson_name());
                values.put("tag", uInfo.getTag());

                SQLiteDatabase db2 = getWritableDatabase();
                db2.update(DBInfo.Table.FACEPERSON_INFO_TB, values,
                        "person_id = ?", new String[]{uInfo.getPerson_id()});
            } else {
                values.put("group_ids", uInfo.getGroup_ids());
                values.put("person_id", uInfo.getPerson_id());
                values.put("image_list", uInfo.getImage_list());

                values.put("person_name", uInfo.getPerson_name());
                values.put("tag", uInfo.getTag());
                SQLiteDatabase db2 = getWritableDatabase();
                db2.insert(DBInfo.Table.FACEPERSON_INFO_TB, null, values);
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
    public FacePersonData getFacePerson(String person_id) {
        FacePersonData userinfo = new FacePersonData();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.query(DBInfo.Table.FACEPERSON_INFO_TB,
                    null, "person_id = ?", new String[]{person_id},
                    null, null, null);

            int numRows = cursor.getCount();
            cursor.moveToFirst();
            if (numRows > 0) {
                userinfo.setGroup_ids(cursor.getInt(0));

                userinfo.setPerson_id(cursor.getString(1));
                userinfo.setImage_list(cursor.getString(2));
                userinfo.setPerson_name(cursor.getString(3));
                userinfo.setTag(cursor.getString(4));

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
            db.delete(DBInfo.Table.FACEPERSON_INFO_TB, null, null);
        } catch (Exception e) {

        }
    }

    public void delTable(String person_id) {
        try {
            SQLiteDatabase db = getWritableDatabase();
            db.delete(DBInfo.Table.FACEPERSON_INFO_TB, "person_id = ?", new String[]{person_id});
        } catch (Exception e) {

        }
    }
}
