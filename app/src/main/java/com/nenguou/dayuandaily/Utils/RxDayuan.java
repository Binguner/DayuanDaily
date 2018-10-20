package com.nenguou.dayuandaily.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nenguou.dayuandaily.BuildConfig;
import com.nenguou.dayuandaily.DataBase.DayuanDailyDatabase;
import com.nenguou.dayuandaily.Listener.CallbackListener;
import com.nenguou.dayuandaily.Listener.RetrofitCallbackListener;
import com.nenguou.dayuandaily.Model.Captcha;
import com.nenguou.dayuandaily.Model.Class;
import com.nenguou.dayuandaily.Model.ClassBean;
import com.nenguou.dayuandaily.Model.ClassDetial;
import com.nenguou.dayuandaily.Model.ClassName;
import com.nenguou.dayuandaily.Model.Evaluate;
import com.nenguou.dayuandaily.Model.Grades;
import com.nenguou.dayuandaily.Model.LoginBean;
import com.nenguou.dayuandaily.Model.Major;
import com.nenguou.dayuandaily.Model.MajorAndClasses;
import com.nenguou.dayuandaily.Model.RankLoginModel;
import com.nenguou.dayuandaily.Model.RankModel;
import com.nenguou.dayuandaily.Model.RankModelDetial;
import com.nenguou.dayuandaily.Model.YearCollege;
import com.nenguou.dayuandaily.Model.YearCollege2;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by binguner on 2018/2/15.
 */

public class RxDayuan {

    //private android.support.v7.app.AlertDialog alertDialog;
    //private android.support.v7.app.AlertDialog.Builder builder;

    private Context context;
    private String path;
    private Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    private Retrofit retrofit; /*= new Retrofit
            .Builder()
            .client(getNewClient(context))
            .baseUrl("https://ssl.liuyinxin.com/univ/api/schedule/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .build();*/
    private DayuanService service/* = retrofit.create(DayuanService.class)*/;
    private DayuanDailyDatabase dayuanDailyDatabase;
    private final String RxTag = "rxDayuanTag";

    public RxDayuan(Context context){
        this.context = context;
        Log.d("werwer","inited");
        dayuanDailyDatabase = DayuanDailyDatabase.getInstance(context);
        //initBd();
        retrofit = new Retrofit
                .Builder()
                .client(getNewClient(context))
                .baseUrl("https://ssl.liuyinxin.com/univ/api/schedule/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        service = retrofit.create(DayuanService.class);
    }

    private OkHttpClient getNewClient(Context mContext){
        Log.d("werwer","inited33");
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        if(BuildConfig.DEBUG){
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        }else{
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        }
        path = "data/user/0/com.nenguou.dayuandaily/cache";
        File cacheFile = new File(path,"DayuanCache");
        final Cache cache = new Cache(cacheFile,10*1024*1024);
        // 缓存拦截器
        Interceptor cacheInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if(!NetworkUtils.isAvailable(context)){
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                }

                Response response = chain.proceed(request);

                if (NetworkUtils.isAvailable(context)) {
                    int maxAge = 0;
                    response.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxAge)
                            .build();
                }
                return response;
            }
        };
        ClearableCookieJar cookieJar = new PersistentCookieJar(new SetCookieCache(),new SharedPrefsCookiePersistor(mContext));

        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        builder.addInterceptor(loggingInterceptor)
                .addInterceptor(cacheInterceptor)
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15,TimeUnit.SECONDS)
                .cache(cache)
                .cookieJar(cookieJar)
                .writeTimeout(15,TimeUnit.SECONDS)
                .retryOnConnectionFailure(true);
        return builder.build();
    }

