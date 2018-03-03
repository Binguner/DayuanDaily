package com.nenguou.dayuandaily.Adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.nenguou.dayuandaily.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by binguner on 2018/2/26.
 */

public class GradeInnerViewHolder extends BaseViewHolder {

    //@BindView(R.id.grade_detial_name)
    public TextView grade_detial_name;
    //@BindView(R.id.grade_detial_credit)
    public TextView grade_detial_credit;
    //@BindView(R.id.grade_detial_grade)
    public TextView grade_detial_grade;
    public ImageView grade_detial_shadow;
    public GradeInnerViewHolder(View view) {
        super(view);
        //ButterKnife.bind(this,view);
        grade_detial_name = view.findViewById(R.id.grade_detial_name);
        grade_detial_credit = view.findViewById(R.id.grade_detial_credit);
        grade_detial_grade = view.findViewById(R.id.grade_detial_grade);
        grade_detial_shadow = view.findViewById(R.id.grade_detial_shadow);
    }
}
