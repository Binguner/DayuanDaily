package com.nenguou.dayuandaily.Utils;

import com.nenguou.dayuandaily.Model.Captcha;
import com.nenguou.dayuandaily.Model.Class;
import com.nenguou.dayuandaily.Model.ClassBean;
import com.nenguou.dayuandaily.Model.ClassName;
import com.nenguou.dayuandaily.Model.Evaluate;
import com.nenguou.dayuandaily.Model.Grades;
import com.nenguou.dayuandaily.Model.LoginBean;
import com.nenguou.dayuandaily.Model.Major;
import com.nenguou.dayuandaily.Model.MajorAndClasses;
import com.nenguou.dayuandaily.Model.RankLoginModel;
import com.nenguou.dayuandaily.Model.RankModel;
import com.nenguou.dayuandaily.Model.RestClass;
import com.nenguou.dayuandaily.Model.YearCollege;
import com.nenguou.dayuandaily.Model.YearCollege2;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by binguner on 2018/2/15.
 */

public interface DayuanService {

    //@POST("https://ssl.liuyinxin.com/univ/api/schedule/year-college")
    // 获取 学期 和 学院 数据
    @POST("https://www.intyut.cn/api/intyut/class-schedule/choose/v1")
    Observable<YearCollege> getYearCollege();

    // 获取这个学院里的 专业 和 这个专业的所有班级
    @FormUrlEncoded
    @POST("https://www.intyut.cn/api/intyut/class-schedule/choose/v1")
    Observable<MajorAndClasses> getMajor(@Field("detail") String detail, @Field("college") String college_id);

    @FormUrlEncoded
    @POST("https://ssl.liuyinxin.com/univ/api/schedule/class-name")
    Observable<ClassName> getClassName(@Field("year") int year, @Field("name") String name);

    @FormUrlEncoded
    @POST("https://www.intyut.cn/api/intyut/class-schedule/v1")
    Observable<ClassBean> getClass(@Field("cName") String cName, @Field("cNumber") String cNumber, @Field("term") String term, @Field("tName") String tName);

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

    @POST("https://www.intyut.cn/api/intyut/restclass/choose/v1")
    Observable<RestClass> getRestClass();

}

