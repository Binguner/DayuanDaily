package com.nenguou.dayuandaily.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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

    public static final int TYPE_GET_SUB_NAME = 1;
    public static final int TYPE_GET_SUB_PLACE = 2;
    public static final int TYPE_GET_SUB_TEACHER = 3;
    public static final int TYPE_GET_SUB_TIME = 4;

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
            for(Major.DataBean dataBean: major.getData()){
                try {
                    if(!isExitThisMajor(dataBean.getId())){
                        contentValues.put("major_id", dataBean.getId());
                        contentValues.put("major_name", dataBean.getMajor());
                        contentValues.put("college_id", dataBean.getCollegeId());
                        sqLiteDatabase.insert("Major", null, contentValues);
                        contentValues.clear();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void saveClassName(ClassName className, String major_name, int year) {
        if (className != null) {
            List<String> data = className.getData();
            ContentValues contentValues = new ContentValues();
            try {
                if(!isExitThisClassName(year,major_name)) {
                    for (String s : data) {
                        //Log.d("DaYuanTag",s+"");
                        contentValues.put("className", s);
                        contentValues.put("major_name", major_name);
                        contentValues.put("year", year);
                        sqLiteDatabase.insert("ClassName", null, contentValues);
                        contentValues.clear();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
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
                            //Log.d("WocaoNiMaTag123","周 "+numberWeek+"  第 "+ numberClass +" 节课  "+"课程名字: "+matcher1.group(3)+"  教学楼:"+matcher1.group(4) +
                            //"  教室：" + matcher1.group(5) + "  教师姓名：" + matcher1.group(6) + "教学时间: " + matcher1.group(7));
                            try {
                                if(!isExitThisSchedule(mClass.getData().getId())) {
                                    contentValues.put("schedule_id", mClass.getData().getId());
                                    contentValues.put("term", mClass.getData().getTerm());
                                    contentValues.put("year", mClass.getData().getYear());
                                    contentValues.put("class_name", mClass.getData().getName());//班级名称 eg 软件 1632
                                    contentValues.put("college_name", mClass.getData().getCollege());//学院名称 eg 软件学院
                                    contentValues.put("major_name", mClass.getData().getMajor());//专业名称 eg 软件工程
                                    contentValues.put("week_num", numberWeek);
                                    contentValues.put("course_num", numberClass);
                                    contentValues.put("course_name", matcher1.group(3));
                                    contentValues.put("course_place", matcher1.group(4));
                                    contentValues.put("course_detial_place", matcher1.group(5));
                                    contentValues.put("teacher_name", matcher1.group(6));
                                    contentValues.put("course_time", matcher1.group(7));
                                    sqLiteDatabase.insert("Schedule", null, contentValues);
                                    contentValues.clear();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    //Log.d("WocaoNiMaTag1","=========================");
                }
            }

        }
    }

    /**
     * 返回所有学院的数据
     */
    public List<YearCollege.DataBean.CollegesBean> loadYearCollege(){
        List<YearCollege.DataBean.CollegesBean> list = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query("YearCollege",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                YearCollege.DataBean.CollegesBean collegesBean = new YearCollege.DataBean.CollegesBean();
                collegesBean.setId(cursor.getInt(cursor.getColumnIndex("college_id")));
                collegesBean.setCollege(cursor.getString(cursor.getColumnIndex("college_name")));
                list.add(collegesBean);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    /**
     * 判断表是否存在这个学院
     * @return
     */
    public boolean isExitThisCollege(){
        return false;
    }

    /**
     * @param collegeId 根据传入的 collegeId 获得该学院的所有专业
     * @return 该学院的专业集合
     */
    public List<Major.DataBean> loadMajor(int collegeId){
        List<Major.DataBean> list = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query("Major",null,"college_id = ?",new String[]{String.valueOf(collegeId)},null,null,null);
        if(cursor.moveToFirst()){
            do{
                Major.DataBean dataBean = new Major.DataBean();
                dataBean.setCollegeId(cursor.getInt(cursor.getColumnIndex("college_id")));
                dataBean.setId(cursor.getInt(cursor.getColumnIndex("major_id")));
                dataBean.setMajor(cursor.getString(cursor.getColumnIndex("major_name")));
                list.add(dataBean);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    /**
     * @param id 根据传入的 专业 ID 判断表中是否存在这节课
     * @return 存在返回 true，不存在返回 false；
     */
    public boolean isExitThisMajor(int id) throws Exception {
        Cursor cursor = sqLiteDatabase.query("Major",null,"major_id = ?",new String[]{String.valueOf(id)},null,null,null);
        if(cursor.moveToFirst()){
            int mID = cursor.getInt(cursor.getColumnIndex("major_id"));
            if(mID == id){
                cursor.close();
                return true;
            }
        }
        cursor.close();
        return false;
    }

    /**
     * @param year 专业的学年
     * @param major_name 专业名称
     * @return 返回该专业该学年的所有班级
     */
    public List<String> loadClassName(int year, String major_name){
        List<String> list = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query("ClassName",null,"year = ? and major_name like ?",new String[]{String.valueOf(year),major_name},null,null,null);
        if(cursor.moveToFirst()){
            do{
                String s = cursor.getString(cursor.getColumnIndex("className"));
                list.add(s);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    /**
     * @param year 这些班级的入学年日期
     * @param major_name 这些班级的专业名字
     * @return 如果表中存在这一年这一专业的数据，返回 true
     * @throws Exception 返回「不知道什么」异常
     */
    public boolean isExitThisClassName(int year,String major_name) throws Exception{
        Cursor cursor = sqLiteDatabase.query("ClassName",null,"year = ? and major_name like ?",new String[]{String.valueOf(year),major_name},null,null,null);
        if(cursor.moveToFirst()){
            do{
                String mString = cursor.getString(cursor.getColumnIndex("major_name"));
                int mInt = cursor.getInt(cursor.getColumnIndex("year"));
                if(year == mInt && major_name.equals(mString)){
                    cursor.close();
                    return true;
                }
            }while (cursor.moveToNext());
        }
        cursor.close();
        return false;
    }

    /**
     * @param Type TYPE_GET_SUB_NAME = 1; TYPE_GET_SUB_PLACE = 2;TYPE_GET_SUB_TEACHER = 3;TYPE_GET_SUB_TIME = 4;
     * @param schedule_id 课表的唯一 id
     * @param week_num 课表的周几
     * @param course_num 课表的周几的第几节
     * @return 根据 Type 不同，分别返回课程名字，上课地点，上课老师，上课的周数
     */
    public String loadSchedules(int Type,int schedule_id,int week_num,int course_num){
        //List<String> list = new ArrayList<>();
        String s = null;
        Cursor cursor = sqLiteDatabase.query("Schedule",null,"schedule_id = ? and week_num = ? and course_num = ?",
                        new String[]{String.valueOf(schedule_id),String.valueOf(week_num),String.valueOf(course_num)},null,null,null);
        if(cursor.moveToFirst()){
            do{
                switch (Type){
                    case DayuanDailyDatabase.TYPE_GET_SUB_NAME:
                        s = cursor.getString(cursor.getColumnIndex("course_name"));
                        break;
                    case DayuanDailyDatabase.TYPE_GET_SUB_PLACE:
                        s = cursor.getString(cursor.getColumnIndex("course_place"))+" "+cursor.getString(cursor.getColumnIndex("course_detial_place"));
                        break;
                    case DayuanDailyDatabase.TYPE_GET_SUB_TEACHER:
                        s = cursor.getString(cursor.getColumnIndex("teacher_name"));
                        break;
                    case DayuanDailyDatabase.TYPE_GET_SUB_TIME:
                        s = cursor.getString(cursor.getColumnIndex("course_time"));
                        break;
                }
            }while (cursor.moveToNext());
        }
        return s;

    }

    /**
     * @param schedule_id 传入课表的唯一 id
     * @return 如果数据库中存在这个课表，返回为 true
     * @throws Exception 「不知道什么」异常c
     */
    public boolean isExitThisSchedule(int schedule_id) throws Exception{
        Cursor cursor = sqLiteDatabase.query("Schedule",null,"schedule_id = ?",new String[]{String.valueOf(schedule_id)},null,null,null);
        if(cursor.moveToFirst()){
            do {
                int id = cursor.getInt(cursor.getColumnIndex("schedule_id"));
                if(id == schedule_id){
                    cursor.close();
                    return true;
                }
            }while (cursor.moveToNext());
        }
        cursor.close();
        return false;
    }
}
