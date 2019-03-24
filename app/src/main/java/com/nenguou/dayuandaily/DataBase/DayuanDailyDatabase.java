package com.nenguou.dayuandaily.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.nenguou.dayuandaily.Model.Class;
import com.nenguou.dayuandaily.Model.ClassBean;
import com.nenguou.dayuandaily.Model.ClassBeanData;
import com.nenguou.dayuandaily.Model.ClassDetial;
import com.nenguou.dayuandaily.Model.ClassName;
import com.nenguou.dayuandaily.Model.Classe;
import com.nenguou.dayuandaily.Model.College;
import com.nenguou.dayuandaily.Model.DataYearCollege;
import com.nenguou.dayuandaily.Model.Detail;
import com.nenguou.dayuandaily.Model.Grades;
import com.nenguou.dayuandaily.Model.Major;
import com.nenguou.dayuandaily.Model.MajorAndClassedData;
import com.nenguou.dayuandaily.Model.MajorAndClasses;
import com.nenguou.dayuandaily.Model.RankModelDetial;
import com.nenguou.dayuandaily.Model.RestClass;
import com.nenguou.dayuandaily.Model.Term;
import com.nenguou.dayuandaily.Model.YearCollege;
import com.nenguou.dayuandaily.Model.YearCollege2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by binguner on 2018/2/15.
 */

public class DayuanDailyDatabase {

    public static final int TYPE_GET_SUB_NAME = 1;
    public static final int TYPE_GET_SUB_PLACE = 2;
    public static final int TYPE_GET_SUB_TEACHER = 3;
    public static final int TYPE_GET_SUB_RAWWEEK = 4;
    public static final int TYPE_GET_SUB_WEEKS = 5;
    public static final int TYPE_GET_SUB_LENGTH = 6;

    public static final String DB_NAME = "Dayuan_database";
    public static final int DB_VERSION = 1;

    private List<String> teacherNameList = new ArrayList<>(); // 用来保存评教结果

    private static final String DbTag = "DayuannTag";
    private static DayuanDailyDatabase dayuanDailyDatabase;
    private SQLiteDatabase sqLiteDatabase;
    Context context;


    private DayuanDailyDatabase(Context context) {
        DaYuanDailyDBOpenHelper openHelper = new DaYuanDailyDBOpenHelper(context, DB_NAME, null, DB_VERSION);
        sqLiteDatabase = openHelper.getWritableDatabase();
        this.context = context;
    }

    public synchronized static DayuanDailyDatabase getInstance(Context context) {
        if (dayuanDailyDatabase == null) {
            dayuanDailyDatabase = new DayuanDailyDatabase(context);
        }
        return dayuanDailyDatabase;
    }

    public void dropAndCreateTableGrades(){
        sqLiteDatabase.execSQL("drop table if exists Grades" );
        sqLiteDatabase.execSQL(DaYuanDailyDBOpenHelper.CREATE_GRADES);

    }

    /*public void saveYearCollege(YearCollege2 yearCollege2) {
        if (yearCollege2 != null) {
            int lastYear = yearCollege2.getData().getYears().get(yearCollege2.getData().getYears().size()-1).getYear();
            if(!isExitThisCollege(lastYear)) {
                ContentValues contentValues = new ContentValues();
                List<YearCollege2.DataBean.CollegesBean> collegesBeanList = new ArrayList<>();
                collegesBeanList = yearCollege2.getData().getColleges();
                for (YearCollege2.DataBean.CollegesBean collegesBean : collegesBeanList) {
                    //Log.d("DaYuanTag",collegesBean.getCollege());
                    contentValues.put("college_id", collegesBean.getId());
                    //Log.d("DaYuanTag",collegesBean.getId()+"");
                    contentValues.put("college_name", collegesBean.getCollege());
                    sqLiteDatabase.insert("YearCollege2", null, contentValues);
                    contentValues.clear();
                }
                List<YearCollege2.DataBean.YearsBean> yearsBeans = yearCollege2.getData().getYears();
                for (YearCollege2.DataBean.YearsBean yearsBean : yearsBeans) {
                    contentValues.put("year_id", yearsBean.getId());
                    contentValues.put("year", yearsBean.getYear());
                    sqLiteDatabase.insert("YearList", null, contentValues);
                    contentValues.clear();
                }
            }
        }
    }*/

    /**
     * 保存 学期 和 所有专业 名称
     * @param yearCollege
     *             {
     *                 "n": "2018-2019学年秋(两学期)",
     *                 "v": "2018-2019-1-1"
     *             },
     *
     *              {
     *                 "id": "01",
     *                 "name": "机械工程学院",
     *                 "majors": null
     *             },
     */
    public void saveYearCollege(YearCollege yearCollege) {
        if(null != yearCollege){
            ContentValues contentValues = new ContentValues();
            for (Term term: yearCollege.getData().getTerms()){
                contentValues.put("terms_name",term.getN());
                contentValues.put("terms_value",term.getV());
                sqLiteDatabase.insert("Terms",null,contentValues);
            }

            contentValues.clear();

            for (College college:yearCollege.getData().getColleges()){
                contentValues.put("college_id",college.getId());
                contentValues.put("college_name",college.getName());
                contentValues.put("college_value","null");
                sqLiteDatabase.insert("College",null,contentValues);
            }

            contentValues.clear();

        }
    }

