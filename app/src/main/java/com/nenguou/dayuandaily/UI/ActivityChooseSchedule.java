package com.nenguou.dayuandaily.UI;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.nenguou.dayuandaily.DataBase.DayuanDailyDatabase;
import com.nenguou.dayuandaily.Model.Major;
import com.nenguou.dayuandaily.Model.YearCollege;
import com.nenguou.dayuandaily.R;
import com.nenguou.dayuandaily.Utils.RetrofitCallbackListener;
import com.nenguou.dayuandaily.Utils.RxDayuan;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.InflaterInputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.qqtheme.framework.picker.OptionPicker;
import cn.qqtheme.framework.picker.WheelPicker;
import cn.qqtheme.framework.widget.WheelView;

/**
 * SharedPreferences : User_YearCollege
 * year:Int 2016
 * theSelectedTime : 1 or 2 or 3 or 4
 * isFirstLoadCollege : true / false
 * isScheduleSelected: true / false
 * start : 1520179204000 in rxDayuan (long)
 * collegeId:Int 22 --软件学院
 * majorId:Int 22
 * majorName:String 软件工程
 * className:String 软件1632
 * term:String 2017-2018-2-1
 * schedule_id:Int 2465  in rxDayuan
 */
public class ActivityChooseSchedule extends AppCompatActivity {

    private final String AtyCSTag = "ActivityChoosheduleTag";
    private final int CHOOSE_YEAR = 0;
    private final int CHOOSE_COLLEGE = 1;
    private final int CHOOSE_MAJOR = 2;
    private final int CHOOSE_CLASS = 3;
    @BindView(R.id.pleasechooseclass) TextView pleasechooseclass;
    @BindView(R.id.chooseYearText) TextView chooseYearText;
    @BindView(R.id.chooseCollegeText) TextView chooseCollegeText;
    @BindView(R.id.chooseMajorText) TextView chooseMajorText;
    @BindView(R.id.chooseClassText) TextView chooseClassText;
    @BindView(R.id.chooseClassOver) Button chooseClassOver;
    @BindView(R.id.chooseClassAgain) Button chooseClassAgain;

