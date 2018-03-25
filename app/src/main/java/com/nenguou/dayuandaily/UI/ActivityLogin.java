package com.nenguou.dayuandaily.UI;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.nenguou.dayuandaily.R;
import com.nenguou.dayuandaily.Utils.RetrofitCallbackListener;
import com.nenguou.dayuandaily.Utils.RxDayuan;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ActivityLogin extends AppCompatActivity {

    /**
     * SharedPreferences: User_grades
     * captchaUrl : String /captcha/5/88e0ddc6-8edd-4467-be8d-78c999e50dd9.jpg
     * cookies : String 879c06766d4648d59b95a40f26ae91b9
     * isLoadedData: true
     * username : String 2016001000
     * password : String 101010
     * captcha : String qwer
     * sessionId : String = cookies
     */

    @BindView(R.id.username_textinputlayout) TextInputLayout username_textinputlayout;
    @BindView(R.id.password_textinputlayout) TextInputLayout password_textinputlayout;
    @BindView(R.id.code_textinputlayout) TextInputLayout code_textinputlayout;
    @BindView(R.id.ed_username) EditText ed_username;
    @BindView(R.id.ed_password) EditText ed_password;
    @BindView(R.id.ed_code) EditText ed_code;
    @BindView(R.id.loginbtn) Button loginbtn;
    @BindView(R.id.canclebtn) Button canclebtn;
    @BindView(R.id.login_findMe) TextView login_findMe;
    @BindView(R.id.aty_login_captcha_pic) ImageView aty_login_captcha_pic;
    String fromWhere;
    RxDayuan rxDayuan;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    private static final String loginTag = "AtyLonginTag";
    private static final int GET_CAPTCHA = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.myAppTheme_login);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        rxDayuan = new RxDayuan(this);
        sharedPreferences = getSharedPreferences("User_grades",MODE_PRIVATE);
        checkIfLoadedGrades();
        checkFromWhere();
        editor = getSharedPreferences("User_grades",MODE_PRIVATE).edit();
        transparentStatus();
        initViews();
        setListener();
    }

    private void checkFromWhere() {
        Intent intent = getIntent();
        fromWhere = intent.getStringExtra("fromWhere");
    }

    private void checkIfLoadedGrades() {
        if(sharedPreferences.getBoolean("isLoadedData",false)){
            Intent intent = new Intent(this,ActivityGrades.class);
            startActivity(intent);
            ActivityLogin.this.finish();
        }
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case GET_CAPTCHA:
                    String imageUrl = (String) msg.obj;
                    //Log.d(loginTag,"in Handler: "+imageUrl);
                    try {
                        Picasso.with(ActivityLogin.this)
                                .load(imageUrl)
                                //.placeholder(getResources().getColor(R.color.colorBackground))
                                //.error(getResources().getColor(R.color.colorBackground))
                                .into(aty_login_captcha_pic);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    //Log.d(loginTag,"over Handler ");
                    break;
            }
        }
    };

    private void loadCaptcha(){
        rxDayuan.getCaptcha(new RetrofitCallbackListener() {
            @Override
            public void onFinish(int status) {
                if(status == 0){
                    String imageUrl = "http://grade.liuyinxin.com/univ/login" + sharedPreferences.getString("captchaUrl","");
                    Message message = new Message();
                    message.what = GET_CAPTCHA;
                    message.obj = imageUrl;
                    handler.sendMessage(message);
                }else if(status == 1){
                    Toast.makeText(ActivityLogin.this,"请重新点击验证码",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Exception e) {

            }
        });

        //Log.d(loginTag,imageUrl+"");
    }

    private void setListener() {
        ed_username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().isEmpty()){
                    username_textinputlayout.setErrorEnabled(false);
                    //editor.putString("username",charSequence.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        ed_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().isEmpty()){
                    password_textinputlayout.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        ed_code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().isEmpty()){
                    code_textinputlayout.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void initViews() {
        aty_login_captcha_pic = findViewById(R.id.aty_login_captcha_pic);
        username_textinputlayout.setHint("Username");
        ed_username.setTextColor(getResources().getColor(R.color.colorWhite));
        password_textinputlayout.setHint("Password");
        loadCaptcha();
        //ed_code.draw
    }

    private void transparentStatus() {
        if(Build.VERSION.SDK_INT >= 21){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }



    @OnClick(R.id.aty_login_captcha_pic)
    public void get_CaptchaAgain(View view){
        loadCaptcha();
    }

    @OnClick(R.id.loginbtn)
    public void loginBtnClick(View view){
        if(username_textinputlayout.getEditText().getText().toString().isEmpty()){
            username_textinputlayout.setError("请输入学号");
        }else {
            username_textinputlayout.setErrorEnabled(false);
            editor.putString("username",username_textinputlayout.getEditText().getText().toString());
            Log.d(loginTag,"UserName is : "+ username_textinputlayout.getEditText().getText().toString());
        }
        if(password_textinputlayout.getEditText().getText().toString().isEmpty()){
            password_textinputlayout.setError("请输入密码");
        }else {
            password_textinputlayout.setErrorEnabled(false);
            editor.putString("password",password_textinputlayout.getEditText().getText().toString());
            Log.d(loginTag,"password is : "+ password_textinputlayout.getEditText().getText().toString());
        }
        if(code_textinputlayout.getEditText().getText().toString().isEmpty()){
            code_textinputlayout.setError("请输入验证码");
        }else {
            code_textinputlayout.setErrorEnabled(false);
            editor.putString("captcha",code_textinputlayout.getEditText().getText().toString());
            Log.d(loginTag,"captcha is : "+ code_textinputlayout.getEditText().getText().toString());
        }
        editor.commit();
        loginbtn.setClickable(false);
        Toast.makeText(this,"正在加载，请稍后!",Toast.LENGTH_SHORT).show();
        if(!username_textinputlayout.getEditText().getText().toString().isEmpty() && !password_textinputlayout.getEditText().getText().toString().isEmpty()
                && !code_textinputlayout.getEditText().getText().toString().isEmpty()) {
            rxDayuan.getLoginSuccess(new RetrofitCallbackListener() {
                @Override
                public void onFinish(int status) {
                    rxDayuan.getGrades(new RetrofitCallbackListener() {
                        @Override
                        public void onFinish(int status) {
                            if (status == 0) {
                                editor.putBoolean("isLoadedData", true);
                                editor.commit();
                                if(null!=fromWhere&&fromWhere.equals("MainActivity")){
                                    ActivityLogin.this.finish();
                                }else {
                                    Intent intent = new Intent(ActivityLogin.this, ActivityGrades.class);
                                    startActivity(intent);
                                    loginbtn.setClickable(true);
                                    ActivityLogin.this.finish();
                                }
                            }
                            if (status == 1) {
                                Toast.makeText(ActivityLogin.this, "网络异常，请重试", Toast.LENGTH_SHORT).show();
                                loginbtn.setClickable(true);
                            }
                        }

                        @Override
                        public void onError(Exception e) {
                            Toast.makeText(ActivityLogin.this, e.toString() , Toast.LENGTH_SHORT).show();
                            loginbtn.setClickable(true);
                        }
                    });

                }

                @Override
                public void onError(Exception e) {
                    loginbtn.setClickable(true);
                    Toast.makeText(ActivityLogin.this, e.toString(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(ActivityLogin.this, "登陆失败，请重试", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @OnClick(R.id.ed_code)
    public void ed_code_login(final View view){
        ed_code.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
                loginBtnClick(view);
                return true;
            }
        });
    }

    @OnClick(R.id.canclebtn)
    public void anclebtnBack(View view){
        onBackPressed();
        this.finish();
    }

    @OnClick(R.id.login_findMe)
    void findMe(View v){
        openQQ(v,"819985138");
    }

    private void openQQ(View view,@Nullable String qqNum){
        if(checkAppExist(this,"com.tencent.mobileqq")){
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("mqqwpa://im/chat?chat_type=wpa&uin="+qqNum+"&version=1")));
        }else if (checkAppExist(this,"com.tencent.tim")){
            startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("mqqwpa://im/chat?chat_type=wpa&uin="+qqNum+"&version=1")));
        }else {
            Snackbar.make(view,"未安装QQ",Snackbar.LENGTH_SHORT).show();
        }
    }

    private boolean checkAppExist(Context context, String packageName){
        if(packageName == null || "".equals(packageName)){
            return false;
        }try{
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

}
