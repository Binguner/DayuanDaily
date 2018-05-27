package com.nenguou.dayuandaily.Adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.nenguou.dayuandaily.Model.Account;
import com.nenguou.dayuandaily.R;

import java.util.List;

public class AccountAdapter extends BaseQuickAdapter<Account,AccountViewHolder> {

    public AccountAdapter(int layoutResId, @Nullable List<Account> data) {
        super(layoutResId, data);

    }

    @Override
    protected void convert(AccountViewHolder helper, Account item) {
        helper.account_name_value.setText(item.getStudentName());
        helper.account_studentnumber_value.setText(item.getStudentNumber());
    }
}

class AccountViewHolder extends BaseViewHolder{

    TextView account_name_value;
    TextView account_studentnumber_value;
    public AccountViewHolder(View view) {
        super(view);
        account_name_value = view.findViewById(R.id.account_name_value);
        account_studentnumber_value = view.findViewById(R.id.account_studentnumber_value);
    }
}
