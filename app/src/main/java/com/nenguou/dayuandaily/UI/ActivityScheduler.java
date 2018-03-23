package com.nenguou.dayuandaily.UI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.nenguou.dayuandaily.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ActivityScheduler extends AppCompatActivity {

    @BindView(R.id.ct_1_mon) MyScheduleButton ct_1_mon;
    @BindView(R.id.ct_1_tues) MyScheduleButton ct_1_tues;
    @BindView(R.id.ct_1_wen) MyScheduleButton ct_1_wen;
    @BindView(R.id.ct_1_thur) MyScheduleButton ct_1_thur;
    @BindView(R.id.ct_1_fri) MyScheduleButton ct_1_fri;
    @BindView(R.id.ct_1_sat) MyScheduleButton ct_1_sat;
    @BindView(R.id.ct_1_sun) MyScheduleButton ct_1_sun;

    @BindView(R.id.ct_2_mon) MyScheduleButton ct_2_mon;
    @BindView(R.id.ct_2_tues) MyScheduleButton ct_2_tues;
    @BindView(R.id.ct_2_wen) MyScheduleButton ct_2_wen;
    @BindView(R.id.ct_2_thur) MyScheduleButton ct_2_thur;
    @BindView(R.id.ct_2_fri) MyScheduleButton ct_2_fri;
    @BindView(R.id.ct_2_sat) MyScheduleButton ct_2_sat;
    @BindView(R.id.ct_2_sun) MyScheduleButton ct_2_sun;

    @BindView(R.id.ct_3_mon) MyScheduleButton ct_3_mon;
    @BindView(R.id.ct_3_tues) MyScheduleButton ct_3_tues;
    @BindView(R.id.ct_3_wen) MyScheduleButton ct_3_wen;
    @BindView(R.id.ct_3_thur) MyScheduleButton ct_3_thur;
    @BindView(R.id.ct_3_fri) MyScheduleButton ct_3_fri;
    @BindView(R.id.ct_3_sat) MyScheduleButton ct_3_sat;
    @BindView(R.id.ct_3_sun) MyScheduleButton ct_3_sun;

    @BindView(R.id.ct_4_mon) MyScheduleButton ct_4_mon;
    @BindView(R.id.ct_4_tues) MyScheduleButton ct_4_tues;
    @BindView(R.id.ct_4_wen) MyScheduleButton ct_4_wen;
    @BindView(R.id.ct_4_thur) MyScheduleButton ct_4_thur;
    @BindView(R.id.ct_4_fri) MyScheduleButton ct_4_fri;
    @BindView(R.id.ct_4_sat) MyScheduleButton ct_4_sat;
    @BindView(R.id.ct_4_sun) MyScheduleButton ct_4_sun;

    @BindView(R.id.ct_5_mon) MyScheduleButton ct_5_mon;
    @BindView(R.id.ct_5_tues) MyScheduleButton ct_5_tues;
    @BindView(R.id.ct_5_wen) MyScheduleButton ct_5_wen;
    @BindView(R.id.ct_5_thur) MyScheduleButton ct_5_thur;
    @BindView(R.id.ct_5_fri) MyScheduleButton ct_5_fri;
    @BindView(R.id.ct_5_sat) MyScheduleButton ct_5_sat;
    @BindView(R.id.ct_5_sun) MyScheduleButton ct_5_sun;

    @BindView(R.id.ct_6_mon) MyScheduleButton ct_6_mon;
    @BindView(R.id.ct_6_tues) MyScheduleButton ct_6_tues;
    @BindView(R.id.ct_6_wen) MyScheduleButton ct_6_wen;
    @BindView(R.id.ct_6_thur) MyScheduleButton ct_6_thur;
    @BindView(R.id.ct_6_fri) MyScheduleButton ct_6_fri;
    @BindView(R.id.ct_6_sat) MyScheduleButton ct_6_sat;
    @BindView(R.id.ct_6_sun) MyScheduleButton ct_6_sun;

    @BindView(R.id.ct_7_mon) MyScheduleButton ct_7_mon;
    @BindView(R.id.ct_7_tues) MyScheduleButton ct_7_tues;
    @BindView(R.id.ct_7_wen) MyScheduleButton ct_7_wen;
    @BindView(R.id.ct_7_thur) MyScheduleButton ct_7_thur;
    @BindView(R.id.ct_7_fri) MyScheduleButton ct_7_fri;
    @BindView(R.id.ct_7_sat) MyScheduleButton ct_7_sat;
    @BindView(R.id.ct_7_sun) MyScheduleButton ct_7_sun;

    @BindView(R.id.ct_8_mon) MyScheduleButton ct_8_mon;
    @BindView(R.id.ct_8_tues) MyScheduleButton ct_8_tues;
    @BindView(R.id.ct_8_wen) MyScheduleButton ct_8_wen;
    @BindView(R.id.ct_8_thur) MyScheduleButton ct_8_thur;
    @BindView(R.id.ct_8_fri) MyScheduleButton ct_8_fri;
    @BindView(R.id.ct_8_sat) MyScheduleButton ct_8_sat;
    @BindView(R.id.ct_8_sun) MyScheduleButton ct_8_sun;

    @BindView(R.id.ct_9_mon) MyScheduleButton ct_9_mon;
    @BindView(R.id.ct_9_tues) MyScheduleButton ct_9_tues;
    @BindView(R.id.ct_9_wen) MyScheduleButton ct_9_wen;
    @BindView(R.id.ct_9_thur) MyScheduleButton ct_9_thur;
    @BindView(R.id.ct_9_fri) MyScheduleButton ct_9_fri;
    @BindView(R.id.ct_9_sat) MyScheduleButton ct_9_sat;
    @BindView(R.id.ct_9_sun) MyScheduleButton ct_9_sun;

    @BindView(R.id.ct_10_mon) MyScheduleButton ct_10_mon;
    @BindView(R.id.ct_10_tues) MyScheduleButton ct_10_tues;
    @BindView(R.id.ct_10_wen) MyScheduleButton ct_10_wen;
    @BindView(R.id.ct_10_thur) MyScheduleButton ct_10_thur;
    @BindView(R.id.ct_10_fri) MyScheduleButton ct_10_fri;
    @BindView(R.id.ct_10_sat) MyScheduleButton ct_10_sat;
    @BindView(R.id.ct_10_sun) MyScheduleButton ct_10_sun;

    @BindView(R.id.ct_11_mon) MyScheduleButton ct_11_mon;
    @BindView(R.id.ct_11_tues) MyScheduleButton ct_11_tues;
    @BindView(R.id.ct_11_wen) MyScheduleButton ct_11_wen;
    @BindView(R.id.ct_11_thur) MyScheduleButton ct_11_thur;
    @BindView(R.id.ct_11_fri) MyScheduleButton ct_11_fri;
    @BindView(R.id.ct_11_sat) MyScheduleButton ct_11_sat;
    @BindView(R.id.ct_11_sun) MyScheduleButton ct_11_sun;

    @BindView(R.id.search_schedule) ImageView search_schedule;
    @BindView(R.id.schedule_toolbar) Toolbar schedule_toolbar;
    @BindView(R.id.choose_week_sideBar) MySideBar choose_week_sideBar;

    List<MyScheduleButton> list_mon;
    List<MyScheduleButton> list_tues;
    List<MyScheduleButton> list_wen;
    List<MyScheduleButton> list_thur;
    List<MyScheduleButton> list_fri;
    List<MyScheduleButton> list_sat;
    List<MyScheduleButton> list_sun;
//    AlertDialog alertDialog = null;
//    AlertDialog.Builder builder = null;
    private static final String sTag= "AtySchedulerTag";
    private static int selectedTime;
    private boolean isScheduleSelected = false;
    int nowTime = 0;
    String theSelectedTime;
    SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.myAppTheme);
        super.onCreate(savedInstanceState);
        isFitstLoad();
        setContentView(R.layout.activity_scheduler);
        ButterKnife.bind(this);
        initData();
        initViews();
        checkTheLastTime();
        //initAlerDialog();
    }

    private void checkTheLastTime() {
        ///*SharedPreferences*/ sharedPreferences = getSharedPreferences("User_YearCollege",MODE_PRIVATE);
        theSelectedTime = sharedPreferences.getString("theSelectedTime","");
        Log.d("sadfas",theSelectedTime +" ");
        if(theSelectedTime.equals("A")){

        }else if(theSelectedTime.equals("N")){
            MySideBar.setTheSelectedOne("N");
            selectedTime = nowTime;
        }else if(theSelectedTime != ""){
            MySideBar.setTheSelectedOne(theSelectedTime);
            selectedTime = Integer.parseInt(theSelectedTime);
        }
        refreshSchedule(list_mon);
        refreshSchedule(list_tues);
        refreshSchedule(list_wen);
        refreshSchedule(list_thur);
        refreshSchedule(list_fri);
        refreshSchedule(list_sat);
        refreshSchedule(list_sun);

    }

    private void isFitstLoad() {
        editor = getSharedPreferences("User_YearCollege",MODE_PRIVATE).edit();
        sharedPreferences = getSharedPreferences("User_YearCollege",MODE_PRIVATE);
        nowTime = (int) (((System.currentTimeMillis() - sharedPreferences.getLong("start",1)) / 604800000)) + 1;
        isScheduleSelected = sharedPreferences.getBoolean("isScheduleSelected",false);
        if(!isScheduleSelected){
            Intent intent = new Intent(this,ActivityChooseSchedule.class);
            startActivity(intent);
            this.finish();
        }
    }

    @OnClick(R.id.search_schedule)
    public void goToSearchSchedule(View view){
        Intent intent = new Intent(this,ActivityChooseSchedule.class);
        startActivity(intent);
        this.finish();
    }

    private void initViews() {
//        hideTheRepeatedClass(list_mon);
//        hideTheRepeatedClass(list_tues);
//        hideTheRepeatedClass(list_wen);
//        hideTheRepeatedClass(list_thur);
//        hideTheRepeatedClass(list_fri);
//        hideTheRepeatedClass(list_sat);
//        hideTheRepeatedClass(list_sun);
        choose_week_sideBar.setOnSeleceListener(new MySideBar.onSeleceListener() {
            @Override
            public void onSelect(String s) {
                //Log.d(sTag,s+"");
                //MyScheduleButton.setSelectedTime(s);
                //Toast.makeText(ActivityScheduler.this,s,Toast.LENGTH_SHORT).show();
                MySideBar.setTheSelectedOne(s);
                editor.putString("theSelectedTime",s);
                editor.commit();
                if(s.equals("A")){
                    selectedTime = 0;
                }else if(s.equals("N")){
                    Toast.makeText(ActivityScheduler.this,"现在是第 " + nowTime + " 周",Toast.LENGTH_SHORT).show();
                    selectedTime = nowTime;
                }else {
                    selectedTime = Integer.parseInt(s);
                }
                try {
                    refreshSchedule(list_mon);
                    refreshSchedule(list_tues);
                    refreshSchedule(list_wen);
                    refreshSchedule(list_thur);
                    refreshSchedule(list_fri);
                    refreshSchedule(list_sat);
                    refreshSchedule(list_sun);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onMoveUp(String s) {
                //MyScheduleButton.setSelectedTime(s);
                //Log.d(sTag,s+"");
            }
        });

        repeatTheButton(list_mon);
        repeatTheButton(list_tues);
        repeatTheButton(list_wen);
        repeatTheButton(list_thur);
        repeatTheButton(list_fri);
        repeatTheButton(list_sat);
        repeatTheButton(list_sun);
        schedule_toolbar.setNavigationIcon(R.mipmap.back_bg);
        schedule_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                ActivityScheduler.this.finish();
            }
        });
        //ct_1_mon.getLayoutParams().height = 464; 60 = 240
    }

    /**
     * 点击一次 滑动侧边脸 就根据滑动到位置，刷新页面，设置当前课表是否可见
     * 如果当前的 课 有 text ，则现实
     * @param list 该天的所有课程的 List
     */
    private void refreshSchedule(List<MyScheduleButton> list) {
        for(MyScheduleButton button : list){
            //int oldBtnWidth = button.getLayoutParams().width;
            //int oldBtnHeight = button.getLayoutParams().height;
            button.setVisibility(View.INVISIBLE);
            button.setSelectedTime(selectedTime);
            if(button.getText()!=null && !button.getText().equals("")){

//                button.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        //alertDialog.show();
//                        //initAlerDialog();
//                        Toast.makeText(ActivityScheduler.this,"Clicked",Toast.LENGTH_SHORT).show();
//                    }
//                });
                int[] weekArray = button.getWeekList();
               // Log.d(sTag, "=============");
                for(int i : weekArray) {
                 //   Log.d(sTag, "i = " + i);
                    if( i == selectedTime){
                        button.setVisibility(View.VISIBLE);
                    }else if(selectedTime == 0){
                        button.setVisibility(View.VISIBLE);
                    }
                }

            }
//            if(button.getTheClassNumber() >= 2){
//                //int oldBtnWidth = button.getLayoutParams().width;
//
//                Log.d(sTag,"oldBtnWidth is : "+ oldBtnWidth);
//                Log.d(sTag,"oldBtnHeight is : "+ oldBtnHeight);
//                button.setVisibility(View.INVISIBLE);
//                Log.d(sTag,"Parent is : " + button.getParent()+" id : " + button.getId() + " textSize is :" + button.getTheClassNumber());
//                ConstraintLayout constraintLayout = (ConstraintLayout) button.getParent();
//                LinearLayout linearLayout = new LinearLayout(this);
//                linearLayout.setOrientation(LinearLayout.HORIZONTAL);
//                linearLayout.setLayoutParams(button.getLayoutParams());
//                linearLayout.setBackgroundColor(Color.TRANSPARENT);
//                for(int i = 0 ; i < button.getTheClassNumber(); i++){
//                    MyScheduleButton myScheduleButton = new MyScheduleButton(this,button.getClassNum(),button.getWeekNum());
//                    myScheduleButton.setHeight(oldBtnHeight);
//                    myScheduleButton.setMinWidth(20);
////                    myScheduleButton
//                    myScheduleButton.setMinimumWidth(oldBtnWidth/button.getTheClassNumber());
//                    myScheduleButton.setWidth(20);
//                    //myScheduleButton.getLayoutParams().width = oldBtnWidth/button.getTheClassNumber();
//                    linearLayout.addView(myScheduleButton);
//                    Log.d(sTag,"Height is : "+linearLayout.getLayoutParams().height + " Width is : " + linearLayout.getLayoutParams().width);
//                }
//                constraintLayout.addView(linearLayout,button.getLayoutParams());
//                //constraintLayout.addView(linearLayout,100,400);
//            }
        }
    }

    /**
     * 根据每个按钮中课程的 上课几节 (length) 重复当前的课
     * @param list 该天的所有课程的 List
     */
    private void repeatTheButton(List<MyScheduleButton> list) {
        for(int i = 0 ; i < list.size(); i++){
            int length = list.get(i).getLength();
            int oldHeight = list.get(i).getLayoutParams().height;
            list.get(i).getLayoutParams().height = oldHeight * length;
        }
    }

    private void hideTheRepeatedClass(List<MyScheduleButton> list){
        for(int i = 1; i < list.size(); i++){
            if(list.get(i).getText().equals(list.get(i-1).getText())){
                int thisBtnHeight = list.get(i).getLayoutParams().height;
                if(!list.get(i).getText().equals("")) {
                    list.get(i).setVisibility(View.INVISIBLE);
                }
                //int w = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
                //int h = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
                //list.get(i-1).measure(w,h);
                //int oldHeight = list.get(i-1).getMeasuredHeight();//192 / 58 =



                //int oldHeight = 192;//192 / 58 =
                int oldHeight = list.get(i-1).getLayoutParams().height;//192 / 58 =

                int newHeight = oldHeight + thisBtnHeight;
                float xDpi = getResources().getDisplayMetrics().xdpi;
                float yDpi = getResources().getDisplayMetrics().ydpi;
                //list.get(i).
                //list.get(i).setLayoutParams();
                int j = 1;
                while (list.get(i-j).getVisibility() == View.INVISIBLE){
                    j++;
                    //Log.d(sTag,"The j is : "+ j+"");
                }
                //if(!list.get(i).getText().equals("")) {
                    list.get(i - j).getLayoutParams().height = oldHeight + oldHeight * j;
                //}
                //Log.d(sTag,"Text is :"+list.get(i).getText()+"  list size is :"+ list.size()+"  xdpi and ydpi are: "+xDpi+" "+yDpi    +" old Height is :" + oldHeight + "  new Height is : "+ newHeight+"  text is:" + list.get(i-1).getText());
            }
        }
    }

    //private void

    /**
     * 把每节课装在 当天的 List 里
     */
    private void initData() {
        list_mon = new ArrayList<>();
        list_mon.add(ct_1_mon);
        list_mon.add(ct_2_mon);
        list_mon.add(ct_3_mon);
        list_mon.add(ct_4_mon);
        list_mon.add(ct_5_mon);
        list_mon.add(ct_6_mon);
        list_mon.add(ct_7_mon);
        list_mon.add(ct_8_mon);
        list_mon.add(ct_9_mon);
        list_mon.add(ct_10_mon);
        list_mon.add(ct_11_mon);

        list_tues = new ArrayList<>();
        list_tues.add(ct_1_tues);
        list_tues.add(ct_2_tues);
        list_tues.add(ct_3_tues);
        list_tues.add(ct_4_tues);
        list_tues.add(ct_5_tues);
        list_tues.add(ct_6_tues);
        list_tues.add(ct_7_tues);
        list_tues.add(ct_8_tues);
        list_tues.add(ct_9_tues);
        list_tues.add(ct_10_tues);
        list_tues.add(ct_11_tues);

        list_wen = new ArrayList<>();
        list_wen.add(ct_1_wen);
        list_wen.add(ct_2_wen);
        list_wen.add(ct_3_wen);
        list_wen.add(ct_4_wen);
        list_wen.add(ct_5_wen);
        list_wen.add(ct_6_wen);
        list_wen.add(ct_7_wen);
        list_wen.add(ct_8_wen);
        list_wen.add(ct_9_wen);
        list_wen.add(ct_10_wen);
        list_wen.add(ct_11_wen);

        list_thur = new ArrayList<>();
        list_thur.add(ct_1_thur);
        list_thur.add(ct_2_thur);
        list_thur.add(ct_3_thur);
        list_thur.add(ct_4_thur);
        list_thur.add(ct_5_thur);
        list_thur.add(ct_6_thur);
        list_thur.add(ct_7_thur);
        list_thur.add(ct_8_thur);
        list_thur.add(ct_9_thur);
        list_thur.add(ct_10_thur);
        list_thur.add(ct_11_thur);

        list_fri = new ArrayList<>();
        list_fri.add(ct_1_fri);
        list_fri.add(ct_2_fri);
        list_fri.add(ct_3_fri);
        list_fri.add(ct_4_fri);
        list_fri.add(ct_5_fri);
        list_fri.add(ct_6_fri);
        list_fri.add(ct_7_fri);
        list_fri.add(ct_8_fri);
        list_fri.add(ct_9_fri);
        list_fri.add(ct_10_fri);
        list_fri.add(ct_11_fri);

        list_sat = new ArrayList<>();
        list_sat.add(ct_1_sat);
        list_sat.add(ct_2_sat);
        list_sat.add(ct_3_sat);
        list_sat.add(ct_4_sat);
        list_sat.add(ct_5_sat);
        list_sat.add(ct_6_sat);
        list_sat.add(ct_7_sat);
        list_sat.add(ct_8_sat);
        list_sat.add(ct_9_sat);
        list_sat.add(ct_10_sat);
        list_sat.add(ct_11_sat);

        list_sun = new ArrayList<>();
        list_sun.add(ct_1_sun);
        list_sun.add(ct_2_sun);
        list_sun.add(ct_3_sun);
        list_sun.add(ct_4_sun);
        list_sun.add(ct_5_sun);
        list_sun.add(ct_6_sun);
        list_sun.add(ct_7_sun);
        list_sun.add(ct_8_sun);
        list_sun.add(ct_9_sun);
        list_sun.add(ct_10_sun);
        list_sun.add(ct_11_sun);
    }
//    private void initAlerDialog(){
//        AlertDialog alertDialog = null;
//        AlertDialog.Builder builder = null;
//        builder = new AlertDialog.Builder(this);
//        alertDialog = builder.show();
//        alertDialog.setContentView(R.layout.pop_show_detial_layout);
//        Window window = alertDialog.getWindow();
//        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
//        window.setGravity(Gravity.BOTTOM);
//        window.setWindowAnimations(R.style.Theme_AppCompat_Dialog_Alert);
//    }
}
