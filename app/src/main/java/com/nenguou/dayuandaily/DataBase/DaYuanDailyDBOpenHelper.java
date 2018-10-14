package com.nenguou.dayuandaily.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by binguner on 2018/2/15.
 */

public class DaYuanDailyDBOpenHelper extends SQLiteOpenHelper{

    /**
     * YearCollege2
     * 存储学院信息
     * {"id": 2,"college": "体育学院"},{"id": 3,"college": "信息与计算机学院"},
     */
    public static final String CREATE_COLLEGELISTS = "create table College(" +
            "id integer primary key autoincrement," +
            "college_id text," +
            "college_name text," +
            "college_value text)";

    /**
     * Major
     * 存储某个学院下的所有专业名称，根据 college_id 找到所有专业
     * {"id": 3,"collegeId": 3,"major": "信息安全"},{"id": 4,"collegeId": 3,"major": "测控技术与仪器"},
     */
    public static final String CREATE_MAJOR = "create table Major(" +
            "id integer primary key autoincrement," +
            //"major_id integer," +
            "college_id text," +
            "college_name text," +
            "major_name text)";    // 机械工程学院

    /**
     * ClassName
     * 存储该专业，该年级下的所有班级
     * "data": ["软件1609","软件1611","软件1614","软件1632","软件1631","软件1630",
     */
    public static final String CREATE_CLASSNAME = "create table ClassName(" +
            "id integer primary key autoincrement," +
            "college_name text," +
            "college_id text,"+
            "major_name text,"+
            "class_number text,"+
            "class_name text)";

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

    /**
     *  保存 学期 名称 和值
     *  {
     *      "n": "2018-2019学年秋(两学期)",
     *      "v": "2018-2019-1-1"
     *  },
     */
    public static final String CREATE_TERMSLIST = "create table Terms(" +
            "id integer primary key autoincrement," +
            "terms_name text," +
            "terms_value text)";

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

    public static final String CREATE_RANKS = "create table Ranks(" +
            "id integer primary key autoincrement," +
            "username text," + // 819985138@qq.com
            "xh text," + // 学号
            "xm text," +   // 姓名
            "bjh text," + // 班级名称 软件1632
            "bm text," + // 班级名称 软件1632
            "zyh text," +   // 不知道是什么 1600101
            "xsh text," +   // 不知道是什么 16
            "njdm text," +   // 不知道是什么 2016
            "zym text," + // 专业名称 软件工程
            "xsm text," +   // 学院名称 软件学院
            "yqzxf text," +  // 要求总学分 186.50
            "yxzzsjxf text," +  // 已修自主实践学分
            "zxf text," +   // 已修课程学分 74
            "yxzxf text," + //  74
            "cbjgxf text," +    // 6
            "sbjgxf text," +    // 0
            "pjxfjd text," +    // 3.11
            "gpabjpm text," +   // 9
            "gpazypm text," +   // 267
            "pjcj text," +  //80.52
            "pjcjbjpm text," +  //8
            "pjcjzypm text," +  //  242
            "jqxfcj text," +    // 79.18
            "jqbjpm text," +    // 8
            "jqzypm text," +    // 216
            "tsjqxfcj text," +  // 79.18
            "tjsj text," +  // 2018-04-01 01:00:10
            "bjrs text," +  //33
            "zyrs text," +  // 1006
            "dlrs text," +
            "gpadlpm text)"; //3446
    public DaYuanDailyDBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_COLLEGELISTS);
        sqLiteDatabase.execSQL(CREATE_MAJOR);
        sqLiteDatabase.execSQL(CREATE_CLASSNAME);
        sqLiteDatabase.execSQL(CREATE_SCHEDULE);
        sqLiteDatabase.execSQL(CREATE_TERMSLIST);
        sqLiteDatabase.execSQL(CREATE_GRADES);
        sqLiteDatabase.execSQL(CREATE_RANKS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
