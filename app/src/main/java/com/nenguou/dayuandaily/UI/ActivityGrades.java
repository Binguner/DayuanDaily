package com.nenguou.dayuandaily.UI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.nenguou.dayuandaily.Adapter.GradeOutAdapter;
import com.nenguou.dayuandaily.DataBase.DayuanDailyDatabase;
import com.nenguou.dayuandaily.Listener.CallbackListener;
import com.nenguou.dayuandaily.R;
import com.nenguou.dayuandaily.Listener.RetrofitCallbackListener;
import com.nenguou.dayuandaily.Utils.RxDayuan;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ActivityGrades extends AppCompatActivity {

    @BindView(R.id.grade_out_recyclerview) RecyclerView grade_out_recyclerview;
    @BindView(R.id.grades_toolbar) Toolbar grades_toolbar;
    @BindView(R.id.grades_refresh) ImageView grades_refresh;
    ImageView cap_pic;

    private GradeOutAdapter gradeOutAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private static final int GET_CAPTCHA = 0;
    DayuanDailyDatabase dayuanDailyDatabase;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    DayuanDailyDatabase sqLiteDatabase;
    RxDayuan rxDayuan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.myAppTheme);
        setContentView(R.layout.activity_grades);
        ButterKnife.bind(this);
        showTips();
        initData();
        initViews();
    }

    private void showTips() {
        Toast.makeText(this,"若要获取最新数据，请点击右上角的刷新。",Toast.LENGTH_SHORT).show();
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
                        Picasso.with(ActivityGrades.this)
                                .load(imageUrl)
                                //.placeholder(getResources().getColor(R.color.colorBackground))
                                //.error(getResources().getColor(R.color.colorBackground))
                                .into(cap_pic);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_grades,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @OnClick(R.id.grades_refresh)
    public void refreshGrades(View view){
       /*
        PopupMenu popupMenu = new PopupMenu(this,view);
        MenuInflater menuInflater  = popupMenu.getMenuInflater();
        menuInflater.inflate(R.menu.menu_grades,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.menu_grades_refresh:
                        //Toast.makeText(ActivityGrades.this,"refresh",Toast.LENGTH_SHORT).show();
                        sqLiteDatabase.dropAndCreateTableGrades();
                        showRefreshGradesAlerDialog();
                        break;
                    case R.id.menu_grades_reLoging:
                        //Toast.makeText(ActivityGrades.this,"Login",Toast.LENGTH_SHORT).show();
                        editor.putBoolean("isLoadedData",false);
                        editor.commit();
                        Intent intent = new Intent(ActivityGrades.this,ActivityLogin.class);
                        intent.putExtra("fromWhere","ActivityGrades2Login");
                        startActivity(intent);
                        ActivityGrades.this.finish();
                        break;
                }
                return false;
            }
        });
        popupMenu.show();*/

        Toast.makeText(ActivityGrades.this,"正在努力刷新，请稍后...",Toast.LENGTH_SHORT).show();
        rxDayuan.getGrades2(new CallbackListener() {
            @Override
            public void callBack(int status, @NotNull String msg) {
                Toast.makeText(ActivityGrades.this,msg,Toast.LENGTH_SHORT).show();
                if (status == 1){
                    Intent intent = new Intent(ActivityGrades.this,ActivityGrades.class);
                    startActivity(intent);
                    ActivityGrades.this.finish();
                }
            }
        });
        //sqLiteDatabase.dropAndCreateTableGrades();
        //showRefreshGradesAlerDialog();
    }

    private void showRefreshGradesAlerDialog() {
        rxDayuan.getCaptcha(new RetrofitCallbackListener() {
            @Override
            public void onFinish(int status) {
                if(status == 0){
                    String imageUrl = "https://grade.liuyinxin.com/univ/login" + sharedPreferences.getString("captchaUrl","");
                    Message message = new Message();
                    message.what = GET_CAPTCHA;
                    message.obj = imageUrl;
                    handler.sendMessage(message);
                }else if(status == 1){
                    Toast.makeText(ActivityGrades.this,"请重新点击验证码",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Exception e) {

            }

            @Override
            public void setText(String msg) {

            }
        });
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.pop_refresh_grades,null);
        final EditText input_cap = view.findViewById(R.id.input_cap);
        cap_pic = view.findViewById(R.id.cap_pic);
        TextView pop_refresh_grades_classNumber = view.findViewById(R.id.pop_refresh_grades_classNumber);
        TextView cap_refresh_cancle = view.findViewById(R.id.cap_refresh_cancle);
        TextView cap_refresh_ok = view.findViewById(R.id.cap_refresh_ok);
        CardView cap_refresh_cardview = view.findViewById(R.id.cap_refresh_cardview);

        pop_refresh_grades_classNumber.setText(sharedPreferences.getString("username","请重新登陆"));

        builder.setView(view);
        final AlertDialog dialog = builder.show();

        cap_refresh_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }

        });
        cap_refresh_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ActivityGrades.this,"正在加载，请稍等...",Toast.LENGTH_SHORT).show();
                editor.putString("captcha",input_cap.getText().toString());
                editor.commit();
                rxDayuan.getLoginSuccess(new RetrofitCallbackListener() {
                    @Override
                    public void onFinish(int status) {
                        rxDayuan.getGrades(new RetrofitCallbackListener() {
                            @Override
                            public void onFinish(int status) {
                                if (status == 0){
                                    dialog.dismiss();
                                    try {
                                        Toast.makeText(ActivityGrades.this,"加载成功！",Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(ActivityGrades.this,ActivityGrades.class);
                                        startActivity(intent);
                                        ActivityGrades.this.finish();
                                    }catch (Exception e){
                                        e.printStackTrace();
                                        Toast.makeText(ActivityGrades.this,"更新失败，请重试",Toast.LENGTH_SHORT).show();
                                    }
                                }
                                if (status == 1){
                                    Toast.makeText(ActivityGrades.this,"网络异常，请重试",Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onError(Exception e) {

                            }

                            @Override
                            public void setText(String msg) {

                            }
                        });
                    }

                    @Override
                    public void onError(Exception e) {

                    }

                    @Override
                    public void setText(String msg) {

                    }
                });
            }
        });

        cap_refresh_cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        window.setWindowAnimations(R.style.Theme_AppCompat_Dialog_Alert);
    }

    private void initViews() {
        grades_toolbar.setNavigationIcon(R.mipmap.back_bg);
        grades_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                ActivityGrades.this.finish();
            }
        });


    }


    private void initData() {

        rxDayuan = new RxDayuan(this);
        sqLiteDatabase = DayuanDailyDatabase.getInstance(this);
        sharedPreferences = getSharedPreferences("User_grades",MODE_PRIVATE);
        editor = getSharedPreferences("User_grades",MODE_PRIVATE).edit();
        String studentNumber = sharedPreferences.getString("username","");
        dayuanDailyDatabase = DayuanDailyDatabase.getInstance(this);
        List<String> termNameArray = null;

        if(!dayuanDailyDatabase.isExitThieGrade(studentNumber)){
            rxDayuan.getGrades2(new CallbackListener() {
                @Override
                public void callBack(int status, @NotNull String msg) {
                    Toast.makeText(ActivityGrades.this,msg,Toast.LENGTH_SHORT).show();
                }
            });
        }

        try {
            termNameArray = dayuanDailyDatabase.getTermName(studentNumber);
        } catch (Exception e) {
            e.printStackTrace();
        }

        layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        gradeOutAdapter = new GradeOutAdapter(this,R.layout.activity_grades_detial,termNameArray);

        grade_out_recyclerview.setLayoutManager(layoutManager);
        grade_out_recyclerview.setAdapter(gradeOutAdapter);
    }
}