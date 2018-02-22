package com.nenguou.dayuandaily.UI;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Button;

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
    String class_name;
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
        //this.setPadding();
    }

    private void showOrNot() {
        if("".equals(this.getText())){
            this.setVisibility(INVISIBLE);
        }
    }

    private void initData(AttributeSet attrs) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("User_YearCollege",Context.MODE_PRIVATE);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyScheduleButton);
        weekNum = typedArray.getInteger(R.styleable.MyScheduleButton_weekNum,1);
        classNum = typedArray.getInteger(R.styleable.MyScheduleButton_classNum,1);
        dayuanDailyDatabase = DayuanDailyDatabase.getInstance(context);
        schedule_id = sharedPreferences.getInt("schedule_id",2465);
        class_name = dayuanDailyDatabase.loadSchedules(DayuanDailyDatabase.TYPE_GET_SUB_NAME,schedule_id,weekNum,classNum);
        if(class_name != null) {
            Log.d(Tag,"class_name is: " + class_name);
            this.setText(class_name);
        }
        //Log.d("Spefsd","college_id:"+college_id+"  weekNum:"+weekNum+"  classNum:"+classNum);
    }
}
