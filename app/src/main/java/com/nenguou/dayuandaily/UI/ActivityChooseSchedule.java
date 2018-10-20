package com.nenguou.dayuandaily.UI;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.nenguou.dayuandaily.DataBase.DayuanDailyDatabase;
import com.nenguou.dayuandaily.Listener.CallbackListener;
import com.nenguou.dayuandaily.Model.Classe;
import com.nenguou.dayuandaily.Model.College;
import com.nenguou.dayuandaily.Model.Major;
import com.nenguou.dayuandaily.Model.Term;
import com.nenguou.dayuandaily.Model.YearCollege2;
import com.nenguou.dayuandaily.R;
import com.nenguou.dayuandaily.Listener.RetrofitCallbackListener;
import com.nenguou.dayuandaily.Utils.RxDayuan;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.qqtheme.framework.picker.OptionPicker;

/**
 * SharedPreferences : User_YearCollege
 * year:Int 2016
 * theSelectedTime : 1 or 2 or 3 or 4
 * isFirstLoadCollege : true / false
 * isScheduleSelected: true / false
 * start : 1520179204000 in rxDayuan (long)
 * term_name : 2018-2019学年秋(两学期)
 * term_value : 2018-2019-1-1
 * college_id:01
 * college_name: 机械工程学院
 * major_name:工业设计
 * majorId:Int 22
 * majorName:String 软件工程
 * className:String 软件1632
 * classNumber:String 软件1632
 * term:String 2017-2018-2-1
 * schedule_id:Int 2465  in rxDayuan
 */
