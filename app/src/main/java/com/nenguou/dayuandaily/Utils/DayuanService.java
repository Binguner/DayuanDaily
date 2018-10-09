package com.nenguou.dayuandaily.Utils;

import com.nenguou.dayuandaily.Model.Captcha;
import com.nenguou.dayuandaily.Model.Class;
import com.nenguou.dayuandaily.Model.ClassName;
import com.nenguou.dayuandaily.Model.Evaluate;
import com.nenguou.dayuandaily.Model.Grades;
import com.nenguou.dayuandaily.Model.LoginBean;
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

    //@POST("https://ssl.liuyinxin.com/univ/api/schedule/year-college")
    @POST("https://www.intyut.cn/api/intyut/class-schedule/choose/v1")
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

    // 登陆
    @FormUrlEncoded
    @POST("https://www.intyut.cn/api/intyut/login")
    Observable<LoginBean> login(@Field("username") String username, @Field("password") String password, @Field("remember-me") String remember_me);

    // 成绩
    //@GET("https://grade.liuyinxin.com/univ/grade/get")
    @POST("https://www.intyut.cn/api/intyut/grade/v1")
    Observable<Grades> getGrades();

    @GET("https://grade.liuyinxin.com/univs/evaluate/{sessionId}")
    Observable<Evaluate> getEvaluateResults(@Path("sessionId") String sessionId);


    @FormUrlEncoded
    @POST("https://grade.liuyinxin.com/univ/tyut/login")
    Observable<RankLoginModel> rankLogin(@Field("username") String username, @Field("password") String password);

    // 排名
    @POST("https://www.intyut.cn/api/intyut/rank/v1")
    Observable<RankModel> getRankModel();

}

