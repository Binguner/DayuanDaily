package com.nenguou.dayuandaily.Broadcast;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

/**
 * Created by binguner on 2018/2/23.
 */

public class PleaseWaitBroadcast extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
        builder.setTitle("正在拼命加载");
        builder.setMessage("请稍等");
        builder.setCancelable(false);
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
    }
}