public class ActivityChooseSchedule extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private final String AtyCSTag = "ActivityChoosheduleTag";
    private final int CHOOSE_COLLEGE = 0;
    private final int CHOOSE_TERM = 1;
    private final int CHOOSE_MAJOR = 2;
    private final int CHOOSE_CLASS = 3;
    private boolean justLoaded = false;
    @BindView(R.id.pleasechooseclass) TextView pleasechooseclass;
    @BindView(R.id.chooseYearText) TextView chooseYearText;
    @BindView(R.id.chooseCollegeText) TextView chooseCollegeText;
    @BindView(R.id.chooseMajorText) TextView chooseMajorText;
    @BindView(R.id.chooseClassText) TextView chooseClassText;
    @BindView(R.id.chooseTimeText) TextView chooseTimeText;
    @BindView(R.id.chooseClassOver) Button chooseClassOver;
    @BindView(R.id.chooseClassAgain) Button chooseClassAgain;

    @BindView(R.id.choose_year_cardview) CardView choose_year_cardview;
    @BindView(R.id.choose_college_cardview) CardView choose_college_cardview;
    @BindView(R.id.choose_major_cardview) CardView choose_major_cardview;
    @BindView(R.id.choose_class_cardview) CardView choose_class_cardview;
    @BindView(R.id.choose_time_cardview) CardView choose_time_cardview;

    @BindView(R.id.choose_schedule_toolbar) Toolbar choose_schedule_toolbar;
    @BindView(R.id.the_saved_schedule) Spinner the_saved_schedule;

    List<String> class_name_list;
    DayuanDailyDatabase dayuanDailyDatabase;
    RxDayuan rxDayuan;
    String[] classNameArray;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.myAppTheme);
        setContentView(R.layout.activity_choose_schedule);
        ButterKnife.bind(this);
        editor = this.getSharedPreferences("User_YearCollege", Context.MODE_PRIVATE).edit();
        rxDayuan = new RxDayuan(this);
        dayuanDailyDatabase = DayuanDailyDatabase.getInstance(this);
        setListener();
        initDatas();

    }

    private void setListener() {
        choose_schedule_toolbar.setNavigationIcon(R.mipmap.back_bg);
        choose_schedule_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityChooseSchedule.this.finish();
            }
        });
    }

    private void initDatas() {
        class_name_list = new ArrayList<>();
        class_name_list = dayuanDailyDatabase.getSavedClassName();
        classNameArray = class_name_list.toArray(new String[0]);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,android.R.id.text1,classNameArray);
        //the_saved_schedule.setAdapter(arrayAdapter);
        //SharedPreferences.Editor editor = this.getSharedPreferences("User_YearCollege", Context.MODE_PRIVATE).edit();
        the_saved_schedule.setOnItemSelectedListener(this);
        the_saved_schedule.setAdapter(arrayAdapter);


        SharedPreferences sharedPreferences = getSharedPreferences("User_YearCollege",MODE_PRIVATE);
       // boolean isFirstLoad = sharedPreferences.getBoolean("isFirstLoadCollege",true);
        //boolean isScheduleSelected = sharedPreferences.getBoolean("isScheduleSelected",false);
        //if (isFirstLoad) {
            Toast.makeText(this, "正在加载数据，请稍等", Toast.LENGTH_SHORT).show();
            /*rxDayuan.getYearCollege(new RetrofitCallbackListener() {
                @Override
                public void onFinish(int status) {
                    Toast.makeText(ActivityChooseSchedule.this, "数据加载成功", Toast.LENGTH_SHORT).show();
                    SharedPreferences.Editor editor = getSharedPreferences("User_YearCollege", MODE_PRIVATE).edit();
                    editor.putBoolean("isFirstLoadCollege", false);
                    //editor.putBoolean("isScheduleSelected",true);
                    editor.commit();
                }

                @Override
                public void onError(Exception e) {
                    Toast.makeText(ActivityChooseSchedule.this, "数据加载失败，请重新打开 App", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void setText(String msg) {

                }
            });*/
            dayuanDailyDatabase.dropYearCollege();
            rxDayuan.getYearCollege(new CallbackListener() {
                @Override
                public void callBack(int status, @NotNull String msg) {
                    if(msg.contains("suc")){
                        Toast.makeText(ActivityChooseSchedule.this,"数据加载成功！",Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(ActivityChooseSchedule.this,"数据加载失败，请重新打开此页面！",Toast.LENGTH_SHORT).show();
                    }
                }
            });
       // }
//        if(!isFirstLoad){
//            Intent intent = new Intent(this,ActivityScheduler.class);
//            startActivity(intent);
//            this.finish();
//        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String [] newArray;
        if( i == 1){
            dayuanDailyDatabase.clearTheSchedule();
            class_name_list.clear();
            class_name_list.add("已存课表");
            class_name_list.add("清空课表");
            editor.putBoolean("isScheduleSelected",false);
            editor.commit();
            /*String[] */newArray = class_name_list.toArray(new String[0]);
            ArrayAdapter<String> Adapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,android.R.id.text1,newArray);

            the_saved_schedule.setAdapter(Adapter);
        }
        if(i != 0 && i != 1){
            //editor.putInt("schedule_id", dayuanDailyDatabase.getScheduleId(classNameArray[i]));
            editor.putString("className",class_name_list.get(i));
            editor.commit();
            Intent intent = new Intent(ActivityChooseSchedule.this,ActivityScheduler.class);
            startActivity(intent);
            ActivityChooseSchedule.this.finish();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @OnClick(R.id.choose_year_cardview)
    public void chooseYearClick(View view){
        showChooseDialog(CHOOSE_TERM);
    }

    @OnClick(R.id.choose_college_cardview)
    public void chooseCollegeClick(View view){
        if(chooseYearText.getText().equals("请选择")){
            Toast.makeText(this,"请先选择学期",Toast.LENGTH_SHORT).show();
        }else {
            showChooseDialog(CHOOSE_COLLEGE);
        }
    }

    @OnClick(R.id.choose_major_cardview)
    public void chooseMajorClick(View view){
        if(chooseCollegeText.getText().equals("请选择")){
            Toast.makeText(this,"请先选择学院",Toast.LENGTH_SHORT).show();
        }else {
            showChooseDialog(CHOOSE_MAJOR);
        }
    }

    @OnClick(R.id.choose_class_cardview)
    public void chooseClassClick(View view){
        if(chooseMajorText.getText().equals("请选择")){
            Toast.makeText(this,"请先选择专业",Toast.LENGTH_SHORT).show();
        }else {
            showChooseDialog(CHOOSE_CLASS);
        }
    }

    @OnClick(R.id.chooseTimeText)
    public void chooseTimeClick(View view){
        //DialogFragment dialogFragment = new DataPickerFragment();
        //dialogFragment.show(getFragmentManager(),"datePicker");

        final Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH) + 1;
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        final SharedPreferences.Editor editor= this.getSharedPreferences("User_YearCollege",MODE_PRIVATE).edit();

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                Toast.makeText(ActivityChooseSchedule.this, i+"year "+(i1+1)+"month "+i2+"day", Toast.LENGTH_SHORT).show();
                calendar.set(i,i1,i2,0,0);
                editor.putLong("start",calendar.getTimeInMillis());
                editor.commit();
            }
        },year,month,day);
        datePickerDialog.show();
    }

    public void setClassStartTime(){

    }

    @OnClick(R.id.chooseClassAgain)
    public void chooseClassAgainClick(View view){
        chooseYearText.setText("请选择");
        chooseCollegeText.setText("请选择");
        chooseMajorText.setText("请选择");
        chooseClassText.setText("请选择");
        pleasechooseclass.setText("请选择学期");
    }

    @OnClick(R.id.chooseClassOver)
    public void chooseClassOverClick(View v){

        if(chooseClassText.getText().toString().equals("请选择")){
            Toast.makeText(ActivityChooseSchedule.this,"请先选择！",Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences.Editor editor = getSharedPreferences("User_YearCollege",MODE_PRIVATE).edit();
        editor.putBoolean("isScheduleSelected",true);
        editor.commit();
        Intent intent = new Intent(this,ActivityScheduler.class);
        startActivity(intent);
        this.finish();
    }

    public void showChooseDialog(int type){
        final String[] dataArray;
        OptionPicker picker = null;
        switch (type){
            case CHOOSE_COLLEGE:
                final SharedPreferences.Editor editor = getSharedPreferences("User_YearCollege",MODE_PRIVATE).edit();
                final List<College> collegeList = dayuanDailyDatabase.loadCollege();
                if(null != collegeList){
                    dataArray = new String[collegeList.size()];
                    for(int i = 0 ; i < collegeList.size(); i++){
                        dataArray[i] = collegeList.get(i).getName();
                    }
                    picker = new OptionPicker(this,dataArray);
                    picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
                        @Override
                        public void onOptionPicked(int index, String item) {
                            chooseCollegeText.setText(dataArray[index]);
                            editor.putString("college_id", collegeList.get(index).getId());
                            editor.putString("college_name", collegeList.get(index).getName());
                            editor.commit();
                            pleasechooseclass.setText("请选择专业");
                            //chooseCollegeText.setText("请选择");
                            chooseMajorText.setText("请选择");
                            chooseClassText.setText("请选择");
                            dayuanDailyDatabase.dropMajorAndClasses();
                            rxDayuan.getMajorAndClasses("01", collegeList.get(index).getId(), new CallbackListener() {
                                @Override
                                public void callBack(int status, @NotNull String msg) {
                                    if (status == 1){
                                        justLoaded = true;
                                    }else {
                                        Toast.makeText(ActivityChooseSchedule.this, "专业数据加载失败，请重试！", Toast.LENGTH_SHORT).show();
                                        justLoaded = false;
                                    }
                                }
                            });

                        }
                    });
                }
                break;
            case CHOOSE_TERM:
                final List<Term> termList = dayuanDailyDatabase.loadTerm();
                dataArray = new String[termList.size()];
                for (int i = 0 ; i < termList.size(); i++){
                    dataArray[i] = termList.get(i).getN();
                }
                if(null!=termList){
                    picker = new OptionPicker(this,dataArray);
                    picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
                        @Override
                        public void onOptionPicked(int index, String item) {
                            chooseYearText.setText(dataArray[index]);
                            SharedPreferences.Editor editor = getSharedPreferences("User_YearCollege", MODE_PRIVATE).edit();
                            editor.putString("term_name", termList.get(index).getN());
                            editor.putString("term_value", termList.get(index).getV());
                            editor.commit();
                            pleasechooseclass.setText("请选择学院");
                            chooseMajorText.setText("请选择");
                            chooseClassText.setText("请选择");
                        }
                    });
                }
                break;

            case CHOOSE_MAJOR:
                /*if (!justLoaded) {
                    dayuanDailyDatabase.dropMajorAndClasses();
                    SharedPreferences sharedPreferences1 = getSharedPreferences("User_YearCollege", MODE_PRIVATE);
                    Toast.makeText(ActivityChooseSchedule.this, "正在加载数据，请稍等！", Toast.LENGTH_SHORT).show();
                    rxDayuan.getMajorAndClasses("01", sharedPreferences1.getString("college_id", "null"), new CallbackListener() {
                        @Override
                        public void callBack(int status, @NotNull String msg) {
                            if (msg.contains("suc")) {
                                Toast.makeText(ActivityChooseSchedule.this, "数据加载成功！", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ActivityChooseSchedule.this, "数据加载失败，请重试！", Toast.LENGTH_SHORT).show();
                            }
                            if (status == 1) {
                                justLoaded = true;
                            }
                        }
                    });
                }*/

                if(justLoaded){
                    final List<String> majorNameList = dayuanDailyDatabase.loadMajors();
                    picker = new OptionPicker(this, majorNameList);
                    picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
                        @Override
                        public void onOptionPicked(int index, String item) {
                            chooseMajorText.setText(majorNameList.get(index));
                            SharedPreferences.Editor editor = getSharedPreferences("User_YearCollege", MODE_PRIVATE).edit();
                            editor.putString("major_name",majorNameList.get(index));
                            editor.commit();
                            pleasechooseclass.setText("请选择班级");
                            chooseClassText.setText("请选择");
                        }
                    });
                }
                /*SharedPreferences sharedPreferences = getSharedPreferences("User_YearCollege",MODE_PRIVATE);
                int collegeId = sharedPreferences.getInt("collegeId",22);
                final int year = sharedPreferences.getInt("year",2016);
                final List<Major.DataBean> majorList = dayuanDailyDatabase.loadMajor(collegeId);
                if(majorList!=null) {
                    dataArray = new String[majorList.size()];
                    for (int i = 0; i < majorList.size(); i++) {
                        dataArray[i] = majorList.get(i).getMajor();
                    }
                    picker = new OptionPicker(this, dataArray);
                    picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
                        @Override
                        public void onOptionPicked(int index, String item) {
                            chooseMajorText.setText(dataArray[index]);
                            SharedPreferences.Editor editor = getSharedPreferences("User_YearCollege", MODE_PRIVATE).edit();
                            editor.putInt("majorId", majorList.get(index).getId());
                            editor.putString("majorName", majorList.get(index).getMajor());
                            //Log.d(Tag, majorList.get(index).getMajor());
                            String majorName = majorList.get(index).getMajor();
                            rxDayuan.getClassName(year, majorName);
                            editor.commit();
                            pleasechooseclass.setText("请选择班级");
                            chooseClassText.setText("请选择");

                        }
                    });
                }*/
                break;
            case CHOOSE_CLASS:
                final SharedPreferences sharedPreferences2 = getSharedPreferences("User_YearCollege",MODE_PRIVATE);
                //final int classYear = sharedPreferences2.getInt("year",2016);
                //final String class_term = sharedPreferences2.getString("term","2017-2018-2-1");
                String major_name = sharedPreferences2.getString("major_name","软件工程");
                final List<Classe> classNameList = dayuanDailyDatabase.loadClassName(major_name);
                if(classNameList != null){
                    dataArray = new String[classNameList.size()];
                    for(int i = 0 ; i < classNameList.size(); i++){
                        //Log.d(AtyCSTag,classNameList.get(i).toString());
                        dataArray[i] = classNameList.get(i).getName();
                    }
                    picker = new OptionPicker(this,dataArray);
                    picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
                        @Override
                        public void onOptionPicked(int index, String item) {
                            chooseClassText.setText(dataArray[index]);
                            SharedPreferences.Editor editor1 = getSharedPreferences("User_YearCollege",MODE_PRIVATE).edit();
                            editor1.putString("className",classNameList.get(index).getName());
                            editor1.putString("classNumber",classNameList.get(index).getNumber());
                            editor1.commit();
                            String className = dataArray[index].toString();
                            pleasechooseclass.setText("选择完毕，请点击确定");
                            rxDayuan.getClass(
                                    classNameList.get(index).getName(),
                                    classNameList.get(index).getNumber(),
                                    sharedPreferences2.getString("term_value", "null"),
                                    sharedPreferences2.getString("term_name", "null"),
                                    new CallbackListener() {
                                        @Override
                                        public void callBack(int status, @NotNull String msg) {
                                            if (status != 1){
                                                Toast.makeText(ActivityChooseSchedule.this, "课程数据加载失败，请重试！", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }
                            );
                        }
                    });
                }
        }
        picker.setOffset(3);
        picker.setLineSpaceMultiplier(3);
        picker.setSelectedIndex(0);
        picker.setTextSize(18);
        try {
            picker.show();
        }catch (IllegalArgumentException e){
            Toast.makeText(this,"正在加载数据，请稍等 :)",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

}