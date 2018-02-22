package com.nenguou.dayuandaily.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.nenguou.dayuandaily.R;

import butterknife.ButterKnife;

public class ActivityScheduler extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.myAppTheme);
        setContentView(R.layout.activity_scheduler);
        ButterKnife.bind(this);
    }
}
