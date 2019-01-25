package com.teplot.testapp.db;

/**
 * 数据库信息类，主要是保存一些数据库的版本，名字，及数据库表的创建语句和表的信息等，通过这个类记录，方便操作
 *
 *
 */
public class DBInfo {
    /**
     * 数据库信息
     *
     *
     */
    public static class DB {

        // 数据库名称
        public static final String DB_NAME = "apptest.db";
        // 数据库的版本号
        public static final int DB_VERSION = 1;
    }
    /**
     * 数据库表的信息
     *
     *
     */
    public static class Table {

        public static final String LOGIN_INFO_TB = "user";
        public static final String LOGIN_INFO_CREATE = "CREATE TABLE IF NOT EXISTS "
                + LOGIN_INFO_TB
                + "("
                + "id" + " integer primary key,"

                + "account" + " text,"
                + "userName" + " text,"
                + "telephone" + " text,"
                + "email" + " text,"
                + "icon" + " text,"
                + "status" + " text,"

                + "password" + " text,"
                + "registerTime" + " text,"

                + "sex" + " text,"
                + "sign" + " text,"
                + "weiXin" + " text,"
                + "address" + " text,"
                + "realNameStatus" + " text,"

                + "weather_info" + " text,"
                + "new_message" + " text,"
                + "disturb_mode" + " text,"
                + "disturb_time_start" + " text,"
                + "disturb_time_end" + " text,"

                + "areaId" + " text,"
                + "areaName" + " text)";

        public static final String LOGIN_INFO_DROP = "DROP TABLE"
                + LOGIN_INFO_TB;



        public static final String FACEPERSON_INFO_TB = "faceperson";
        public static final String FACEPERSON_INFO_CREATE = "CREATE TABLE IF NOT EXISTS "
                + FACEPERSON_INFO_TB
                + "("
                + "group_ids" + " integer primary key,"

                + "person_id" + " text,"
                + "image_list" + " text,"
                + "person_name" + " text,"
                + "tag" + " text)";

        public static final String FACEPERSON_INFO_DROP = "DROP TABLE"
                + FACEPERSON_INFO_TB;

    }
}
