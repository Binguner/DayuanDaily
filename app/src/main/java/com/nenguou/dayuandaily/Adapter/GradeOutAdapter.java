package com.nenguou.dayuandaily.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.nenguou.dayuandaily.DataBase.DayuanDailyDatabase;
import com.nenguou.dayuandaily.Model.Grades;
import com.nenguou.dayuandaily.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by binguner on 2018/2/26.
 */

public class GradeOutAdapter extends BaseQuickAdapter<String,GradeOutViewHolder> {

    List<String> termNameArray = new ArrayList<>();

    GradeInnerAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    DayuanDailyDatabase dayuanDailyDatabase;
    SharedPreferences sharedPreferences;
    List<Grades.DataBean.GradesBean> gradesBeans;
    Context context;
    String studentNumber;
    private static final String AdapterTag = "GradeOutAdaptetTag";

    public GradeOutAdapter(Context context,int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
        this.termNameArray = data;
        this.context = context;
        initDatas();
    }

    private void initDatas() {
        sharedPreferences = context.getSharedPreferences("User_grades",Context.MODE_PRIVATE);
        studentNumber = sharedPreferences.getString("username","");

        dayuanDailyDatabase = DayuanDailyDatabase.getInstance(context);
    }

    @Override
    protected void convert(GradeOutViewHolder helper, String item) {
        int postion = helper.getLayoutPosition();
        helper.grade_name.setText(termNameArray.get(postion).toString());

        gradesBeans = dayuanDailyDatabase.getTermDetial(studentNumber,termNameArray.get(postion).toString());
        for(Grades.DataBean.GradesBean gradesBean:gradesBeans){
            Log.d(AdapterTag,"ClassName: "+gradesBean.getClassName());
        }

        adapter = new GradeInnerAdapter(R.layout.grade_detial_layout,gradesBeans);
        layoutManager = new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
        layoutManager.setAutoMeasureEnabled(true);

        helper.grade_innner_recyclerview.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,true));
        helper.grade_innner_recyclerview.setAdapter(adapter);

//        gradesBeans.clear();
    }


//    static class GradeOutViewHolder extends BaseViewHolder{
//
//        @BindView(R.id.grade_name) TextView grade_name;
//        public GradeOutViewHolder(View view) {
//            super(view);
//            ButterKnife.bind(this,view);
//        }
//    }
}
