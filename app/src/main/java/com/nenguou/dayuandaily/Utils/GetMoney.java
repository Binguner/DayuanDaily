package com.nenguou.dayuandaily.Utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.widget.Toast;

import com.nenguou.dayuandaily.MainActivity;

import static android.content.Context.CLIPBOARD_SERVICE;

public class GetMoney {

    //private static Context context;
    //private static ApplicationInfo applicationInfo;
    //private static SharedPreferences.Editor editor;

    /**
     * user: String l,z
     * time:
     */

    public static void openAliBaba(Context context) {
        ApplicationInfo applicationInfo;
        SharedPreferences.Editor editor;
        SharedPreferences sharedPreferences;
        boolean isExitThisApp = true;
        try {
            applicationInfo = context.getPackageManager().getApplicationInfo("com.eg.android.AlipayGphone", PackageManager.GET_UNINSTALLED_PACKAGES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            //Snackbar.make(,"尚未安装支付宝",Snackbar.LENGTH_SHORT).show();
            Toast.makeText(context, "尚未安装支付宝", Toast.LENGTH_SHORT).show();
            isExitThisApp = false;
        }
        //if (null != applicationInfo) {
        if (isExitThisApp) {
            ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);
            editor = context.getSharedPreferences("test", context.MODE_PRIVATE).edit();
            sharedPreferences = context.getSharedPreferences("test", context.MODE_PRIVATE);
            String user = sharedPreferences.getString("user", "z");
            if (user.equals("l")) {
                clipboardManager.setPrimaryClip(new ClipData(ClipData.newPlainText("Label", "快来领支付宝红包！人人可领，天天可领！复制此消息，打开最新版支付宝就能领取！RXaFYw62U8")));
                editor.putString("user", "z");
                editor.commit();
            } else {
                clipboardManager.setPrimaryClip(new ClipData(ClipData.newPlainText("Label", "快来领支付宝红包！人人可领，天天可领！复制此消息，打开最新版支付宝就能领取！2FnQKo55L9")));
                editor.putString("user", "l");
                editor.commit();
            }
            PackageManager packageManager = context.getApplicationContext().getPackageManager();
            Intent intent = packageManager.getLaunchIntentForPackage("com.eg.android.AlipayGphone"/*"com.alipay.android.phone.wallet.sharetoken.ui.TokenDecodeActivity"*/);

            try {
                context.startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }

//            openAlbabaWebView.getSettings().setAppCacheEnabled(true);
//            openAlbabaWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
//            openAlbabaWebView.getSettings().setDomStorageEnabled(true);
//            openAlbabaWebView.loadUrl("http://qr.alipay.com/c1x089016ue4yr5wykokc9f");


        }
    }
}