    /**
     * 删除表 Terms
     * 删除所有 学期 和 专业名称 的数据
     */
    public void dropYearCollege(){
        sqLiteDatabase.execSQL("drop table if exists Terms" );
        sqLiteDatabase.execSQL("drop table if exists College" );
        sqLiteDatabase.execSQL(DaYuanDailyDBOpenHelper.CREATE_TERMSLIST);
        sqLiteDatabase.execSQL(DaYuanDailyDBOpenHelper.CREATE_COLLEGELISTS);
    }

    /**
     * @return 返回所有 学期 数据
     */
    public List<Term> loadTerm(){
        List<Term> termList = new ArrayList<>();
        Term term = null;
        Cursor cursor = sqLiteDatabase.query("Terms",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do {
                term = new Term(
                        cursor.getString(cursor.getColumnIndex("terms_name")),
                        cursor.getString(cursor.getColumnIndex("terms_value"))
                );
                termList.add(term);
            }while (cursor.moveToNext());
        }
        return termList;
    }

    /**
     * @return 返回所有 学院 数据
     */
    public List<College> loadCollege(){
        List<College> collegeList = new ArrayList<>();
        College college = null;
        Cursor cursor = sqLiteDatabase.query("College",null,null,null,null,null,null);
        if (cursor.moveToFirst()){
            do {
                college = new College(
                        cursor.getString(cursor.getColumnIndex("college_id")),
                        cursor.getString(cursor.getColumnIndex("college_name")),
                        cursor.getString(cursor.getColumnIndex("college_value"))
                );
                collegeList.add(college);
            }while (cursor.moveToNext());
        }
        return collegeList;
    }