    @BindView(R.id.choose_year_cardview) CardView choose_year_cardview;
    @BindView(R.id.choose_college_cardview) CardView choose_college_cardview;
    @BindView(R.id.choose_major_cardview) CardView choose_major_cardview;
    @BindView(R.id.choose_class_cardview) CardView choose_class_cardview;
    DayuanDailyDatabase dayuanDailyDatabase;
    RxDayuan rxDayuan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.myAppTheme);
        setContentView(R.layout.activity_choose_schedule);
        ButterKnife.bind(this);
        rxDayuan = new RxDayuan(this);
        initDatas();
        dayuanDailyDatabase = DayuanDailyDatabase.getInstance(this);

    }

    private void initDatas() {
        SharedPreferences sharedPreferences = getSharedPreferences("User_YearCollege",MODE_PRIVATE);
        boolean isFirstLoad = sharedPreferences.getBoolean("isFirstLoadCollege",true);
        //boolean isScheduleSelected = sharedPreferences.getBoolean("isScheduleSelected",false);
        if (isFirstLoad) {
            Toast.makeText(this, "正在加载数据，请稍等", Toast.LENGTH_SHORT).show();
            rxDayuan.getYearCollege(new RetrofitCallbackListener() {
                @Override
                public void onFinish(int status) {
                    Toast.makeText(ActivityChooseSchedule.this, "数据加载成功", Toast.LENGTH_SHORT).show();
                    SharedPreferences.Editor editor = getSharedPreferences("User_YearCollege", MODE_PRIVATE).edit();
                    editor.putBoolean("isFirstLoadCollege", false);
                    //editor.putBoolean("isScheduleSelected",true);
                    editor.commit();
                }

                @Override
                public void onError(Exception e) {
                    Toast.makeText(ActivityChooseSchedule.this, "数据加载失败，请重新打开 App", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void setText(String msg) {

                }
            });
        }
//        if(!isFirstLoad){
//            Intent intent = new Intent(this,ActivityScheduler.class);
//            startActivity(intent);
//            this.finish();
//        }
    }

    @OnClick(R.id.choose_year_cardview)
    public void chooseYearClick(View view){
        showChooseDialog(CHOOSE_YEAR);
    }

    @OnClick(R.id.choose_college_cardview)
    public void chooseCollegeClick(View view){
        if(chooseYearText.getText().equals("请选择")){
            Toast.makeText(this,"请先选择年级",Toast.LENGTH_SHORT).show();
        }else {
            showChooseDialog(CHOOSE_COLLEGE);
        }
    }

    @OnClick(R.id.choose_major_cardview)
    public void chooseMajorClick(View view){
        if(chooseCollegeText.getText().equals("请选择")){
            Toast.makeText(this,"请先选择学院",Toast.LENGTH_SHORT).show();
        }else {
            showChooseDialog(CHOOSE_MAJOR);
        }
    }

    @OnClick(R.id.choose_class_cardview)
    public void chooseClassClick(View view){
        if(chooseMajorText.getText().equals("请选择")){
            Toast.makeText(this,"请先选择专业",Toast.LENGTH_SHORT).show();
        }else {
            showChooseDialog(CHOOSE_CLASS);
        }
    }

    @OnClick(R.id.chooseClassAgain)
    public void chooseClassAgainClick(View view){
        chooseYearText.setText("请选择");
        chooseCollegeText.setText("请选择");
        chooseMajorText.setText("请选择");
        chooseClassText.setText("请选择");
        pleasechooseclass.setText("请选择年级");
    }

    @OnClick(R.id.chooseClassOver)
    public void chooseClassOverClick(View v){
        SharedPreferences.Editor editor = getSharedPreferences("User_YearCollege",MODE_PRIVATE).edit();
        editor.putBoolean("isScheduleSelected",true);
        editor.commit();
        Intent intent = new Intent(this,ActivityScheduler.class);
        startActivity(intent);
        this.finish();
    }

    public void showChooseDialog(int type){
        final String[] dataArray;
        OptionPicker picker = null;
        switch (type){
            case CHOOSE_YEAR:

                final SharedPreferences.Editor editor = getSharedPreferences("User_YearCollege",MODE_PRIVATE).edit();
                final List<Integer> yearList = dayuanDailyDatabase.loadYearList();
                if(yearList!=null) {
                    dataArray = new String[yearList.size()];
                    for (int i = 0; i < yearList.size(); i++) {
                        dataArray[i] = yearList.get(i) + "";
                    }
                    picker = new OptionPicker(this, dataArray);
                    picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
                        @Override
                        public void onOptionPicked(int index, String item) {
                            chooseYearText.setText(dataArray[index]);
                            editor.putInt("year", yearList.get(index));
                            editor.commit();
                            pleasechooseclass.setText("请选择学院");
                            chooseCollegeText.setText("请选择");
                            chooseMajorText.setText("请选择");
                            chooseClassText.setText("请选择");
                        }
                    });
                }
                break;
            case CHOOSE_COLLEGE:
                final List<YearCollege.DataBean.CollegesBean> collegeslist = dayuanDailyDatabase.loadYearCollege();
                if(collegeslist!=null) {
                    dataArray = new String[collegeslist.size()];
                    for (int i = 0; i < collegeslist.size(); i++) {
                        dataArray[i] = collegeslist.get(i).getCollege();
                    }
                    picker = new OptionPicker(this, dataArray);
                    picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
                        @Override
                        public void onOptionPicked(int index, String item) {
                            chooseCollegeText.setText(dataArray[index]);
                            SharedPreferences.Editor editor = getSharedPreferences("User_YearCollege", MODE_PRIVATE).edit();
                            editor.putInt("collegeId", collegeslist.get(index).getId());
                            rxDayuan.getMajor(collegeslist.get(index).getId());
                            editor.commit();
                            pleasechooseclass.setText("请选择专业");
                            chooseMajorText.setText("请选择");
                            chooseClassText.setText("请选择");
                        }
                    });
                }
                break;
            case CHOOSE_MAJOR:
                SharedPreferences sharedPreferences = getSharedPreferences("User_YearCollege",MODE_PRIVATE);
                int collegeId = sharedPreferences.getInt("collegeId",22);
                final int year = sharedPreferences.getInt("year",2016);
                final List<Major.DataBean> majorList = dayuanDailyDatabase.loadMajor(collegeId);
                if(majorList!=null) {
                    dataArray = new String[majorList.size()];
                    for (int i = 0; i < majorList.size(); i++) {
                        dataArray[i] = majorList.get(i).getMajor();
                    }
                    picker = new OptionPicker(this, dataArray);
                    picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
                        @Override
                        public void onOptionPicked(int index, String item) {
                            chooseMajorText.setText(dataArray[index]);
                            SharedPreferences.Editor editor = getSharedPreferences("User_YearCollege", MODE_PRIVATE).edit();
                            editor.putInt("majorId", majorList.get(index).getId());
                            editor.putString("majorName", majorList.get(index).getMajor());
                            //Log.d(Tag, majorList.get(index).getMajor());
                            String majorName = majorList.get(index).getMajor();
                            rxDayuan.getClassName(year, majorName);
                            editor.commit();
                            pleasechooseclass.setText("请选择班级");
                            chooseClassText.setText("请选择");

                        }
                    });
                }
                break;
            case CHOOSE_CLASS:
                SharedPreferences sharedPreferences1 = getSharedPreferences("User_YearCollege",MODE_PRIVATE);
                final int classYear = sharedPreferences1.getInt("year",2016);
                final String class_term = sharedPreferences1.getString("term","2017-2018-2-1");
                String major_name = sharedPreferences1.getString("majorName","软件工程");
                List<String> classNameList = dayuanDailyDatabase.loadClassName(classYear,major_name);
                if(classNameList != null){
                    dataArray = new String[classNameList.size()];
                    for(int i = 0 ; i < classNameList.size(); i++){
                        Log.d(AtyCSTag,classNameList.get(i).toString());
                        dataArray[i] = classNameList.get(i);
                    }
                    picker = new OptionPicker(this,dataArray);
                    picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
                        @Override
                        public void onOptionPicked(int index, String item) {
                            chooseClassText.setText(dataArray[index]);
                            SharedPreferences.Editor editor1 = getSharedPreferences("User_YearCollege",MODE_PRIVATE).edit();
                            editor1.putString("className",dataArray[index]);
                            editor1.commit();
                            String className = dataArray[index].toString();
                            pleasechooseclass.setText("选择完毕，请点击确定");
                            rxDayuan.getClass(className,class_term);
                        }
                    });
                }
        }
        picker.setOffset(3);
        picker.setLineSpaceMultiplier(3);
        picker.setSelectedIndex(2);
        picker.setTextSize(18);
        try {
            picker.show();
        }catch (IllegalArgumentException e){
            Toast.makeText(this,"正在加载数据，请稍等 :)",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}