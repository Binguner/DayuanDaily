package com.nenguou.dayuandaily.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.nenguou.dayuandaily.DataBase.DayuanDailyDatabase;
import com.nenguou.dayuandaily.Model.Grades;
import com.nenguou.dayuandaily.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by binguner on 2018/2/26.
 */

public class GradeOutViewHolder extends BaseViewHolder {

    //@BindView(R.id.grade_name) TextView grade_name;
    public TextView grade_name;
    public RecyclerView grade_innner_recyclerview;
    //Context context;
//    GradeInnerAdapter adapter;
//    RecyclerView.LayoutManager layoutManager;
//    DayuanDailyDatabase dayuanDailyDatabase;
//    SharedPreferences sharedPreferences;
    public GradeOutViewHolder(View view) {
        super(view);
        //this.context = context;
        // ButterKnife.bind(this,view);
        grade_name = view.findViewById(R.id.grade_name);

        //sharedPreferences = context.getSharedPreferences("User_grades",Context.MODE_PRIVATE);
        //String studentNumber = sharedPreferences.getString("username","");
        //dayuanDailyDatabase = DayuanDailyDatabase.getInstance(context);
        grade_innner_recyclerview = view.findViewById(R.id.grade_innner_recyclerview);
        //layoutManager = new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false);
        //List<Grades.DataBean.GradesBean> gradesBeans = dayuanDailyDatabase.getTermDetial(studentNumber,grade_name.getText().toString());
        //adapter = new GradeInnerAdapter(R.layout.grade_detial_layout,gradesBeans);

        //grade_innner_recyclerview.setLayoutManager(layoutManager);
        //grade_innner_recyclerview.setAdapter(adapter);

    }
}
