package com.nenguou.dayuandaily.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.nenguou.dayuandaily.Adapter.AccountAdapter;
import com.nenguou.dayuandaily.R;
import com.nenguou.dayuandaily.Utils.StatusBarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Activity_account extends AppCompatActivity {

    @BindView(R.id.account_toolbar) Toolbar account_toolbar;
    @BindView(R.id.accout_recyclerview) RecyclerView accout_recyclerview;
    AccountAdapter accountAdapter;
    LinearLayoutManager linearLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        ButterKnife.bind(this);
        StatusBarUtil.setStatusBarColor(this,R.color.colorToolbar);
        initViews();
        initDatas();

    }

    private void initDatas() {

    }

    private void initViews() {
        account_toolbar.setNavigationIcon(R.mipmap.back_bg);
        account_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Activity_account.this.finish();
            }
        });
    }
}
