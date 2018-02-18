package com.nenguou.dayuandaily;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.nenguou.dayuandaily.DataBase.DayuanDailyDatabase;
import com.nenguou.dayuandaily.Model.Major;
import com.nenguou.dayuandaily.Model.YearCollege;
import com.nenguou.dayuandaily.Utils.RxDayuan;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button test_YearCollege,test_Major,test_classname,test_Class,test_loadYearColleg,test_loadMajor,test_loadClassName,test_loadClass;
    private RxDayuan rxDayuan;
    private final String mainTag = "MainActivityTag";
    DayuanDailyDatabase dayuanDailyDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initId();
        setListener();
    }

    private void setListener() {
        test_YearCollege.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rxDayuan.getYearCollege();
            }
        });

        test_Major.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rxDayuan.getMajor(12);
            }
        });

        test_classname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rxDayuan.getClassName(2016,"工业设计");
            }
        });

        test_Class.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rxDayuan.getClass("软件1632","2017-2018-2-1");
            }
        });

        test_loadYearColleg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<YearCollege.DataBean.CollegesBean> list = dayuanDailyDatabase.loadYearCollege();
                for (YearCollege.DataBean.CollegesBean collegesBean : list) {
                    //Log.d(mainTag, "Id: "+collegesBean.getId() + "  College: " + collegesBean.getCollege());
                }
            }
        });

        test_loadMajor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Major.DataBean> list = dayuanDailyDatabase.loadMajor(12);
                for(Major.DataBean dataBean : list){
                    Log.d(mainTag, "Id: "+dataBean.getId() + "  College_id : " + dataBean.getCollegeId()+"  Major: "+dataBean.getMajor());

                }
            }
        });

        test_loadClassName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<String> s = dayuanDailyDatabase.loadClassName(2016,"工业设计");
                for(String s1: s){
//                    Log.d(mainTag, "class: " + s1);
                }
            }
        });
        test_loadClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = dayuanDailyDatabase.loadSchedules(DayuanDailyDatabase.TYPE_GET_SUB_TIME,2465,5,1);
                Log.d(mainTag, "class_name is : " + s);
            }
        });
    }

    private void initId() {
        test_loadClass = findViewById(R.id.test_loadClass);
        test_loadYearColleg = findViewById(R.id.test_loadYearColleg);
        rxDayuan = new RxDayuan(this);
        test_YearCollege = findViewById(R.id.test_YearCollege);
        test_Major = findViewById(R.id.test_Major);
        test_classname = findViewById(R.id.test_classname);
        test_Class = findViewById(R.id.test_Class);
        test_loadMajor = findViewById(R.id.test_loadMajor);
        test_loadClassName = findViewById(R.id.test_loadClassName);
        dayuanDailyDatabase = DayuanDailyDatabase.getInstance(this);
    }
}