    /***
     * 将专业 和班级保存到数据库中
     * @param majorAndClasses   MajorAndClasses
     */
    public void saveMajorAndClass(MajorAndClasses majorAndClasses) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("User_YearCollege",Context.MODE_PRIVATE);
        if (majorAndClasses != null) {
            ContentValues contentValues = new ContentValues();
            // 保存专业
            for(Major major: majorAndClasses.getData().getMajors()){
                /*try {
                    if(!isExitThisMajor(dataBean.getId())){
                        contentValues.put("major_id", dataBean.getId());
                        contentValues.put("major_name", dataBean.getMajor());
                        contentValues.put("college_id", dataBean.getCollegeId());
                        sqLiteDatabase.insert("Major", null, contentValues);
                        contentValues.clear();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }*/
                contentValues.put("college_id",sharedPreferences.getString("college_id","null"));
               // Log.d("qweqweqwe",sharedPreferences.getString("college_name","null"));
                contentValues.put("college_name",sharedPreferences.getString("college_name","null"));
                contentValues.put("major_name",major.getName());
                sqLiteDatabase.insert("Major",null,contentValues);
                contentValues.clear();

                for (Classe classe:major.getClasses()){
                    contentValues.put("college_name",sharedPreferences.getString("college_name","null"));
                    contentValues.put("college_id",sharedPreferences.getString("college_id","null"));
                    contentValues.put("major_name",major.getName());
                    contentValues.put("class_number",classe.getNumber());
                    contentValues.put("class_name",classe.getName());
                    sqLiteDatabase.insert("ClassName",null,contentValues);
                    contentValues.clear();
                }

            }
        }
    }

    /**
     * 删除 专业 和专业内所有班级
     */
    public void dropMajorAndClasses(){
        sqLiteDatabase.execSQL("drop table if exists Major" );
        sqLiteDatabase.execSQL("drop table if exists ClassName" );
        sqLiteDatabase.execSQL(DaYuanDailyDBOpenHelper.CREATE_MAJOR);
        sqLiteDatabase.execSQL(DaYuanDailyDBOpenHelper.CREATE_CLASSNAME);
    }

    /**
     * @return 返回专业下所有专业名称
     */
    public List<String> loadMajors(){
        List<String> majorList = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query("Major",null,null,null,null,null,null);
        if (cursor.moveToFirst()){
            do {
                majorList.add(cursor.getString(cursor.getColumnIndex("major_name")));
            }while (cursor.moveToNext());
        }
        return majorList;
    }

    public boolean isMajorListEmpty(){
        Cursor cursor = sqLiteDatabase.query("Major",null,null,null,null,null,null);
        if (cursor.moveToFirst()){
            do {
                if (!cursor.getString(cursor.getColumnIndex("college_name")).equals("")){
                    return false;
                }
            }while (cursor.moveToNext());
        }
        return true;
    }

    public void saveClassName(ClassName className, String major_name, int year) {
        if (className != null) {
            List<ClassName.DataBean> data = className.getData();
            ContentValues contentValues = new ContentValues();
            try {
                if(!isExitThisClassName(year,major_name)) {
                    for (ClassName.DataBean s : data) {
                        //Log.d("DaYuanTag",s+"");
                        contentValues.put("className", s.getName());
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

    public void saveClass(ClassBean mClass){
        SharedPreferences sharedPreferences = context.getSharedPreferences("User_YearCollege",Context.MODE_PRIVATE);
        if(mClass != null ){
            ContentValues contentValues = new ContentValues();
            try {
                if(!isExitThisSchedule(sharedPreferences.getString("className","null"),sharedPreferences.getString("term_name","null"))){
                    //for(ClassDetial.ScheduleBean scheduleBean: classDetial.getSchedule()){
                    //for( ClassBeanData classBeanData : mClass.getData()){
                    Log.d("ertef", "Class size : "+mClass.getData().size());
                    for( int i = 0; i < mClass.getData().size(); i++){
                        Log.d("ertef","Class name is :" + mClass.getData().get(i).getName());
                        for(Detail detail :mClass.getData().get(i).getDetails()){

                            contentValues.put("schedule_id",0);
                            contentValues.put("term",sharedPreferences.getString("term_name","null"));
                            contentValues.put("year",sharedPreferences.getString("term_value","null"));
                            contentValues.put("class_name",sharedPreferences.getString("className","null"));
                            contentValues.put("college_name",sharedPreferences.getString("college_name","null"));
                            contentValues.put("major_name",sharedPreferences.getString("major_name","null"));
                            //contentValues.put("course_start_week",scheduleBean.getWeeks().toString().split(",")[0].toString().split("\\[")[1]);
                            //contentValues.put("course_start_week",mClass.getData().get(i).getWeeks().get(0).toString());
                            contentValues.put("course_start_week",mClass.getData().get(i).getRawWeek().split("\\d+")[0]);
                            Log.d("ertef",mClass.getData().get(i).getRawWeek().toString());
                            //try
                            Log.d("ertef",mClass.getData().get(i).getName()+" "+ mClass.getData().get(i).getRawWeek().split("\\d+")[0]+"");
                           // }catch (Exception e){
                            //    e.toString();
                            //}
                            //contentValues.put("link",mClass.getData().getLink().toString());
                            //contentValues.put("class_term",classDetial.getTerm());
                            contentValues.put("class_term",sharedPreferences.getString("term_name","null"));
                            //contentValues.put("week_num",detailsBean.getDay()+1);
                            contentValues.put("week_num",detail.getDay()+1);
                            //contentValues.put("course_start",detailsBean.getStart()+1);
                            contentValues.put("course_start",detail.getStart()+1);
                            ///contentValues.put("course_length",detailsBean.getLength());
                            contentValues.put("course_length",detail.getLength());
                            //contentValues.put("course_name",scheduleBean.getName());
                            contentValues.put("course_name",mClass.getData().get(i).getName());
                            //contentValues.put("course_name_suffix",scheduleBean.getName_suffix());
                            contentValues.put("course_name_suffix",mClass.getData().get(i).getSuffix());
                            //contentValues.put("teacher_name",scheduleBean.getTeacher());
                            contentValues.put("teacher_name",mClass.getData().get(i).getTeacher());
                            //contentValues.put("course_rawWeek",scheduleBean.getRawWeek());
                            contentValues.put("course_rawWeek",mClass.getData().get(i).getRawWeek());
                            //contentValues.put("course_weeks",scheduleBean.getWeeks().toString());
                            contentValues.put("course_weeks",mClass.getData().get(i).getWeeks().toString());
                            //contentValues.put("course_build",detailsBean.getBuild());
                            contentValues.put("course_build",detail.getBuild());
                            //contentValues.put("course_campus",detailsBean.getCampus());
                            contentValues.put("course_campus",detail.getCampus());
                            // contentValues.put("course_room",detailsBean.getRoom());
                            contentValues.put("course_room",detail.getRoom());
                            sqLiteDatabase.insert("Schedule",null,contentValues);
                            contentValues.clear();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public void saveClasss(Class mClass) {

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
            try {
                if (!isExitThisSchedule("sad","s")) {
                    while (matcher.find()) {
                        //Log.d("WocaoNiMaTag",matcher.groupCount()+"");
                        for (int i = 1; i <= matcher.groupCount(); i++) {
                            Log.d("WocaoNiMaTag2", matcher.group(i));
                            matcher1 = pattern1.matcher(matcher.group(i));
                            matcher2 = pattern2.matcher(matcher.group(i));
                            //Log.d("WocaoNiMaTag1","第"+i+"天");
                            //Log.d("WocaoNiMaTag1", "groupCount1 = "+matcher.groupCount()+"个");
                            Log.d("WocaoNiMaTag1", "groupCount2 = " + matcher1.groupCount() + "个");
                            while (matcher2.find()) {
                                Log.d("WocaoNiMaTag122", "周 " + matcher2.group(2) + "");
                                int numberWeek = Integer.parseInt(matcher2.group(2)) + 1;
                                while (matcher1.find()) {
                                    //Log.d("WocaoNiMaTag122",matcher2.groupCount()+" 个");
                                    int numberClass = Integer.parseInt(matcher1.group(2)) + 1;
                                    Log.d("WocaoNiMaTag123", "周 " + numberWeek + "  第 " + numberClass + " 节课  " + "课程名字: " + matcher1.group(3) + "  教学楼:" + matcher1.group(4) +
                                            "  教室：" + matcher1.group(5) + "  教师姓名：" + matcher1.group(6) + "教学时间: " + matcher1.group(7));
//                            try {
//                                if(!isExitThisSchedule(mClass.getData().getId())) {
                                    contentValues.put("schedule_id", mClass.getData().getId());
                                    Log.d("WocaoNiMaTag123", "schedule_id: " + mClass.getData().getId() + "");
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
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
                            }
                        }
                        //Log.d("WocaoNiMaTag1","=========================");
                    }
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    public void saveGrades(Grades grades){
        if(grades != null){
            //sqLiteDatabase.execSQL("drop table if exists Grades");
            List<Grades.DataBean> dataBeanList = grades.getData();
            SharedPreferences sharedPreferences = context.getSharedPreferences("User_grades",Context.MODE_PRIVATE);
            String studentNumber = sharedPreferences.getString("username","");
            ContentValues contentValues = new ContentValues();
            for(Grades.DataBean dataBean:dataBeanList){
                List<Grades.DataBean.GradesBean> gradesBeans = dataBean.getGrades();
                for(Grades.DataBean.GradesBean gradesBean : gradesBeans){
                    contentValues.put("studentNumber",studentNumber);
                    contentValues.put("name",dataBean.getName());
                    contentValues.put("classNumber",gradesBean.getClassNumber());
                    contentValues.put("classOrder",gradesBean.getClassOrder());
                    contentValues.put("className",gradesBean.getClassName());
                    contentValues.put("englishName",gradesBean.getEnglishName());
                    contentValues.put("credit",gradesBean.getCredit());
                    contentValues.put("classInfo",gradesBean.getClassInfo());
                    contentValues.put("grade",gradesBean.getGrade());
                    sqLiteDatabase.insert("Grades",null,contentValues);
                    contentValues.clear();
                }
            }
        }
    }

    public boolean isExitThieGrade(String studentNumber){
        Cursor cursor = sqLiteDatabase.query("Grades",null,"studentNumber like ?",new String[]{String.valueOf(studentNumber)},null,null,null);
        if(cursor.moveToFirst()){
            do {
                if(studentNumber.equals( cursor.getString(cursor.getColumnIndex("studentNumber")))){
                    return true;
                }
            }while (cursor.moveToNext());
        }
        return false;
    }

    /**
     * @param studentNumber 学生学号
     * @return 如果数据库中存在这个学生的 Rank，返回 true，不存在，返回 false；
     */
    public boolean isExitThisRank(String studentNumber){
        Cursor cursor = sqLiteDatabase.query("Ranks",null," username = ?",new String[]{String.valueOf(studentNumber)},null,null,null);
        if(cursor.moveToFirst()){
            do{
                if(studentNumber.equals(cursor.getString(cursor.getColumnIndex("username")))){
                    return true;
                }
            }while (cursor.moveToNext());
        }
        return false;
    }

    /**
     * @param oldValue 学生旧 gpa
     * @param newValue 学生新 gpa
     * @return 若新 gpa == 旧 gpa 返回 false
     */
    public boolean isThisRankChanged(int oldValue, int newValue){
        if(oldValue != newValue){
            return true;
        }
        return false;
    }

    /**
     * @param studentNumber 传入 学生学号
     * @return 返回该学生在数据库中的 old gpa
     */
    private int getOldRanks(int studentNumber){
        Cursor cursor = sqLiteDatabase.query("Ranks",null,null,new String[]{String.valueOf(studentNumber)},null,null,null);
        if(cursor.moveToFirst()){
            do{
                int oldRanks = cursor.getInt(cursor.getColumnIndex("pjxfjd"));
                if(oldRanks != 0){
                    return oldRanks;
                }
           }while (cursor.moveToNext());
        }
        return 0;
    }

    /**
     * 保存查询到的 Ranks
     * 如果数据库中先前保存过了，则删除原数据，增加新数据
     * @param rankModelDetial
     */
    public void saveRank(RankModelDetial rankModelDetial){
        SharedPreferences sharedPreferences = context.getSharedPreferences("User_grades",Context.MODE_PRIVATE);
        if(isExitThisRank(rankModelDetial.getXh())){  // 判断表中原先是否有该数据
           // Log.d("mlgb","原来有这个数据");
            //Log.d("mlgb","原数据 == " + getOldRanks(Integer.parseInt(rankModelDetial.getXh())));
            //Log.d("mlgb","新数据 == " + Integer.parseInt(rankModelDetial.getPjxfjd()));

            //if(!isThisRankChanged(getOldRanks(Integer.parseInt(rankModelDetial.getXh())),Integer.parseInt(rankModelDetial.getPjxfjd()))){    // 判断原先的数据 与 新数据是否相同
              //  Log.d("mlgb","原数据 == 新数据");
                //Log.d("mlgb","原数据 == " + getOldRanks(Integer.parseInt(rankModelDetial.getXh())));
                //Log.d("mlgb","新数据 == " + Integer.parseInt(rankModelDetial.getPjxfjd()));
                //return;
            //}
            //Log.d("mlgb","删了这个数据");
            // 此时表中有数据 且 两次数据不一样 , 删除原数据
            sqLiteDatabase.delete("Ranks","xh = ?",new String[]{String.valueOf(Integer.parseInt(rankModelDetial.getXh()))});
        }
        if(null != rankModelDetial){

            ContentValues contentValues = new ContentValues();
            contentValues.put("username",sharedPreferences.getString("username",""));
            contentValues.put("xh",rankModelDetial.getXh());
            contentValues.put("xm",rankModelDetial.getXm());
            contentValues.put("bjh",rankModelDetial.getBjh());
            contentValues.put("bm",rankModelDetial.getBm());
            contentValues.put("zyh",rankModelDetial.getZyh());
            contentValues.put("zym",rankModelDetial.getZym());
            contentValues.put("xsh",rankModelDetial.getXsh());
            contentValues.put("xsm",rankModelDetial.getXsm());
            contentValues.put("njdm",rankModelDetial.getNjdm());
            contentValues.put("yqzxf",rankModelDetial.getYqzxf());
            contentValues.put("yxzzsjxf",rankModelDetial.getYxzzsjxf());
            contentValues.put("zxf",rankModelDetial.getZxf());
            contentValues.put("yxzxf",rankModelDetial.getYxzxf());
            contentValues.put("cbjgxf",rankModelDetial.getCbjgxf());
            contentValues.put("sbjgxf",rankModelDetial.getSbjgxf());
            contentValues.put("pjxfjd",rankModelDetial.getPjxfjd());
            contentValues.put("gpabjpm",rankModelDetial.getGpabjpm());
            contentValues.put("gpazypm",rankModelDetial.getGpazypm());
            contentValues.put("pjcj",rankModelDetial.getPjcj());
            contentValues.put("pjcjbjpm",rankModelDetial.getPjcjbjpm());
            contentValues.put("pjcjzypm",rankModelDetial.getPjcjzypm());
            contentValues.put("jqxfcj",rankModelDetial.getJqxfcj());
            contentValues.put("jqbjpm",rankModelDetial.getJqbjpm());
            contentValues.put("jqzypm",rankModelDetial.getJqzypm());
            contentValues.put("tsjqxfcj",rankModelDetial.getTsjqxfcj());
            contentValues.put("tjsj",rankModelDetial.getTjsj());
            contentValues.put("bjrs",rankModelDetial.getBjrs());
            contentValues.put("zyrs",rankModelDetial.getZyrs());
            sqLiteDatabase.insert("Ranks",null,contentValues);
            contentValues.clear();

            //sqLiteDatabase.insert();
        }
    }

    public RankModelDetial getRank(String username){
        RankModelDetial rankModelDetial = null;
        if(isExitThisRank(username)){
            rankModelDetial = new RankModelDetial();
            Cursor cursor = sqLiteDatabase.query("Ranks",null," username = ?",new String[]{String.valueOf(username)},null,null,null);

            //Cursor cursor = sqLiteDatabase.query("Ranks",null,null,new String[]{String.valueOf(studentNumber)},null,null,null);
            if(cursor.moveToFirst()){
                do{
                    rankModelDetial.setXh(cursor.getString(cursor.getColumnIndex("xh")));
                    rankModelDetial.setXm(cursor.getString(cursor.getColumnIndex("xm")));
                    rankModelDetial.setBjh(cursor.getString(cursor.getColumnIndex("bjh")));
                    rankModelDetial.setBm(cursor.getString(cursor.getColumnIndex("bm")));
                    rankModelDetial.setZyh(cursor.getString(cursor.getColumnIndex("zyh")));
                    rankModelDetial.setZym(cursor.getString(cursor.getColumnIndex("zym")));
                    rankModelDetial.setXsh(cursor.getString(cursor.getColumnIndex("xsh")));
                    rankModelDetial.setXsm(cursor.getString(cursor.getColumnIndex("xsm")));
                    rankModelDetial.setNjdm(cursor.getString(cursor.getColumnIndex("njdm")));
                    rankModelDetial.setYqzxf(cursor.getString(cursor.getColumnIndex("yqzxf")));
                    rankModelDetial.setYxzzsjxf(cursor.getString(cursor.getColumnIndex("yxzzsjxf")));
                    rankModelDetial.setZxf(cursor.getString(cursor.getColumnIndex("zxf")));
                    rankModelDetial.setYxzxf(cursor.getString(cursor.getColumnIndex("yxzxf")));
                    rankModelDetial.setCbjgxf(cursor.getString(cursor.getColumnIndex("cbjgxf")));
                    rankModelDetial.setSbjgxf(cursor.getString(cursor.getColumnIndex("sbjgxf")));
                    rankModelDetial.setPjxfjd(cursor.getString(cursor.getColumnIndex("pjxfjd")));
                    rankModelDetial.setGpabjpm(cursor.getString(cursor.getColumnIndex("gpabjpm")));
                    rankModelDetial.setGpazypm(cursor.getString(cursor.getColumnIndex("gpazypm")));
                    rankModelDetial.setPjcj(cursor.getString(cursor.getColumnIndex("pjcj")));
                    rankModelDetial.setPjcjbjpm(cursor.getString(cursor.getColumnIndex("pjcjbjpm")));
                    rankModelDetial.setPjcjzypm(cursor.getString(cursor.getColumnIndex("pjcjzypm")));
                    rankModelDetial.setJqxfcj(cursor.getString(cursor.getColumnIndex("jqxfcj")));
                    rankModelDetial.setJqbjpm(cursor.getString(cursor.getColumnIndex("jqbjpm")));
                    rankModelDetial.setJqzypm(cursor.getString(cursor.getColumnIndex("jqzypm")));
                    rankModelDetial.setTsjqxfcj(cursor.getString(cursor.getColumnIndex("tsjqxfcj")));
                    rankModelDetial.setTjsj(cursor.getString(cursor.getColumnIndex("tjsj")));
                    rankModelDetial.setBjrs(cursor.getString(cursor.getColumnIndex("bjrs")));
                    rankModelDetial.setZyrs(cursor.getString(cursor.getColumnIndex("zyrs")));
                    rankModelDetial.setDlrs(cursor.getString(cursor.getColumnIndex("dlrs")));
                    rankModelDetial.setGpadlpm(cursor.getString(cursor.getColumnIndex("gpadlpm")));
                }while (cursor.moveToNext());
            }
        }
        return rankModelDetial;
    }

    /**
     * @return 返回 年份列表
     */
    public List<Integer> loadYearList(){
        List<Integer> list = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query("YearList",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                list.add(cursor.getInt(cursor.getColumnIndex("year")));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    /**
     * 返回所有学院的数据
     */
    public List<YearCollege2.DataBean.CollegesBean> loadYearCollege(){
        List<YearCollege2.DataBean.CollegesBean> list = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query("YearCollege2",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                YearCollege2.DataBean.CollegesBean collegesBean = new YearCollege2.DataBean.CollegesBean();
                collegesBean.setId(cursor.getInt(cursor.getColumnIndex("college_id")));
                collegesBean.setCollege(cursor.getString(cursor.getColumnIndex("college_name")));
                list.add(collegesBean);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    /**
     * Year 和 college 是一起获取的，查找表中是否存在最后一年的年份
     * 判断表是否存在这个学院
     * @return
     */
    public boolean isExitThisCollege(int year){
        Cursor cursor = sqLiteDatabase.query("YearList",null,"year = ?",new String[]{String.valueOf(year)},null,null,null);
        if(cursor.moveToFirst()){
            do{
                if(year == cursor.getInt(cursor.getColumnIndex("year"))){
                    return true;
                }
            }while (cursor.moveToNext());
        }
        return false;
    }

    /**
     * @param collegeId 根据传入的 collegeId 获得该学院的所有专业
     * @return 该学院的专业集合
     */
   /* public List<Major.DataBean> loadMajor(int collegeId){
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
    }*/

    /**
     * @param id 根据传入的 专业 ID 判断表中是否存在这节课
     * @return 存在返回 true，不存在返回 false；
     */
    private boolean isExitThisMajor(int id) throws Exception {
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
     * @param major_name 专业名称
     * @return 返回该专业该学年的所有班级
     */
    public List<Classe> loadClassName(String major_name){
        List<Classe> list = new ArrayList<>();
        Classe classe = null;
        Cursor cursor = sqLiteDatabase.query("ClassName",null,"major_name like ?",new String[]{major_name},null,null,null);
        if(cursor.moveToFirst()){
            do{
                classe = new Classe(
                        cursor.getString(cursor.getColumnIndex("class_name")),
                        cursor.getString(cursor.getColumnIndex("class_number"))
                );
                list.add(classe);
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
    private boolean isExitThisClassName(int year,String major_name) throws Exception{
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
     * @param week_num 课表的周几
     * @param course_start 课表的周几的第几节
     * @return 根据 Type 不同，分别返回课程名字，上课地点，上课老师，上课的周数
     */
    public List<String> loadSchedules(int Type,String class_name,int week_num,int course_start){
        //List<String> list = new ArrayList<>();
        //String s = null;
        List<String> s = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query("Schedule",null,"class_name = ? and week_num = ? and course_start = ?",
                        new String[]{String.valueOf(class_name),String.valueOf(week_num),String.valueOf(course_start)},null,null,null);
        if(cursor.moveToFirst()){
            //Log.d(DbTag,"Type is : " + Type+"");
            do{
                switch (Type){
                    case DayuanDailyDatabase.TYPE_GET_SUB_NAME:
                        s.add(cursor.getString(cursor.getColumnIndex("course_name")) + cursor.getString(cursor.getColumnIndex("course_name_suffix")));
                        break;
                    case DayuanDailyDatabase.TYPE_GET_SUB_PLACE:
                        s.add(cursor.getString(cursor.getColumnIndex("course_campus"))+" "+cursor.getString(cursor.getColumnIndex("course_build")) + " " + cursor.getString(cursor.getColumnIndex("course_room"))) ;
                        break;
                    case DayuanDailyDatabase.TYPE_GET_SUB_TEACHER:
                        s.add(cursor.getString(cursor.getColumnIndex("teacher_name")));
                        break;
                    case DayuanDailyDatabase.TYPE_GET_SUB_RAWWEEK:
                        s.add(cursor.getString(cursor.getColumnIndex("course_rawWeek")));
                        break;
                    case DayuanDailyDatabase.TYPE_GET_SUB_WEEKS:
                        try {
                            s.add(cursor.getString(cursor.getColumnIndex("course_weeks")));
                        }catch (Exception e){

                        }
                        break;
                    case DayuanDailyDatabase.TYPE_GET_SUB_LENGTH:
                        s.add(cursor.getString(cursor.getColumnIndex("course_length")));
                        break;
                }
            }while (cursor.moveToNext());
        }
        cursor.close();
        return s;

    }

    public int getScheduleId(String className){
        Cursor cursor = sqLiteDatabase.query("Schedule",null,"class_name = ?",new String[]{String.valueOf(className)},null,null,null);
        int scheduleId = 0;
        if(cursor.moveToFirst()){
            do{
                if(className.equals(cursor.getString(cursor.getColumnIndex("class_name")))){
                    scheduleId = cursor.getInt(cursor.getColumnIndex("schedule_id"));
                    return scheduleId;
                }
            }while (cursor.moveToNext());
        }
        return scheduleId;
    }

    /**
     * @return 如果数据库中存在这个课表，返回为 true
     * @throws Exception 「不知道什么」异常c
     */
    private boolean isExitThisSchedule(String className,String term_name) throws Exception{
        Cursor cursor = sqLiteDatabase.query("Schedule",null,"class_name = ? and term = ?",new String[]{String.valueOf(className),String.valueOf(term_name)},null,null,null);
        if(cursor.moveToFirst()){
            do {
                String cname = cursor.getString(cursor.getColumnIndex("class_name"));
                String tname = cursor.getString(cursor.getColumnIndex("term"));
                if(className.equals(cname) && tname.equals(tname)){
                    cursor.close();
                    return true;
                }
            }while (cursor.moveToNext());
        }
        cursor.close();
        return false;
    }

    /**
     * 清空课表缓存
     */
    public void clearTheSchedule(){
        List<String> classNameList = getSavedClassName();
        for(String mString : classNameList) {
            sqLiteDatabase.delete("Schedule", "class_name like ?", new String[]{ mString });
        }
    }

    /**
     * @return 返回保存过的班级名称
     */
    public List<String> getSavedClassName(){
        Cursor cursor = sqLiteDatabase.query("Schedule",null,null,null,null,null,null,null);
        List<String> classNameList = new ArrayList<>();
        classNameList.add("已存课表");
        classNameList.add("清空课表");
        if(cursor.moveToFirst()){
            do{
                if(!isExistThisClassInScheduler(cursor.getString(cursor.getColumnIndex("class_name")),classNameList)){
                    classNameList.add(cursor.getString(cursor.getColumnIndex("class_name")));
                }
            }while (cursor.moveToNext());
        }
        return classNameList;
    }

    /**
     * 判断 list 中是否存在这个 String
     * @param className 班级名称
     * @param list 保存班级名称的 list
     * @return 如果存在则返回 true
     */
    private boolean isExistThisClassInScheduler(String className, List<String> list){
        for(String s : list){
            if( s.equals(className)){
                return true;
            }
        }
        return false;
    }

    /**
     * @param studentNumber 学生学号
     * @return 返回所有学期名称的 List 数组
     */
    public List<String> getTermName(String studentNumber) throws Exception{
        List<String> termsList = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query("Grades",null,"studentNumber like ?",new String[]{studentNumber},null,null,null);
        if(cursor.moveToLast()){
            do{
                String term = cursor.getString(cursor.getColumnIndex("name"));
                if(!isExistThisTerm(termsList,term)) {
                    termsList.add(term);
                }
            }while (cursor.moveToPrevious());
        }
        cursor.close();
        return termsList;
    }

    /**
     * 查找数组中是否已经存在该学期
     * @param termsList 学期名称的数组
     * @param term 一个学期的名称
     * @return 存在 返回 true
     */
    private boolean isExistThisTerm(List<String> termsList,String term){
        for(String s : termsList){
            if (s.equals(term)){
                return true;
            }
        }
        return false;
    }

    /**
     * @param studentNumber 学号
     * @param term 学期名字
     * @return Grade 的 List 数组
     */
    public List<Grades.DataBean.GradesBean> getTermDetial(String studentNumber, String term){
        List<Grades.DataBean.GradesBean> gradesBeans = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query("Grades",null,"studentNumber like ? and name like ?",new String[]{studentNumber,term},null,null,null);
        if(cursor.moveToFirst()){
            do{
                Grades.DataBean.GradesBean gradesBean = new Grades.DataBean.GradesBean();
                gradesBean.setClassNumber(cursor.getString(cursor.getColumnIndex("classNumber")));
                gradesBean.setClassOrder(cursor.getString(cursor.getColumnIndex("classOrder")));
                gradesBean.setClassName(cursor.getString(cursor.getColumnIndex("className")));
                gradesBean.setEnglishName(cursor.getString(cursor.getColumnIndex("englishName")));
                gradesBean.setCredit(cursor.getString(cursor.getColumnIndex("credit")));
                gradesBean.setClassInfo(cursor.getString(cursor.getColumnIndex("classInfo")));
                gradesBean.setGrade(cursor.getString(cursor.getColumnIndex("grade")));
                gradesBeans.add(gradesBean);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return gradesBeans;
    }



    public void setTeacherNameList(List<String> teacherNameList){
        this.teacherNameList = teacherNameList;
    }

    public List<String> getTeacherNameList(){
        return teacherNameList;
    }

    /**
     * 保存所有到校区和教室信息
     */
    public void saveRestclass(RestClass restClass){
        if (null != restClass){
            ContentValues contentValues = new ContentValues();
            SharedPreferences.Editor editor = context.getSharedPreferences("RestClass",Context.MODE_PRIVATE).edit();
            editor.putString("term_name",restClass.getData().getTerm());
            editor.putString("term_value",restClass.getData().getTermValue());
            editor.commit();
            for (int i = 0 ; i < restClass.getData().getCampus().size(); i++){

                for (int j = 0 ; j < restClass.getData().getCampus().get(i).getBuilds().size(); j++){
                    contentValues.put("campus_name",restClass.getData().getCampus().get(i).getName());
                    contentValues.put("campus_value",restClass.getData().getCampus().get(i).getValue());
                    contentValues.put("build_name",restClass.getData().getCampus().get(i).getBuilds().get(j).getName());
                    contentValues.put("build_value",restClass.getData().getCampus().get(i).getBuilds().get(j).getValue());
                    sqLiteDatabase.insert("RestClass",null,contentValues);
                    contentValues.clear();
                }
            }
        }
    }

    /**
     * @return 返回所有校区列表
     */
    public Map<String,String> getCampusMap(){
        Map<String,String>  campusMap = new LinkedHashMap<>();
        Cursor cursor = sqLiteDatabase.query("RestClass",null,null,null,null,null,null,null);
        if (null != cursor && cursor.moveToFirst()){
            do {
                campusMap.put(
                        cursor.getString(cursor.getColumnIndex("campus_value")),
                        cursor.getString(cursor.getColumnIndex("campus_name"))
                );
            }while (cursor.moveToNext());
        }
        return campusMap;
    }

    /**
     * @param campusName 校区的名称
     * @return 返回该校区的所有
     */
    public Map<String,String> getBuildMap(String campusName){
        Map<String,String> buildsMap = new LinkedHashMap<>();
        Cursor cursor = sqLiteDatabase.query("RestClass",null,"campus_name like ?",new String[]{campusName},null,null,null,null);
        if (null != cursor && cursor.moveToFirst()){
            do {
                buildsMap.put(
                        cursor.getString(cursor.getColumnIndex("build_value")),
                        cursor.getString(cursor.getColumnIndex("build_name"))
                );
            }while (cursor.moveToNext());
        }
        return buildsMap;
    }

    public void rebuildRestClassTable(){
        sqLiteDatabase.execSQL("DROP TABLE if exists RestClass");
        sqLiteDatabase.execSQL(DaYuanDailyDBOpenHelper.CREATE_RESTCLASS);
    }
}