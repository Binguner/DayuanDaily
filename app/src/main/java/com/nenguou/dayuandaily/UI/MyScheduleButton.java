package com.nenguou.dayuandaily.UI;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.nenguou.dayuandaily.DataBase.DayuanDailyDatabase;
import com.nenguou.dayuandaily.R;

/**
 * Created by binguner on 2018/2/21.
 */

public class MyScheduleButton extends android.support.v7.widget.AppCompatButton {

    Context context;
    int weekNum;
    int classNum;
    int schedule_id;
    int length;
    String class_name;
    String weeks;
    String[] weeksList;
    int[] weeksArray;
    String newString1;
    String newString2;
    String newString3;

    String detial;

    static String selectedTime;
    private static final String Tag = "MySheduleButtonTag";
    DayuanDailyDatabase dayuanDailyDatabase;
    public MyScheduleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initData(attrs);
        initBtnView();
        showOrNot();
    }

    private void initBtnView() {
        this.setTextSize(12);

        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,detial,Toast.LENGTH_SHORT).show();
            }
        });
        //int btnHeight = this.getHeight();
        //Log.d(Tag,"btnHeight is : " + btnHeight);
        //this.setPadding();
    }

    private void showOrNot() {
        if("".equals(this.getText())){
            this.setBackgroundColor(Color.TRANSPARENT);
            this.setClickable(false);
            //this.setVisibility(INVISIBLE);
        }
    }

    public int getLength(){
        return this.length;
    }

    public static void setSelectedTime(String s){
        selectedTime = s;
        //Log.d(Tag,"s : " + s );
        //showTheSelectTimeSchedule();
    }

    private static void showTheSelectTimeSchedule() {

    }

    public int[] getWeekList(){
        return weeksArray;
    }

    public void getDetial(){
        Toast.makeText(context,detial,Toast.LENGTH_SHORT).show();
    }


    private void initData(AttributeSet attrs) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("User_YearCollege",Context.MODE_PRIVATE);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyScheduleButton);
        weekNum = typedArray.getInteger(R.styleable.MyScheduleButton_weekNum,1);
        classNum = typedArray.getInteger(R.styleable.MyScheduleButton_classNum,1);
        dayuanDailyDatabase = DayuanDailyDatabase.getInstance(context);
        schedule_id = sharedPreferences.getInt("schedule_id",9514);
        try {
            class_name = dayuanDailyDatabase.loadSchedules(DayuanDailyDatabase.TYPE_GET_SUB_NAME, schedule_id, weekNum, classNum);
        }catch (Exception e){
            Toast.makeText(context,"数据加载失败，请重试",Toast.LENGTH_SHORT).show();
        }

        //Log.d(Tag,"class_Name is : "+ class_name +" Schedule_id is : "+ schedule_id + " weekNum is : " + weekNum + " classNum is : " + classNum);
        if(class_name != null) {
            this.setText(class_name);
            this.length = Integer.parseInt(dayuanDailyDatabase.loadSchedules(DayuanDailyDatabase.TYPE_GET_SUB_LENGTH,schedule_id,weekNum,classNum));
            weeks = dayuanDailyDatabase.loadSchedules(DayuanDailyDatabase.TYPE_GET_SUB_WEEKS, schedule_id, weekNum, classNum);
            detial = dayuanDailyDatabase.loadSchedules(DayuanDailyDatabase.TYPE_GET_SUB_PLACE,schedule_id,weekNum,classNum)+" "+ dayuanDailyDatabase.loadSchedules(DayuanDailyDatabase.TYPE_GET_SUB_TEACHER,schedule_id,weekNum,classNum);
            if(weeks!=null && !weeks.equals("")){
                //Log.d(Tag,"before : "+weeks);
                newString1 = weeks.replace("]","");
                newString2 = newString1.replace("[","");
                newString3 = newString2.replace(" ","");
                //Log.d(Tag,"after : "+weeks);
                weeksList = newString3.split(",");
                weeksArray = new int[weeksList.length];
                for(int i = 0; i < weeksList.length; i++){
                    weeksArray[i] = Integer.parseInt(weeksList[i]);
                }
                for(int s: weeksArray){
                    //Log.d(Tag,s+" ");
                }
            }
            //Log.d(Tag,"class_name is: " + class_name + " length is : " + length + " weeks is : " + weeks);
        }
        //Log.d("Spefsd","college_id:"+college_id+"  weekNum:"+weekNum+"  classNum:"+classNum);
    }
}
