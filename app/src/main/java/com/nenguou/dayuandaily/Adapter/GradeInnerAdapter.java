package com.nenguou.dayuandaily.Adapter;

import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.nenguou.dayuandaily.Model.Grades;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by binguner on 2018/2/26.
 */

public class GradeInnerAdapter extends BaseQuickAdapter<Grades.DataBean.GradesBean,GradeInnerViewHolder> {

    private String Tag = "GradeInnerAdapterTag";
    private List<Grades.DataBean.GradesBean> gradesBeans = new ArrayList<>();
    public GradeInnerAdapter(int layoutResId, @Nullable List<Grades.DataBean.GradesBean> data) {
        super(layoutResId, data);
        this.gradesBeans = data;
    }

    @Override
    protected void convert(GradeInnerViewHolder helper, Grades.DataBean.GradesBean item) {
        int positon = helper.getLayoutPosition();
        helper.grade_detial_shadow.setVisibility(View.VISIBLE);
        helper.grade_detial_name.setText(gradesBeans.get(positon).getClassName());
        helper.grade_detial_credit.setText(gradesBeans.get(positon).getCredit());
        helper.grade_detial_grade.setText(gradesBeans.get(positon).getGrade());
        if(positon == 0){
            //Log.d(Tag,gradesBeans.size()+"个   " +"positon = "+positon);
            helper.grade_detial_shadow.setVisibility(View.INVISIBLE);
        }
    }
}