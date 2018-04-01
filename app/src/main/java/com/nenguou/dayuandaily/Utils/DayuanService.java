package com.nenguou.dayuandaily.Utils;

import com.nenguou.dayuandaily.Model.Captcha;
import com.nenguou.dayuandaily.Model.Class;
import com.nenguou.dayuandaily.Model.ClassName;
import com.nenguou.dayuandaily.Model.Evaluate;
import com.nenguou.dayuandaily.Model.Grades;
import com.nenguou.dayuandaily.Model.Major;
import com.nenguou.dayuandaily.Model.RankLoginModel;
import com.nenguou.dayuandaily.Model.RankModel;
import com.nenguou.dayuandaily.Model.YearCollege;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by binguner on 2018/2/15.
 */

public interface DayuanService {

    @POST("https://ssl.liuyinxin.com/univ/api/schedule/year-college")
    Observable<YearCollege> getYearCollege();

    @FormUrlEncoded
    @POST("https://ssl.liuyinxin.com/univ/api/schedule/major")
    Observable<Major> getMajor(@Field("collegeId") int collegeId);

    @FormUrlEncoded
    @POST("https://ssl.liuyinxin.com/univ/api/schedule/class-name")
    Observable<ClassName> getClassName(@Field("year") int year, @Field("name") String name);

    @FormUrlEncoded
    @POST("https://ssl.liuyinxin.com/univ/api/schedule/class")
    Observable<Class> getClass(@Field("name") String name, @Field("term") String term);

    // 获取 captchaUrl，cookies
    @GET("https://grade.liuyinxin.com/univ/captcha/get")
    Observable<Captcha> getCaptcha();

    @FormUrlEncoded
    @POST("https://grade.liuyinxin.com/univ/login")
    Observable<String> login(@Field("username") String username, @Field("password") String password,@Field("captcha") String captcha,@Field("sessionId") String sessionId);

    @GET("https://grade.liuyinxin.com/univ/grade/get")
    Observable<Grades> getGrades(@Query("sessionId") String sessionId);

    @GET("https://grade.liuyinxin.com/univs/evaluate/{sessionId}")
    Observable<Evaluate> getEvaluateResults(@Path("sessionId") String sessionId);

    @FormUrlEncoded
    @POST("https://grade.liuyinxin.com/univ/tyut/login")
    Observable<RankLoginModel> rankLogin(@Field("username") String username, @Field("password") String password);

    @FormUrlEncoded
    @POST("https://grade.liuyinxin.com/univ/tyut/rank")
    Observable<RankModel> getRankModel(@Field("username") String username, @Field("password") String password,@Field("cookie") String cookie);

}

