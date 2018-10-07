package com.nenguou.dayuandaily.UI;

import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nenguou.dayuandaily.DataBase.DayuanDailyDatabase;
import com.nenguou.dayuandaily.Listeners.AppBarStateChangeListener;
import com.nenguou.dayuandaily.Model.RankModelDetial;
import com.nenguou.dayuandaily.R;
import com.nenguou.dayuandaily.Listener.RetrofitCallbackListener;
import com.nenguou.dayuandaily.Utils.RxDayuan;
import com.nenguou.dayuandaily.Utils.StatusBarUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Activity_Ranks extends AppCompatActivity {

    @BindView(R.id.ranks_toolbar) Toolbar ranks_toolbar;
    @BindView(R.id.ranks_appbarlayout) AppBarLayout ranks_appbarlayout;
    @BindView(R.id.ranks_cardview_show_name) CardView ranks_cardview_show_name;
    @BindView(R.id.ranks_cardview_show_gpas) CardView ranks_cardview_show_gpas;
    @BindView(R.id.ranks_card_name_name) TextView ranks_card_name_name;
    @BindView(R.id.ranks_card_name_majorname) TextView ranks_card_name_majorname;
    @BindView(R.id.ranks_card_name_classname) TextView ranks_card_name_classname;
    @BindView(R.id.ranks_card_name_studentnumber) TextView ranks_card_name_studentnumber;
    @BindView(R.id.ranks_card_gpas_gpa_value) TextView ranks_card_gpas_gpa_value;
    @BindView(R.id.ranks_gpas_jqcj_value) TextView ranks_gpas_jqcj_value;
    @BindView(R.id.ranks_card_gpas_average_grades_value) TextView ranks_card_gpas_average_grades_value;
    @BindView(R.id.ranks_card_bjpm_value1) TextView ranks_card_bjpm_value1;
    @BindView(R.id.ranks_card_bjpm_value2) TextView ranks_card_bjpm_value2;
    @BindView(R.id.ranks_card_zypm_value1) TextView ranks_card_zypm_value1;
    @BindView(R.id.ranks_card_zypm_value2) TextView ranks_card_zypm_value2;
    @BindView(R.id.ranks_card_gpa_bjpm_value1) TextView ranks_card_gpa_bjpm_value1;
    @BindView(R.id.ranks_card_gpa_bjpm_value2) TextView ranks_card_gpa_bjpm_value2;
    @BindView(R.id.ranks_card_gpa_zypm_value1) TextView ranks_card_gpa_zypm_value1;
    @BindView(R.id.ranks_card_gpa_zypm_value2) TextView ranks_card_gpa_zypm_value2;
    @BindView(R.id.ranks_card_average_grades_bjpm_value1) TextView ranks_card_average_grades_bjpm_value1;
    @BindView(R.id.ranks_card_average_grades_bjpm_value2) TextView ranks_card_average_grades_bjpm_value2;
    @BindView(R.id.ranks_card_average_grades_zypm_value1) TextView ranks_card_average_grades_zypm_value1;
    @BindView(R.id.ranks_card_average_grades_zypm_value2) TextView ranks_card_average_grades_zypm_value2;
    @BindView(R.id.rank_last_time_value) TextView rank_last_time_value;
    @BindView(R.id.ranks_bg) ImageView ranks_bg;

    @BindView(R.id.ranks_refresh) ImageView ranks_refresh;

    RxDayuan rxDayuan;
    DayuanDailyDatabase dayuanDailyDatabase;
    SharedPreferences sharedPreferences;
    List<TextView> textViews;
    List<TextView> textViews2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.myAppTheme);
        setContentView(R.layout.activity__ranks);
        ButterKnife.bind(this);
        StatusBarUtil.transparentStatusbar(this);
        initBg();
        initLists();
        initDatas();
        initViews();
        setListener();
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    try {
                        Picasso.with(Activity_Ranks.this)
                                .load("https://api.lylares.com/bing/image/?400/240/0")
                                .into(ranks_bg);
                    }catch (Exception e){
                        e.printStackTrace();
                        Toast.makeText(Activity_Ranks.this,"背景图片加载失败",Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };
    private void initBg() {
        Toast.makeText(this,"若要加载最新数据，请点击右上角的刷新按钮。",Toast.LENGTH_SHORT).show();
        Message message = new Message();
        message.what = 0;
        handler.sendMessage(message);
    }

    private void initLists() {
        textViews = new ArrayList<>();
        textViews2 = new ArrayList<>();
        textViews.add(ranks_card_name_name);
        textViews.add(ranks_card_name_majorname);
        textViews.add(ranks_card_name_classname);
        textViews.add(ranks_card_name_studentnumber);
        textViews.add(ranks_card_gpas_gpa_value);
        textViews.add(ranks_gpas_jqcj_value);
        textViews.add(ranks_card_gpas_average_grades_value);
        textViews2.add(ranks_card_bjpm_value1);
        textViews2.add(ranks_card_bjpm_value2);
        textViews2.add(ranks_card_zypm_value1);
        textViews2.add(ranks_card_zypm_value2);
        textViews2.add(ranks_card_gpa_bjpm_value1);
        textViews2.add(ranks_card_gpa_bjpm_value2);
        textViews2.add(ranks_card_gpa_zypm_value1);
        textViews2.add(ranks_card_gpa_zypm_value2);
        textViews2.add(ranks_card_average_grades_bjpm_value1);
        textViews2.add(ranks_card_average_grades_bjpm_value2);
        textViews2.add(ranks_card_average_grades_zypm_value1);
        textViews2.add(ranks_card_average_grades_zypm_value2);
        textViews.add(rank_last_time_value);
    }

    private void setTextBecomeSquare(){
        ranks_card_name_name.setText("██");
        ranks_card_name_majorname.setText("████");
        ranks_card_name_classname.setText("████");
        ranks_card_name_studentnumber.setText("████");
        ranks_card_gpas_gpa_value.setText("█");
        ranks_gpas_jqcj_value.setText("█");
        ranks_card_gpas_average_grades_value.setText("█");
        ranks_card_bjpm_value1.setText("█");
        ranks_card_bjpm_value2.setText("█");
        ranks_card_zypm_value1.setText("█");
        ranks_card_zypm_value2.setText("█");
        ranks_card_gpa_bjpm_value1.setText("█");
        ranks_card_gpa_bjpm_value2.setText("█");
        ranks_card_gpa_zypm_value1.setText("█");
        ranks_card_gpa_zypm_value2.setText("█");
        ranks_card_average_grades_bjpm_value1.setText("█");
        ranks_card_average_grades_bjpm_value2.setText("█");
        ranks_card_average_grades_zypm_value1.setText("█");
        ranks_card_average_grades_zypm_value2.setText("█");
        rank_last_time_value.setText("████");
    }

    private void initDatas() {
        for(TextView textView : textViews){
            textView.setTextColor(getResources().getColor(R.color.colorDeepBlackText));
        }
        for(TextView textView : textViews2){
            textView.setTextColor(getResources().getColor(R.color.colorWhite));
        }
        rxDayuan = new RxDayuan(this);
        dayuanDailyDatabase = DayuanDailyDatabase.getInstance(this);
        sharedPreferences = getSharedPreferences("User_grades",MODE_PRIVATE);
        String username = sharedPreferences.getString("username","2016006328");
        RankModelDetial rankModelDetial = dayuanDailyDatabase.getRank(Integer.parseInt(username));
        if (null != rankModelDetial){
            ranks_card_name_name.setText(rankModelDetial.getXm());
            ranks_card_name_majorname.setText(rankModelDetial.getZym());
            ranks_card_name_classname.setText(rankModelDetial.getBjh());
            ranks_card_name_studentnumber.setText(rankModelDetial.getXh());
            ranks_card_gpas_gpa_value.setText(rankModelDetial.getPjxfjd());
            ranks_gpas_jqcj_value.setText(rankModelDetial.getJqxfcj());
            ranks_card_gpas_average_grades_value.setText(rankModelDetial.getPjcj());
            ranks_card_bjpm_value1.setText(rankModelDetial.getJqbjpm());
            ranks_card_bjpm_value2.setText(rankModelDetial.getBjrs());
            ranks_card_zypm_value1.setText(rankModelDetial.getJqzypm());
            ranks_card_zypm_value2.setText(rankModelDetial.getZyrs());
            ranks_card_gpa_bjpm_value1.setText(rankModelDetial.getGpabjpm());
            ranks_card_gpa_bjpm_value2.setText(rankModelDetial.getBjrs());
            ranks_card_gpa_zypm_value1.setText(rankModelDetial.getGpazypm());
            ranks_card_gpa_zypm_value2.setText(rankModelDetial.getZyrs());
            ranks_card_average_grades_bjpm_value1.setText(rankModelDetial.getPjcjbjpm());
            ranks_card_average_grades_bjpm_value2.setText(rankModelDetial.getBjrs());
            ranks_card_average_grades_zypm_value1.setText(rankModelDetial.getPjcjzypm());
            ranks_card_average_grades_zypm_value2.setText(rankModelDetial.getZyrs());
            rank_last_time_value.setText(rankModelDetial.getTjsj());
        }
    }

    private void setListener() {
        ranks_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(TextView textView: textViews) {
                    textView.setTextColor(getResources().getColor(R.color.colorMoreDeepBlaceText2));
                }
                for(TextView textView: textViews2) {
                    textView.setTextColor(getResources().getColor(R.color.colorMoreDeepBlaceText2));
                }
                setTextBecomeSquare();
                sharedPreferences = getSharedPreferences("User_grades",MODE_PRIVATE);
                String username = sharedPreferences.getString("username","2016006328");
                String password = sharedPreferences.getString("password","171425");
                //Toast.makeText(Activity_Ranks.this,"正在加载数据，请稍等...",Toast.LENGTH_SHORT).show();
                Toast.makeText(Activity_Ranks.this,"正在加载数据，请耐心等待...",Toast.LENGTH_SHORT).show();

                rxDayuan.rankLogin(username, password, new RetrofitCallbackListener() {
                    @Override
                    public void onFinish(int status) {
                      //  Log.d("tatata","1111");

                        if(status == 0){
                            initDatas();
                            //T//oast.makeText(Activity_Ranks.this,"已刷新为最新数据！",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Exception e) {
                        //Log.d("tatata","2222");

                    }

                    @Override
                    public void setText(String msg) {
                        //Log.d("tatata","3333");

                        //Toast.makeText(MainActivity.this,msg,Toast.LENGTH_LONG).show();
                    }
                });
                //wLog.d("tatata","4444");
            }
        });
        ranks_appbarlayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                if(state == State.IDLE){    // 变换状态


                }
                if(state == State.COLLAPSED){   // 折叠状态
                    ranks_cardview_show_gpas.setVisibility(View.GONE);
                    ranks_cardview_show_name.setVisibility(View.GONE);
                }
                if(state == State.EXPANDED){    // 展开状态
                    ranks_cardview_show_gpas.setVisibility(View.VISIBLE);
                    ranks_cardview_show_name.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void initViews() {
        ranks_toolbar.setNavigationIcon(R.mipmap.back_bg);
        ranks_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Activity_Ranks.this.finish();
            }
        });
    }
}
