package com.nenguou.dayuandaily.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.nenguou.dayuandaily.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityGradesDetial extends AppCompatActivity {

    @BindView(R.id.grade_innner_recyclerview) RecyclerView grade_innner_recyclerview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grades_detial);
        ButterKnife.bind(this);
    }
}
