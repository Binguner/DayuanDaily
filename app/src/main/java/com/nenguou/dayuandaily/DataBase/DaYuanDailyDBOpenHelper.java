package com.nenguou.dayuandaily.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by binguner on 2018/2/15.
 */

public class DaYuanDailyDBOpenHelper extends SQLiteOpenHelper{

    /**
     * YearCollege
     * 存储学院信息
     * {"id": 2,"college": "体育学院"},{"id": 3,"college": "信息与计算机学院"},
     */
    public static final String CREATE_YEARCOLLEGE = "create table YearCollege(" +
            "id integer primary key autoincrement," +
            "college_id integer," +
            "college_name text)";

    /**
     * Major
     * 存储某个学院下的所有专业名称，根据 college_id 找到所有专业
     * {"id": 3,"collegeId": 3,"major": "信息安全"},{"id": 4,"collegeId": 3,"major": "测控技术与仪器"},
     */
    public static final String CREATE_MAJOR = "create table Major(" +
            "id integer primary key autoincrement," +
            "major_id integer," +
            "major_name text," +
            "college_id integer)";

    /**
     * ClassName
     * 存储该专业，该年级下的所有班级
     * "data": ["软件1609","软件1611","软件1614","软件1632","软件1631","软件1630",
     */
    public static final String CREATE_CLASSNAME = "create table ClassName(" +
            "id integer primary key autoincrement," +
            "year integer," +
            "className text," +
            "major_name, text)";

    /**
     * Schedule
     * 存储课程表
     * week_num: 周几
     * class_num: 这一天的第几节
     * classtable_name: 课程名字
     */
    public static final String CREATE_SCHEDULE = "create table Schedule(" +
            "id integer primary key autoincrement," +
            "schedule_id integer," +
            "term text," +
            "year integer," +
            "class_name text," +
            "college_name text," +
            "major_name text," +
            "week_num integer," +
            "course_num integer," +
            "course_name text," +
            "course_place text," +
            "course_detial_place text," +
            "teacher_name text," +
            "course_time text)";

    public static final String CREATE_YEARLIST = "create table YearList(" +
            "id integer primary key autoincrement," +
            "year_id integer," +
            "year integer)";
    public DaYuanDailyDBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_YEARCOLLEGE);
        sqLiteDatabase.execSQL(CREATE_MAJOR);
        sqLiteDatabase.execSQL(CREATE_CLASSNAME);
        sqLiteDatabase.execSQL(CREATE_SCHEDULE);
        sqLiteDatabase.execSQL(CREATE_YEARLIST);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