//    private void initBd() {
//        builder = new android.support.v7.app.AlertDialog.Builder(context);
//        builder.setTitle("正在拼命加载");
//        builder.setMessage("请稍等");
//        builder.setCancelable(false);
//    }

    public void getGrades(final RetrofitCallbackListener listener){
        SharedPreferences sharedPreferences = context.getSharedPreferences("User_grades",Context.MODE_PRIVATE);
        //String sessionId = sharedPreferences.getString("cookies","");
        service.getGrades()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Grades>() {
                    @Override
                    public void onCompleted() {
                        listener.onFinish(0);
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onFinish(1);
                        listener.onError((Exception) e);
                    }

                    @Override
                    public void onNext(Grades grades) {
                        if(grades!=null){
                            if(grades.getMsg().equals("timout")){
                                Toast.makeText(context,"timout, 请检查学号、密码、验证码是否正确，或点击验证码刷新...",Toast.LENGTH_SHORT).show();
                            }
                            dayuanDailyDatabase.dropAndCreateTableGrades();
                            dayuanDailyDatabase.saveGrades(grades);
                        }
                    }
                });
    }

    public void getGrades2(final CallbackListener callbackListener){
        SharedPreferences sharedPreferences = context.getSharedPreferences("User_grades",Context.MODE_PRIVATE);
        service.getGrades()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Grades>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("tetete",e.toString());
                    }

                    @Override
                    public void onNext(Grades grades) {
                        if (null != grades ){
                            callbackListener.callBack(grades.getCode(),grades.getMsg());
                            Log.d("tetete",grades.getMsg());
                            if (grades.getMsg().equals("success")){
                                dayuanDailyDatabase.dropAndCreateTableGrades();
                                dayuanDailyDatabase.saveGrades(grades);
                            }
                        }
                    }
                });
    }

    public void getCaptcha(final RetrofitCallbackListener listener){
        service.getCaptcha()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Captcha>() {
                    @Override
                    public void onCompleted() {
                        listener.onFinish(0);
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onFinish(1);
                        listener.onError((Exception) e);
                    }

                    @Override
                    public void onNext(Captcha captcha) {
                        SharedPreferences.Editor editor = context.getSharedPreferences("User_grades",Context.MODE_PRIVATE).edit();
                        editor.putString("captchaUrl",captcha.getData().getCaptchaUrl());
                        editor.putString("cookies",captcha.getData().getCookies());
                        editor.commit();
                    }
                });
    }

    public void getLoginSuccess2(final CallbackListener listener){
        SharedPreferences sharedPreferences = context.getSharedPreferences("User_grades",Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username","");
        String password = sharedPreferences.getString("password","");
        String remember_me = sharedPreferences.getString("want2SavePassword","false");

        service.login(username,password,remember_me)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<LoginBean>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(LoginBean loginBean) {
                        listener.callBack(loginBean.getCode(),loginBean.getMsg());
                        /*if(loginBean.getCode() == 1){

                            getGrades2(new CallbackListener() {
                                @Override
                                public void callBack(int status, @NotNull String msg) {

                                }
                            });
                        }*/
                    }
                });
    }

    // 不用了
    public void getLoginSuccess(final RetrofitCallbackListener listener){
        final SharedPreferences sharedPreferences = context.getSharedPreferences("User_grades",Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username","");
        String password = sharedPreferences.getString("password","");
        //final String captcha = sharedPreferences.getString("captcha","");
        String remember_me = sharedPreferences.getString("want2SavePassword","false");
        //Log.d(RxTag,"username : "+ username + "  password :" + password + "captcha :" + captcha +"  sessionId : " + sessionId);
        try {
            service.login(username,password,remember_me)
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<LoginBean>() {
                        @Override
                        public void onCompleted() {
                            listener.onFinish(0);
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(LoginBean loginBean) {
                            if(null != loginBean){
                                if (loginBean.getCode() == 1){
                                    listener.onFinish(0);

                                    listener.setText("登陆成功！");
                                }else if(loginBean.getCode() == -1){
                                    listener.onFinish(1);
                                    listener.setText("用户名或密码输入错误，登录失败!");
                                }
                            }
                        }
                    });
                    /*.subscribe(new Subscriber<String>() {
                        @Override
                        public void onCompleted() {
                            //Log.d(RxTag,"onCompleted  !");
                            listener.onFinish(0);
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.d(RxTag,"onError  !"+e.toString());
                            listener.onFinish(1);
                            Toast.makeText(context,"与教务处连接超时，请稍后重试",Toast.LENGTH_SHORT).show();
                            ///captcha/7/c8b0a10f-5ef2-4f68-a154-e7f476ac0b6b.jpg
                            //d279560346fe47c39c8e5b9dcbe9b85c
                            listener.onError((Exception) e);
                        }

                        @Override
                        public void onNext(String  s) {
                            s.trim();
                            //Log.d(RxTag,s.toString()+" ghjhgfgh !");
                        }
                    });*/
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /*public void getYearCollege(final RetrofitCallbackListener listener){
        service.getYearCollege()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<YearCollege2>() {
                    @Override
                    public void onCompleted() {
                        //alertDialog.dismiss();
                        listener.onFinish(0);
                    }

                    @Override
                    public void onError(Throwable e) {
                        unsubscribe();
                        listener.onFinish(1);
                    }

                    @Override
                    public void onNext(YearCollege2 collegesBean) {
                        if (collegesBean != null){
//                            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialogInterface, int i) {
//                                    onError(new Exception());
//                                }
//                            });
//                            alertDialog = builder.create();
//                            alertDialog.show();

                            dayuanDailyDatabase.saveYearCollege(collegesBean);
                            String term = collegesBean.getData().getTerms().get(0).getName();
                            SharedPreferences.Editor editor = context.getSharedPreferences("User_YearCollege",Context.MODE_PRIVATE).edit();
                            editor.putLong("start",collegesBean.getData().getTerms().get(0).getStart());
                            editor.putString("term",term);
                            editor.commit();
                        }
                    }
                });

    }*/

    public void getYearCollege(final CallbackListener callbackListener){
        service.getYearCollege()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<YearCollege>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(YearCollege yearCollege) {
                        if(yearCollege.getCode() == 1 && null != yearCollege.getData() && yearCollege.getMsg().contains("suc")){
                            dayuanDailyDatabase.saveYearCollege(yearCollege);
                            callbackListener.callBack(yearCollege.getCode(),yearCollege.getMsg());
                        }
                    }
                });
    }

    public void getMajorAndClasses(String detial, String college_id, final CallbackListener callbackListener) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("User_YearCollege",Context.MODE_PRIVATE);
        college_id = sharedPreferences.getString("college_id","01");
                service.getMajor("01", college_id)
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<MajorAndClasses>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(MajorAndClasses majorAndClasses) {
                                //Log.d("DaYuanTag",major.getData().get(0).getMajor());\
                                if (majorAndClasses.getCode() == 1 && null != majorAndClasses.getData() && majorAndClasses.getMsg().contains("suc")){
                                    dayuanDailyDatabase.saveMajorAndClass(majorAndClasses);
                                    callbackListener.callBack(majorAndClasses.getCode(),majorAndClasses.getMsg());
                                }

                            }
                        });
    }

    public void getClassName(final int year, final String name){
        service.getClassName(year,name)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

                .subscribe(new Subscriber<ClassName>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ClassName className) {
                        //Log.d("DaYuanTag",className.getData().size()+"");
                        if(className != null) {
                            dayuanDailyDatabase.saveClassName(className, name, year);
                        }

                    }
                });
    }

    public void getClass(String cName, String cNumber,String term,String tName ,CallbackListener callbackListener ){
        //String mName = "软件1632";
        service.getClass(cName,cNumber,term, tName)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
//                .map(new Func1<Class, Object>() {
//                })
                .subscribe(new Subscriber<ClassBean>() {
                    @Override
                    public void onCompleted() {
                        //Log.d(RxTag,"onCompleted!");
                    }

                    @Override
                    public void onError(Throwable e) {
                        //Log.d(RxTag,"onError: "+ e.toString());
                    }

                    @Override
                    public void onNext(ClassBean aClass) {
                        //Log.d("DaYuanTag1",aClass.getData().getData().toString());
                        if(aClass!=null) {
                            SharedPreferences.Editor editor = context.getSharedPreferences("User_YearCollege", Context.MODE_PRIVATE).edit();
                            editor.putString("schedule_id", "null");
                            editor.commit();
//                            dayuanDailyDatabase.saveClass(aClass);
                            //Log.d(RxTag,"Over!");
                            //Log.d(RxTag,aClass.getData().getData().getSchedule().size()+"");
                            //Log.d(RxTag,aClass.getData().getData());
                            Gson gson = new Gson();
                            //ClassDetial classDetial = gson.fromJson(aClass.getData().getData(),ClassDetial.class);
                            ///Log.d(RxTag,classDetial.getSchedule().size()+"");
                            //Log.d(RxTag,classDetial.getSchedule().get(0).getName()+"");
                            //Log.d(RxTag,classDetial.getSchedule().get(0).getName_suffix()+"");
                            //Log.d(RxTag,classDetial.getSchedule().get(0).getWeeks().toString());
                            dayuanDailyDatabase.saveClass(aClass);
                        }
                    }
                });
    }

    public void getEvaResults(String sessionID, final RetrofitCallbackListener retrofitCallbackListener){
        service.getEvaluateResults(sessionID)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Evaluate>() {
                    @Override
                    public void onCompleted() {
                        retrofitCallbackListener.onFinish(0);
                    }

                    @Override
                    public void onError(Throwable e) {
                        retrofitCallbackListener.onFinish(1);
                        retrofitCallbackListener.onError((Exception) e);
                    }

                    @Override
                    public void onNext(Evaluate evaluate) {
                        try {
                            List<String> teacherArrayList = new ArrayList<>();
                            String[] teacherList = evaluate.getTeachersName().split(";");
                            for (String s : teacherList) {
                                teacherArrayList.add(s);
                            }
                            if (evaluate.getTotal() == 0) {
                                Toast.makeText(context, "请检查验证码是否输入正确！", Toast.LENGTH_SHORT).show();
                            }
                            dayuanDailyDatabase.setTeacherNameList(teacherArrayList);
                        }catch (Exception e){
                            e.printStackTrace();
                            Toast.makeText(context,e.toString(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    // 不用了
    public void rankLogin(final String username, final String password, final RetrofitCallbackListener listener){
//        service.rankLogin(username,password)
//                .subscribeOn(Schedulers.io())
//                .unsubscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<RankLoginModel>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onNext(RankLoginModel rankLoginModel) {
//                        listener.setText(rankLoginModel.getMsg());
//                        Log.d(RxTag,rankLoginModel.getMsg() + " " + rankLoginModel.getCode() + " " + rankLoginModel.getData() );
//                    }
//                });
        service.rankLogin(username,password)
                .subscribeOn(Schedulers.io())
                .flatMap(new Func1<RankLoginModel, Observable<RankModel>>() {
                    @Override
                    public Observable<RankModel> call(RankLoginModel rankLoginModel) {
                        String cookie = "";
                        if(rankLoginModel.getCode() == 1){
                            cookie = rankLoginModel.getData();
                        }
                        return service.getRankModel();
                    }
                })
//                .flatMap(new Func1<RankModel, Observable<RankModelDetial>>() {
//                    @Override
//                    public Observable<RankModelDetial> call(RankModel rankModel) {
//                        Log.d(RxTag,rankModel.getData());
//                        Gson gson = new Gson();
//                        RankModelDetial rankModelDetial = new RankModelDetial();
//                        rankModelDetial = gson.fromJson(rankModel.getData(),RankModelDetial.class);
//                        return Observable.just(rankModelDetial);
//                    }
//                })
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Subscriber<RankModel>() {
                    @Override
                    public void onCompleted() {
                        listener.onFinish(0);
                        Toast.makeText(context,"数据加载成功！",Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(RxTag,"error: " + e.toString());
                        Toast.makeText(context,"数据加载失败，请重试...",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(RankModel rankModel) {
                        if (null != rankModel){
                            Gson gson = new Gson();
                            String data = rankModel.getData().replace("[","");
                            data = data.replace("]","");
                            RankModelDetial rankModelDetial = gson.fromJson(data,RankModelDetial.class);
                            //Log.d(RxTag,"Ot:" + rankModelDetial.toString());
                            dayuanDailyDatabase.saveRank(rankModelDetial);
                            //Toast.makeText(context,"正在加载数据，请耐心等待...",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void getRanks(final CallbackListener callbackListener){
        service.getRankModel()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<RankModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(RankModel rankModel) {
                        callbackListener.callBack(rankModel.getCode(),rankModel.getMsg());
                        if(rankModel.getCode() == 1 && rankModel.getMsg().equals("success") && null != rankModel.getData()){
                            Gson gson = new Gson();
                            String data = rankModel.getData().replace("[","");
                            data = data.replace("]","");
                            RankModelDetial rankModelDetial = gson.fromJson(data,RankModelDetial.class);
                            //Log.d(RxTag,"Ot:" + rankModelDetial.toString());
                            dayuanDailyDatabase.saveRank(rankModelDetial);
                        }

                    }
                });
    }



}