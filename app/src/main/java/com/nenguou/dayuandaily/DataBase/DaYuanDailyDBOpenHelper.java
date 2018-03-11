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
            "schedule_id integer," +    // 9514
            "term text," +  // 2017-2018-2-1
            "year integer," +   // 2016
            "class_name text," +   // 软件1632
            "college_name text," +  // 软件学院
            "major_name text," +    // 软件工程
            "link text,"+   // null

            "class_term text,"+  //2017-2018学年春(两学期)
            "week_num integer," +   //  0
            "course_start integer," + // 0
            "course_length integer,"+   // 2
            "course_start_week integer,"+
            "course_name text," +   // 大学英语(四)
            "course_name_suffix text,"+  // _08
            "teacher_name text," +  // 王茹
            "course_rawWeek text,"+     // 1-14周上
            "course_weeks text,"+   // [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14]
            "course_build text," +     // 行勉楼
            "course_campus text," +     // 明向校区
            "course_room text)";  // A404
            //"course_time text)";

    public static final String CREATE_YEARLIST = "create table YearList(" +
            "id integer primary key autoincrement," +
            "year_id integer," +
            "year integer)";

    /**
     * studentNumber 学号
     */
    public static final String CREATE_GRADES = "create table Grades(" +
            "id integer primary key autoincrement," +
            "studentNumber text," +
            "name text," +
            "classNumber text," +
            "classOrder text," +
            "className text," +
            "englishName text," +
            "credit integer," +
            "classInfo text," +
            "grade real)";
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
        sqLiteDatabase.execSQL(CREATE_GRADES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
