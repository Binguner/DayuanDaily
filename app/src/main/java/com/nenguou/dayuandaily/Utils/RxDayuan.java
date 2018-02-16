package com.nenguou.dayuandaily.Utils;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nenguou.dayuandaily.BuildConfig;
import com.nenguou.dayuandaily.DataBase.DayuanDailyDatabase;
import com.nenguou.dayuandaily.Model.Class;
import com.nenguou.dayuandaily.Model.ClassName;
import com.nenguou.dayuandaily.Model.Major;
import com.nenguou.dayuandaily.Model.YearCollege;

import java.io.File;
import java.io.IOException;
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
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by binguner on 2018/2/15.
 */

public class RxDayuan {

    private Context context;
    private String path;
    private Gson gson = new GsonBuilder().create();
    private Retrofit retrofit = new Retrofit
            .Builder()
            .client(getNewClient(context))
            .baseUrl("https://ssl.liuyinxin.com/univ/api/schedule/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .build();
    private DayuanService service = retrofit.create(DayuanService.class);
    private DayuanDailyDatabase dayuanDailyDatabase;

    private OkHttpClient getNewClient(Context mContext){

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

        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        builder.addInterceptor(loggingInterceptor)
                .addInterceptor(cacheInterceptor)
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15,TimeUnit.SECONDS)
                .cache(cache)
                .writeTimeout(15,TimeUnit.SECONDS)
                .retryOnConnectionFailure(true);
        return builder.build();
    }

    public RxDayuan(Context context){
        this.context = context;
        dayuanDailyDatabase = DayuanDailyDatabase.getInstance(context);
    }

    public void getYearCollege(){
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
                    public void onNext(YearCollege collegesBean) {
                        if (collegesBean != null){
                            dayuanDailyDatabase.saveYearCollege(collegesBean);
                        }
                    }
                });
    }

    public void getMajor(int collegeId){
        service.getMajor(collegeId)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Major>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Major major) {
                        //Log.d("DaYuanTag",major.getData().get(0).getMajor());
                        dayuanDailyDatabase.saveMajor(major);
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
                        dayuanDailyDatabase.saveClassName(className,name,year);

                    }
                });
    }

    public void getClass(String name, String term){
        service.getClass(name,term)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Class>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Class aClass) {
                       Log.d("DaYuanTag1",aClass.getData().getData().toString());
                        dayuanDailyDatabase.saveClass(aClass);
                    }
                });
    }



}
