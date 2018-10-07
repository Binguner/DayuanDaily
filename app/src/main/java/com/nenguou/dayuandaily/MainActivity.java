package com.nenguou.dayuandaily;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.nenguou.dayuandaily.DataBase.DayuanDailyDatabase;
import com.nenguou.dayuandaily.ImageLoader.MyImageLoader;
import com.nenguou.dayuandaily.UI.ActivityLogin;
import com.nenguou.dayuandaily.UI.ActivityScheduler;
import com.nenguou.dayuandaily.UI.Activity_Ranks;
import com.nenguou.dayuandaily.Listener.RetrofitCallbackListener;
import com.nenguou.dayuandaily.Utils.RxDayuan;
import com.nenguou.dayuandaily.Utils.StatusBarUtil;
import com.squareup.picasso.Picasso;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements  OnBannerListener {

    private Button test_YearCollege,test_Major,test_classname,test_Class,test_loadYearColleg,test_loadMajor,test_loadClassName,test_loadClass,
            go_to_choose_class_aty,test_login,test_getCaptcha,openAliPay,check_grades,check_schedule,get_money,oneKeyTestTeatch,get_rank_btn;
    List<Uri> imageUrls;

    RxDayuan rxDayuan;
    @BindView(R.id.main_banner) Banner main_banner;
    @BindView(R.id.main_fab_menu) FloatingActionMenu main_fab_menu;
    ImageView cap_pic;
    private final String mainTag = "MainActivityTag";
    SharedPreferences.Editor editor = null;
    SharedPreferences sharedPreferences = null;
    SharedPreferences sharedPreferences1 = null;    // 系统设置
    DayuanDailyDatabase dayuanDailyDatabase;
    private static final int GET_CAPTCHA = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(chooseTheFirstViews()){
           return;
        }
        setTheme(R.style.myAppTheme);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        StatusBarUtil.transparentStatusbar(this);
        initId();
        editor = getSharedPreferences("test",MODE_PRIVATE).edit();
        sharedPreferences = getSharedPreferences("test",MODE_PRIVATE);
        setListener();
        initBanner();
        initFloatingButton();
    }

    private void initFloatingButton() {
        main_fab_menu.addMenuButton(new FloatingActionButton(this));
        main_fab_menu.addMenuButton(new FloatingActionButton(this));
        main_fab_menu.addMenuButton(new FloatingActionButton(this));
    }

    private void initBanner() {
        imageUrls = new ArrayList<>();

        //imageView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        imageUrls.add(Uri.parse("https://api.lylares.com/bing/image/?400/240/-1"));
        imageUrls.add(Uri.parse("https://api.lylares.com/bing/image/?400/240/0"));
        imageUrls.add(Uri.parse("https://api.lylares.com/bing/image/?400/240/1"));
        imageUrls.add(Uri.parse("https://api.lylares.com/bing/image/?400/240/2"));

        main_banner.setImages(imageUrls)
                .setImageLoader(new MyImageLoader())
                .setOnBannerListener(this)
                .start();
        //imageUrls.add(imageView);
        // 设置 Banner 样式
        //main_banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);

        // 设置图片集合
        //main_banner.setImages(imageUrls);

        // 设置图片加载器
        //main_banner.setImageLoader(new MyImageLoader());

        //设置banner动画效果
        //main_banner.setBannerAnimation(Transformer.Default);

        //设置标题集合（当 banner 样式有显示title时）
        //main_banner.setBannerTitles(titles);

        // 设置点击事件
        //main_banner.setOnBannerListener(new OnBannerListener() {
            //@Override
            //public void OnBannerClick(int position) {
               // Log.d(mainTag,"Clicked " + position);
           // }
       // });

        // 设置是否允许手动滑动轮播图
        //main_banner.setViewPagerIsScroll(true);

        //设置自动轮播，默认为true
        //main_banner.isAutoPlay(true);

        //设置轮播时间
        //main_banner.setDelayTime(3000);

       /* Log.d(mainTag,"before");

        Log.d(mainTag,"after");*/

        //设置指示器位置（当 banner 模式中有指示器时）
        //main_banner.setIndicatorGravity(BannerConfig.RIGHT);
        //main_banner 设置方法全部调用完毕时最后调用
        //main_banner.start();
    }

    @Override
    public void OnBannerClick(int position) {
        Toast.makeText(getApplicationContext(),"You clicked " + position,Toast.LENGTH_SHORT).show();
    }


    private boolean chooseTheFirstViews() {
        sharedPreferences1 = PreferenceManager.getDefaultSharedPreferences(this);
        if(sharedPreferences1.getBoolean("firstGoToScheduler",false)){
            Intent intent = new Intent(this,ActivityScheduler.class);
            startActivity(intent);
            this.finish();
            return true;
        }
        return false;
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
                        Picasso.with(MainActivity.this)
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
    protected void onStart() {
        super.onStart();
        main_banner.startAutoPlay();
        Log.d(mainTag,"onStart");

    }

    @Override
    protected void onStop() {
        super.onStop();
        main_banner.stopAutoPlay();
        Log.d(mainTag,"onStop");

    }

    private void setListener() {
/*        test_YearCollege.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rxDayuan.getYearCollege(new RetrofitCallbackListener() {
                    @Override
                    public void onFinish(int status) {

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

        test_Major.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rxDayuan.getMajor(12);
            }
        });

        test_classname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rxDayuan.getClassName(2016,"工业设计");
            }
        });

        test_Class.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rxDayuan.getClass("软件1632","2017-2018-2-1");
            }
        });

        test_loadYearColleg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<YearCollege.DataBean.CollegesBean> list = dayuanDailyDatabase.loadYearCollege();
                for (YearCollege.DataBean.CollegesBean collegesBean : list) {
                    //Log.d(mainTag, "Id: "+collegesBean.getId() + "  College: " + collegesBean.getCollege());
                }
            }
        });

        test_loadMajor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Major.DataBean> list = dayuanDailyDatabase.loadMajor(12);
                for(Major.DataBean dataBean : list){
                    //Log.d(mainTag, "Id: "+dataBean.getId() + "  College_id : " + dataBean.getCollegeId()+"  Major: "+dataBean.getMajor());

                }
            }
        });

        test_loadClassName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<String> s = dayuanDailyDatabase.loadClassName(2016,"工业设计");
                for(String s1: s){
//                    Log.d(mainTag, "class: " + s1);
                }
            }
        });
        test_loadClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //String s = dayuanDailyDatabase.loadSchedules(DayuanDailyDatabase.TYPE_GET_SUB_NAME,2465,1,2);
                //Log.d(mainTag, "class_name is : " + s);
            }
        });
        go_to_choose_class_aty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ActivityScheduler.class);
                startActivity(intent);
            }
        });
        test_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ActivityLogin.class);
                startActivity(intent);
            }
        });
        test_getCaptcha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //rxDayuan.getCaptcha();
            }
        });*/

        /*openAliPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApplicationInfo applicationInfo = null;
                try {
                    applicationInfo = MainActivity.this.getPackageManager().getApplicationInfo("com.eg.android.AlipayGphone", PackageManager.GET_UNINSTALLED_PACKAGES);
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                    Snackbar.make(view,"尚未安装支付宝",Snackbar.LENGTH_SHORT).show();
                }
                if (null!=applicationInfo){
                    ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                    clipboardManager.setPrimaryClip(new ClipData(ClipData.newPlainText("Label","快来领支付宝红包！人人可领，天天可领！复制此消息，打开最新版支付宝就能领取！2FnQKo55L9")));
                    PackageManager packageManager = MainActivity.this.getApplicationContext().getPackageManager();
                    Intent intent = packageManager.getLaunchIntentForPackage("com.eg.android.AlipayGphone");
                    //List<ApplicationInfo> list = packageManager.getInstalledApplications(0);
                    //for(ApplicationInfo info:list){
                        //Log.d("FGHJNBGH",info.packageName);
                    //}
                    startActivity(intent);
                }
            }*/
        //});





        check_grades.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                long oldTime = sharedPreferences.getLong("time",0);
//                long newTIme = System.currentTimeMillis();
//                if((newTIme - oldTime) > 86400000){
//                    editor.putLong("time",newTIme);
//                    editor.commit();
//                    openAlibaba();
//                }else {
                    Intent intent = new Intent(MainActivity.this, ActivityLogin.class);
                    intent.putExtra("fromWhere","MainActivity2Grades");
                    startActivity(intent);
//                }

            }
        });
        check_schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                long oldTime = sharedPreferences.getLong("time",0);
