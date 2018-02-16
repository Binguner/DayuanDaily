package com.nenguou.dayuandaily.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.nenguou.dayuandaily.Model.Class;
import com.nenguou.dayuandaily.Model.ClassName;
import com.nenguou.dayuandaily.Model.Major;
import com.nenguou.dayuandaily.Model.YearCollege;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by binguner on 2018/2/15.
 */

public class DayuanDailyDatabase {

    public static final String DB_NAME = "Dayuan_database";
    public static final int DB_VERSION = 1;

    private static DayuanDailyDatabase dayuanDailyDatabase;
    private SQLiteDatabase sqLiteDatabase;

    private DayuanDailyDatabase(Context context){
        DaYuanDailyDBOpenHelper openHelper = new DaYuanDailyDBOpenHelper(context,DB_NAME,null,DB_VERSION);
        sqLiteDatabase = openHelper.getWritableDatabase();
    }

    public synchronized static DayuanDailyDatabase getInstance(Context context){
        if(dayuanDailyDatabase == null){
            dayuanDailyDatabase = new DayuanDailyDatabase(context);
        }
        return dayuanDailyDatabase;
    }

    public void saveYearCollege(YearCollege yearCollege){
        if( yearCollege!=null ){
            ContentValues contentValues = new ContentValues();
            List<YearCollege.DataBean.CollegesBean> collegesBeanList = new ArrayList<>();
            collegesBeanList = yearCollege.getData().getColleges();
            for(YearCollege.DataBean.CollegesBean collegesBean: collegesBeanList){
                //Log.d("DaYuanTag",collegesBean.getCollege());
                contentValues.put("college_id",collegesBean.getId());
                //Log.d("DaYuanTag",collegesBean.getId()+"");
                contentValues.put("college_name",collegesBean.getCollege());
                sqLiteDatabase.insert("YearCollege",null,contentValues);
                contentValues.clear();
            }
        }
    }

    public void saveMajor(Major major){
        if( major!=null ){
            ContentValues contentValues = new ContentValues();
            contentValues.put("major_id",major.getData().get(0).getId());
            contentValues.put("major_name",major.getData().get(0).getMajor());
            contentValues.put("college_id",major.getData().get(0).getCollegeId());
            sqLiteDatabase.insert("Major",null,contentValues);
            contentValues.clear();
        }
    }

    public void saveClassName(ClassName className, String major_name,int year){
        if(className!=null){
            List<String> data = className.getData();
            ContentValues contentValues = new ContentValues();
            for(String s: data){
                //Log.d("DaYuanTag",s+"");
                contentValues.put("className",s);
                contentValues.put("major_name",major_name);
                contentValues.put("year",year);
                sqLiteDatabase.insert("ClassName",null,contentValues);
                contentValues.clear();
            }
        }
    }

    public void saveClass(Class mClass){

        if(mClass!=null){
//            ContentValues contentValues = new ContentValues();
//            contentValues.put("schedule_id",mClass.getData().getId());
//            contentValues.put("term",mClass.getData().getTerm());
//            contentValues.put("year",mClass.getData().getYear());
//            contentValues.put("class_name",mClass.getData().getName());//班级名称 eg 软件 1632
//            contentValues.put("college_name",mClass.getData().getCollege());//学院名称 eg 软件学院
//            contentValues.put("major_name",mClass.getData().getMajor());//专业名称 eg 软件工程
            String weekRegex = "\"\\d\":*.*?}";
            String[] weekDay = mClass.getData().getData().split(weekRegex);
            Log.d("DaYuanTag","Here");

            for(String s:weekDay){
                Log.d("DaYuanTag2",s);
            }
        }
        Log.d("DaYuanTag","OutHere");

    }
}
