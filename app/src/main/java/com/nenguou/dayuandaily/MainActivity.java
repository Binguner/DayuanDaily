package com.nenguou.dayuandaily;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.nenguou.dayuandaily.Utils.RxDayuan;

public class MainActivity extends AppCompatActivity {

    private Button test_YearCollege,test_Major,test_classname,test_Class;
    private RxDayuan rxDayuan;
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
                rxDayuan.getMajor(22);
            }
        });

        test_classname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rxDayuan.getClassName(2017,"软件工程");
            }
        });

        test_Class.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rxDayuan.getClass("软件1632","2017-2018-2-1");
            }
        });
    }

    private void initId() {
        rxDayuan = new RxDayuan(this);
        test_YearCollege = findViewById(R.id.test_YearCollege);
        test_Major = findViewById(R.id.test_Major);
        test_classname = findViewById(R.id.test_classname);
        test_Class = findViewById(R.id.test_Class);
    }
}