//                long newTIme = System.currentTimeMillis();
//                if((newTIme - oldTime) > 86400000){
//                    editor.putLong("time",newTIme);
//                    editor.commit();
//                    openAlibaba();
//                }else {
                Intent intent = new Intent(MainActivity.this, ActivityScheduler.class);
                startActivity(intent);
//                }
            }
        });


        get_money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long oldTime = sharedPreferences.getLong("time",0);
                long newTIme = System.currentTimeMillis();
                //Log.d("QEWR","get out");

                //if((newTIme - oldTime) > 86400000) {
                  //  Log.d("QEWR","get in");


                    editor.putLong("time", newTIme);
                    editor.commit();
                    openAlibaba();
                //}
            }
        });

        oneKeyTestTeatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPreferences = getSharedPreferences("User_grades",MODE_PRIVATE);
                boolean isLoadedData = sharedPreferences.getBoolean("isLoadedData",false);
                if (isLoadedData){
                    // 如果已经保存学号和密码
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
                                Toast.makeText(MainActivity.this,"请重新点击验证码",Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onError(Exception e) {

                        }

                        @Override
                        public void setText(String msg) {

                        }
                    });

                    final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    View view1 = LayoutInflater.from(MainActivity.this).inflate(R.layout.pop_refresh_grades,null);
                    final EditText input_cap = view1.findViewById(R.id.input_cap);
                    cap_pic = view1.findViewById(R.id.cap_pic);
                    TextView pop_refresh_grades_classNumber = view1.findViewById(R.id.pop_refresh_grades_classNumber);
                    TextView cap_refresh_cancle = view1.findViewById(R.id.cap_refresh_cancle);
                    TextView cap_refresh_ok = view1.findViewById(R.id.cap_refresh_ok);
                    CardView cap_refresh_cardview = view1.findViewById(R.id.cap_refresh_cardview);
                    TextView cap_relogin = view1.findViewById(R.id.cap_relogin);

                    pop_refresh_grades_classNumber.setText(sharedPreferences.getString("username","请重新登陆"));

                    builder.setView(view1);
                    final AlertDialog dialog = builder.show();
                    cap_relogin.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            editor = getSharedPreferences("User_grades",MODE_PRIVATE).edit();
                            editor.putBoolean("isLoadedData",false);
                            editor.commit();
                            Intent intent = new Intent(MainActivity.this,ActivityLogin.class);
                            intent.putExtra("fromWhere","MainActivity");
                            dialog.dismiss();
                            startActivity (intent);


                        }
                    });
                    cap_refresh_cancle.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
                    cap_refresh_ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(MainActivity.this,"正在评教，请稍等...",Toast.LENGTH_LONG).show();
                            editor = getSharedPreferences("User_grades",MODE_PRIVATE).edit();
                            editor.putString("captcha",input_cap.getText().toString());
                            editor.commit();
                            rxDayuan.getLoginSuccess(new RetrofitCallbackListener() {
                                @Override
                                public void onFinish(int status) {
                                    rxDayuan.getEvaResults(sharedPreferences.getString("cookies", ""), new RetrofitCallbackListener() {
                                        @Override
                                        public void onFinish(int status) {
                                            List<String> list = dayuanDailyDatabase.getTeacherNameList();
                                            String [] teacherList = new String[list.size()];
                                            for(int i = 0; i < list.size() ; i++){
                                                teacherList[i] = list.get(i);
                                            }

                                            AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
                                            View view2 = getLayoutInflater().inflate(R.layout.pop_choose_one_class_layout,null);
                                            ListView teacherNameList = view2.findViewById(R.id.class_item_listview);
                                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,teacherList);

                                            teacherNameList.setAdapter(arrayAdapter);

                                            builder1.setView(view2);

                                            final AlertDialog alertDialog = builder1.show();
                                            teacherNameList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                @Override
                                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                                    dialog.dismiss();
                                                    alertDialog.dismiss();
                                                }
                                            });
                                            Window window = alertDialog.getWindow();
                                            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                                            window.setGravity(Gravity.CENTER);
                                            window.setWindowAnimations(R.style.Theme_AppCompat_Dialog_Alert);


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

                }else {
                    // 没有保存学号和密码
                    Toast.makeText(MainActivity.this,"请先登陆",Toast.LENGTH_SHORT).show();
                }
            }
        });

        get_rank_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sharedPreferences = getSharedPreferences("User_grades",MODE_PRIVATE);
                Boolean isLoadedData = sharedPreferences.getBoolean("isLoadedData",false);
                String username = sharedPreferences.getString("username","0000");
                String password = sharedPreferences.getString("password","0000");
                if(!isLoadedData){
                    Intent intent = new Intent(MainActivity.this,ActivityLogin.class);
                    startActivity(intent);
                    return;
                }
                if(dayuanDailyDatabase.isExitThisRank(Integer.parseInt(username))) {

                    Intent intent = new Intent(MainActivity.this, Activity_Ranks.class);
                    startActivity(intent);
                    return;
                }

                Toast.makeText(MainActivity.this,"正在加载数据，请耐心等待...",Toast.LENGTH_SHORT).show();
                rxDayuan.rankLogin(username, password, new RetrofitCallbackListener() {
                    @Override
                    public void onFinish(int status) {
                        Intent intent = new Intent(MainActivity.this, Activity_Ranks.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onError(Exception e) {

                    }

                    @Override
                    public void setText(String msg) {
                        //Toast.makeText(MainActivity.this,msg,Toast.LENGTH_LONG).show();
                    }
                });

            }
        });



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ///onDestroy();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sharedPreferences = getSharedPreferences("User_grades",MODE_PRIVATE);
        String want2SavePassword = sharedPreferences.getString("want2SavePassword","true");
        Boolean want2SavePassword2 = false;
        if (want2SavePassword.equals("true")){
            want2SavePassword2 = true;
        }
        if(!want2SavePassword2){
            editor = getSharedPreferences("User_grades",MODE_PRIVATE).edit();
            editor.putString("username","0000");
            editor.putString("password","0000");
            editor.putBoolean("isLoadedData",false);
            editor.commit();
        }
    }

    public void openAlibaba(){
        ApplicationInfo applicationInfo = null;
        try {
            applicationInfo = MainActivity.this.getPackageManager().getApplicationInfo("com.eg.android.AlipayGphone", PackageManager.GET_UNINSTALLED_PACKAGES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            //Snackbar.make(,"尚未安装支付宝",Snackbar.LENGTH_SHORT).show();
            Toast.makeText(this,"尚未安装支付宝",Toast.LENGTH_SHORT).show();
        }
        if (null!=applicationInfo){
            ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            editor = getSharedPreferences("test",MODE_PRIVATE).edit();
            sharedPreferences = getSharedPreferences("test",MODE_PRIVATE);
            String user = sharedPreferences.getString("user","z");
            if(user.equals("l")) {
                clipboardManager.setPrimaryClip(new ClipData(ClipData.newPlainText("Label","快来领支付宝红包！人人可领，天天可领！复制此消息，打开最新版支付宝就能领取！RXaFYw62U8")));
                editor.putString("user","z");
                editor.commit();
            }else {
                clipboardManager.setPrimaryClip(new ClipData(ClipData.newPlainText("Label", "快来领支付宝红包！人人可领，天天可领！复制此消息，打开最新版支付宝就能领取！2FnQKo55L9")));
                editor.putString("user","l");
                editor.commit();
            }
            PackageManager packageManager = MainActivity.this.getApplicationContext().getPackageManager();
            Intent intent = packageManager.getLaunchIntentForPackage("com.eg.android.AlipayGphone"/*"com.alipay.android.phone.wallet.sharetoken.ui.TokenDecodeActivity"*/);

            try {
                startActivity(intent);
            }catch (Exception e){
                e.printStackTrace();
            }

//            openAlbabaWebView.getSettings().setAppCacheEnabled(true);
//            openAlbabaWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
//            openAlbabaWebView.getSettings().setDomStorageEnabled(true);
//            openAlbabaWebView.loadUrl("http://qr.alipay.com/c1x089016ue4yr5wykokc9f");



        }
    }
    private void initId() {
        get_rank_btn = findViewById(R.id.get_rank_btn);
        //openAlbabaWebView = findViewById(R.id.openAlbabaWebView);
        oneKeyTestTeatch = findViewById(R.id.oneKeyTestTeatch);
        get_money = findViewById(R.id.get_money);
        check_schedule = findViewById(R.id.check_schedule);
        check_grades = findViewById(R.id.check_grades);
        //openAliPay = findViewById(R.id.openAliPay);
       //test_getCaptcha = findViewById(R.id.test_getCaptcha);
       // test_login = findViewById(R.id.test_login);
       // go_to_choose_class_aty = findViewById(R.id.go_to_choose_class_aty);
       /* test_loadClass = findViewById(R.id.test_loadClass);
        test_loadYearColleg = findViewById(R.id.test_loadYearColleg);*/
        rxDayuan = new RxDayuan(this);
       /* test_YearCollege = findViewById(R.id.test_YearCollege);
        test_Major = findViewById(R.id.test_Major);
        test_classname = findViewById(R.id.test_classname);
        test_Class = findViewById(R.id.test_Class);
        test_loadMajor = findViewById(R.id.test_loadMajor);
        test_loadClassName = findViewById(R.id.test_loadClassName);*/
        dayuanDailyDatabase = DayuanDailyDatabase.getInstance(this);
    }


}
