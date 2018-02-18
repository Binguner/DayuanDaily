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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by binguner on 2018/2/15.
 */

public class DayuanDailyDatabase {

    public static final String DB_NAME = "Dayuan_database";
    public static final int DB_VERSION = 1;

    private static DayuanDailyDatabase dayuanDailyDatabase;
    private SQLiteDatabase sqLiteDatabase;

    private DayuanDailyDatabase(Context context) {
        DaYuanDailyDBOpenHelper openHelper = new DaYuanDailyDBOpenHelper(context, DB_NAME, null, DB_VERSION);
        sqLiteDatabase = openHelper.getWritableDatabase();
    }

    public synchronized static DayuanDailyDatabase getInstance(Context context) {
        if (dayuanDailyDatabase == null) {
            dayuanDailyDatabase = new DayuanDailyDatabase(context);
        }
        return dayuanDailyDatabase;
    }

    public void saveYearCollege(YearCollege yearCollege) {
        if (yearCollege != null) {
            ContentValues contentValues = new ContentValues();
            List<YearCollege.DataBean.CollegesBean> collegesBeanList = new ArrayList<>();
            collegesBeanList = yearCollege.getData().getColleges();
            for (YearCollege.DataBean.CollegesBean collegesBean : collegesBeanList) {
                //Log.d("DaYuanTag",collegesBean.getCollege());
                contentValues.put("college_id", collegesBean.getId());
                //Log.d("DaYuanTag",collegesBean.getId()+"");
                contentValues.put("college_name", collegesBean.getCollege());
                sqLiteDatabase.insert("YearCollege", null, contentValues);
                contentValues.clear();
            }
        }
    }

    public void saveMajor(Major major) {
        if (major != null) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("major_id", major.getData().get(0).getId());
            contentValues.put("major_name", major.getData().get(0).getMajor());
            contentValues.put("college_id", major.getData().get(0).getCollegeId());
            sqLiteDatabase.insert("Major", null, contentValues);
            contentValues.clear();
        }
    }

    public void saveClassName(ClassName className, String major_name, int year) {
        if (className != null) {
            List<String> data = className.getData();
            ContentValues contentValues = new ContentValues();
            for (String s : data) {
                //Log.d("DaYuanTag",s+"");
                contentValues.put("className", s);
                contentValues.put("major_name", major_name);
                contentValues.put("year", year);
                sqLiteDatabase.insert("ClassName", null, contentValues);
                contentValues.clear();
            }
        }
    }

    public void saveClass(Class mClass) {

        if (mClass != null) {
            ContentValues contentValues = new ContentValues();


            // 将每一天的课程分出来
            String regex = "(\"\\d\":\\{.*?\\})+";
            String date = mClass.getData().getData();
            //Log.d("WocaoNiMaTag",date);
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(date);

            // 将每一节课分开
            String regex4Class = "(\"(\\d)\":\"(.+?\\d+)\\((.+?),(.+?),(.+?),(.+?)\\)\")+";
            // 获取周几
            String regex4Week = "(\"(\\d)\":\\{)+";
            Pattern pattern1 = Pattern.compile(regex4Class);
            Matcher matcher1 = null;
            Pattern pattern2 = Pattern.compile(regex4Week);
            Matcher matcher2 = null;
            while (matcher.find()){
                //Log.d("WocaoNiMaTag",matcher.groupCount()+"");
                for (int i = 1 ;i <= matcher.groupCount(); i++){
                    Log.d("WocaoNiMaTag2",matcher.group(i));
                    matcher1 = pattern1.matcher(matcher.group(i));
                    matcher2 = pattern2.matcher(matcher.group(i));
                    //Log.d("WocaoNiMaTag1","第"+i+"天");
                    //Log.d("WocaoNiMaTag1", "groupCount1 = "+matcher.groupCount()+"个");
                    Log.d("WocaoNiMaTag1", "groupCount2 = "+matcher1.groupCount()+"个");
                    while (matcher2.find()) {
                        Log.d("WocaoNiMaTag122", "周 " + matcher2.group(2) + "");
                        int numberWeek = Integer.parseInt(matcher2.group(2))+1;
                        while (matcher1.find()) {
                            //Log.d("WocaoNiMaTag122",matcher2.groupCount()+" 个");
                            int numberClass = Integer.parseInt(matcher1.group(2)) + 1;
                            Log.d("WocaoNiMaTag123","周 "+numberWeek+"  第 "+ numberClass +" 节课  "+"课程名字: "+matcher1.group(3)+"  教学楼:"+matcher1.group(4) +
                            "  教室：" + matcher1.group(5) + "  教师姓名：" + matcher1.group(6) + "教学时间: " + matcher1.group(7));
                            contentValues.put("schedule_id",mClass.getData().getId());
                            contentValues.put("term",mClass.getData().getTerm());
                            contentValues.put("year",mClass.getData().getYear());
                            contentValues.put("class_name",mClass.getData().getName());//班级名称 eg 软件 1632
                            contentValues.put("college_name",mClass.getData().getCollege());//学院名称 eg 软件学院
                            contentValues.put("major_name",mClass.getData().getMajor());//专业名称 eg 软件工程
                            contentValues.put("week_num",numberWeek);
                            contentValues.put("course_num",numberClass);
                            contentValues.put("course_name",matcher1.group(3));
                            contentValues.put("course_place",matcher1.group(4));
                            contentValues.put("course_detial_place",matcher1.group(5));
                            contentValues.put("teacher_name",matcher1.group(6));
                            contentValues.put("course_time",matcher1.group(7));
                            sqLiteDatabase.insert("Schedule",null,contentValues);
                            contentValues.clear();
                        }
                    }
                    //Log.d("WocaoNiMaTag1","=========================");
                }
            }

        }
    }
}
