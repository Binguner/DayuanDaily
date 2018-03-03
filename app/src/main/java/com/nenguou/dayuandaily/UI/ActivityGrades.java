package com.nenguou.dayuandaily.UI;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.nenguou.dayuandaily.Adapter.GradeOutAdapter;
import com.nenguou.dayuandaily.DataBase.DayuanDailyDatabase;
import com.nenguou.dayuandaily.Model.Grades;
import com.nenguou.dayuandaily.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityGrades extends AppCompatActivity {

    @BindView(R.id.grade_out_recyclerview) RecyclerView grade_out_recyclerview;

    private GradeOutAdapter gradeOutAdapter;
    private RecyclerView.LayoutManager layoutManager;
    DayuanDailyDatabase dayuanDailyDatabase;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.myAppTheme);
        setContentView(R.layout.activity_grades);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        sharedPreferences = getSharedPreferences("User_grades",MODE_PRIVATE);
        String studentNumber = sharedPreferences.getString("username","");
        dayuanDailyDatabase = DayuanDailyDatabase.getInstance(this);
        List<String> termNameArray = dayuanDailyDatabase.getTermName(studentNumber);

        layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        gradeOutAdapter = new GradeOutAdapter(this,R.layout.activity_grades_detial,termNameArray);

        grade_out_recyclerview.setLayoutManager(layoutManager);
        grade_out_recyclerview.setAdapter(gradeOutAdapter);
    }
}
