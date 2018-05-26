package com.nenguou.dayuandaily.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.gson.Gson;
import com.nenguou.dayuandaily.Model.Class;
import com.nenguou.dayuandaily.Model.ClassDetial;
import com.nenguou.dayuandaily.Model.ClassName;
import com.nenguou.dayuandaily.Model.Grades;
import com.nenguou.dayuandaily.Model.Major;
import com.nenguou.dayuandaily.Model.RankModel;
import com.nenguou.dayuandaily.Model.RankModelDetial;
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

    public void saveYearCollege(YearCollege yearCollege) {
        if (yearCollege != null) {
            int lastYear = yearCollege.getData().getYears().get(yearCollege.getData().getYears().size()-1).getYear();
            if(!isExitThisCollege(lastYear)) {
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
                List<YearCollege.DataBean.YearsBean> yearsBeans = yearCollege.getData().getYears();
                for (YearCollege.DataBean.YearsBean yearsBean : yearsBeans) {
                    contentValues.put("year_id", yearsBean.getId());
                    contentValues.put("year", yearsBean.getYear());
                    sqLiteDatabase.insert("YearList", null, contentValues);
                    contentValues.clear();
                }
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


    public void saveClass(Class mClass, ClassDetial classDetial){
        if(mClass != null && classDetial != null){
            ContentValues contentValues = new ContentValues();
            try {
                if(!isExitThisSchedule(mClass.getData().getId())){
                    for(ClassDetial.ScheduleBean scheduleBean: classDetial.getSchedule()){
                        for(ClassDetial.ScheduleBean.DetailsBean detailsBean:scheduleBean.getDetails()){
                            contentValues.put("schedule_id",mClass.getData().getId());
                            contentValues.put("term",mClass.getData().getTerm());
                            contentValues.put("year",mClass.getData().getYear());
                            contentValues.put("class_name",mClass.getData().getName());
                            contentValues.put("college_name",mClass.getData().getCollege());
                            contentValues.put("major_name",mClass.getData().getMajor());
                            contentValues.put("course_start_week",scheduleBean.getWeeks().toString().split(",")[0].toString().split("\\[")[1]);
                            //contentValues.put("link",mClass.getData().getLink().toString());
                            contentValues.put("class_term",classDetial.getTerm());
                            contentValues.put("week_num",detailsBean.getDay()+1);
                            contentValues.put("course_start",detailsBean.getStart()+1);
                            contentValues.put("course_length",detailsBean.getLength());
                            contentValues.put("course_name",scheduleBean.getName());
                            contentValues.put("course_name_suffix",scheduleBean.getName_suffix());
                            contentValues.put("teacher_name",scheduleBean.getTeacher());
                            contentValues.put("course_rawWeek",scheduleBean.getRawWeek());
                            contentValues.put("course_weeks",scheduleBean.getWeeks().toString());
                            contentValues.put("course_build",detailsBean.getBuild());
                            contentValues.put("course_campus",detailsBean.getCampus());
                            contentValues.put("course_room",detailsBean.getRoom());
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
            try {
                if (!isExitThisSchedule(mClass.getData().getId())) {
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
                    contentValues.put("classNumber",gradesBean.getClassName());
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

    /**
     * @param studentNumber 学生学号
     * @return 如果数据库中存在这个学生的 Rank，返回 true，不存在，返回 false；
     */
    public boolean isExitThisRank(int studentNumber){
        Cursor cursor = sqLiteDatabase.query("Ranks",null," xh = ?",new String[]{String.valueOf(studentNumber)},null,null,null);
        if(cursor.moveToFirst()){
            do{
                if(studentNumber == cursor.getInt(cursor.getColumnIndex("xh"))){
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

        if(isExitThisRank(Integer.parseInt(rankModelDetial.getXh()))){  // 判断表中原先是否有该数据
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

    public RankModelDetial getRank(int studentNumber){
        RankModelDetial rankModelDetial = null;
        if(isExitThisRank(studentNumber)){
            rankModelDetial = new RankModelDetial();
            Cursor cursor = sqLiteDatabase.query("Ranks",null," xh = ?",new String[]{String.valueOf(studentNumber)},null,null,null);

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
     * @param schedule_id 课表的唯一 id
     * @param week_num 课表的周几
     * @param course_start 课表的周几的第几节
     * @return 根据 Type 不同，分别返回课程名字，上课地点，上课老师，上课的周数
     */
    public List<String> loadSchedules(int Type,int schedule_id,int week_num,int course_start){
        //List<String> list = new ArrayList<>();
        //String s = null;
        List<String> s = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query("Schedule",null,"schedule_id = ? and week_num = ? and course_start = ?",
                        new String[]{String.valueOf(schedule_id),String.valueOf(week_num),String.valueOf(course_start)},null,null,null);
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
                        s.add(cursor.getString(cursor.getColumnIndex("course_weeks")));
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

    /**
     * @param schedule_id 传入课表的唯一 id
     * @return 如果数据库中存在这个课表，返回为 true
     * @throws Exception 「不知道什么」异常c
     */
    private boolean isExitThisSchedule(int schedule_id) throws Exception{
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
}