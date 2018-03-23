package com.nenguou.dayuandaily.UI;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nenguou.dayuandaily.DataBase.DayuanDailyDatabase;
import com.nenguou.dayuandaily.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.internal.operators.parallel.ParallelFromPublisher;

/**
 * Created by binguner on 2018/2/21.
 */

public class MyScheduleButton extends android.support.v7.widget.AppCompatButton {

    Context context;
    int weekNum;
    int classNum;
    int schedule_id;
    String class_name;
    String weeks;
    String[] weeksList;
    String newString1;
    String newString2;
    String newString3;
    String detial;

    int selectedTime;   // 选择的周数
    int week_list_num  = 0;  // 使用的数组的下表

    List<List<Integer>> all_class_week_list = new ArrayList<>();    // 保存 {{1,2,3,4,5},{1,2,3,4},{3,2,7,5,6}}
    List<Integer> theListOfTheClassWhichContainsTheSelectTime = new ArrayList<>();  // 包含有当前周数的课程名字

    List<String> class_name_list; // 这一节课所有的 课程名字
    List<String> weeks_list; // 所有上课周数的 List，未进行 split
    int[] weeksArray;   // 根据 weeks_list split 出的某一节课的 数组
    List<String> length_list;  // 课程的 长度 的 list

    List<String> rawWeeks_list;  // 所有课程的 rawWeeks 的 List
    List<String> places_list;
    List<String> teacher_list;


    private static final String Tag = "MySheduleButtonTag";

    DayuanDailyDatabase dayuanDailyDatabase;
    public MyScheduleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initData(attrs);
        initBtnView();
        // 只在第一次进入界面的时候调用
        showOrNot();
    }

    // 供外部调用：指定当前选定的周数，选定后 initBtnView()
    public void setSelectedTime(int time){
        this.selectedTime = time;
        initBtnView();
    }

    /**
     * 判断该按钮显不显示，若 按钮 内的字符为 空，则 INVISIBLE，且不可点击
     * 若 按钮 内的字符部位空，则 VISIBLE，且可点击
     */
    private void showOrNot() {
        if("".equals(this.getText())){
            //this.setBackgroundColor(Color.TRANSPARENT);
            this.setVisibility(INVISIBLE);
            this.setClickable(false);
        }else if(!"".equals(this.getText())){
            this.setVisibility(VISIBLE);
            this.setClickable(true);
        }
    }

    /**
     * @return 返回 这节课 上多久
     */
    public int getLength(){
        if(this.length_list.size() > 0) {
            return Integer.parseInt(this.length_list.get(0));
        }
        return 0;
    }


    /**
     * @return 返回这节课的上课周数的数组，用于匹配
     */
    public int[] getWeekList(){
        return weeksArray;
    }

    public void getDetial(){
        Toast.makeText(context,detial,Toast.LENGTH_SHORT).show();
    }

    private void initBtnView() {

        theListOfTheClassWhichContainsTheSelectTime.clear();
        for(int i = 0 ; i < all_class_week_list.size(); i++ ){  // {{1,2},{1,2,3}}
            List<Integer> list = all_class_week_list.get(i);    //{1,2}
            for(int j : list){
                if (j == selectedTime){
                    theListOfTheClassWhichContainsTheSelectTime.add(i);     //{1}
                    //Log.d(Tag, "i = " + i);
                }
            }
        }

        this.setTextSize(12);

        if(all_class_week_list.size() > 0){
            int count = 0;
            int allNumebr = 0;
            for(List<Integer> list: all_class_week_list){
                allNumebr += list.size();
            }
            weeksArray = new int[allNumebr];
            for(int i = 0; i < all_class_week_list.size(); i++){
                List<Integer> list = all_class_week_list.get(i);

                for(int j = 0; j < list.size(); j++){
                    weeksArray[count] = list.get(j);
                    count++;
                }
            }
        }

        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(context,places_list.get(0) + " " + teacher_list.get(0) + " " + rawWeeks_list.get(0),Toast.LENGTH_SHORT).show();
                //Log.d()
                if(theListOfTheClassWhichContainsTheSelectTime.size() > 1){
                    initAlertDialogToChoose();
                }else {
                    initAlerDialog(theListOfTheClassWhichContainsTheSelectTime.get(0));
                }
                //Log.d(Tag,theListOfTheClassWhichContainsTheSelectTime.size()+"");
            }
        });

        if(class_name_list.size() > 0){
            String mString = "";
            for (int j = 0; j < theListOfTheClassWhichContainsTheSelectTime.size(); j++) {
                if(theListOfTheClassWhichContainsTheSelectTime.size() > 1) {
                    //Log.d(Tag,"theListOfTheClassWhichContainsTheSelectTime has : " + theListOfTheClassWhichContainsTheSelectTime.toString() );
                    //Log.d(Tag,class_name_list.get(theListOfTheClassWhichContainsTheSelectTime.get(j)));
                    //Log.d(Tag," first theListOfTheClassWhichContainsTheSelectTime is :" + theListOfTheClassWhichContainsTheSelectTime);
                    if(!mString.contains(class_name_list.get(theListOfTheClassWhichContainsTheSelectTime.get(j)))){
                        mString = mString +" "+class_name_list.get(theListOfTheClassWhichContainsTheSelectTime.get(j));
                    }
                }else {
                    mString = class_name_list.get(theListOfTheClassWhichContainsTheSelectTime.get(0));
                }
            }
            //this.setText(class_name_list.get(week_list_num));
            this.setText(mString);
            //Log.d(Tag,mString);
        }
    }
    private void initAlerDialog(int item_num){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.pop_show_detial_layout,null);
        TextView pop_class_name = view.findViewById(R.id.pop_class_name);
        pop_class_name.setText(class_name_list.get(item_num));
        TextView pop_classroom_name = view.findViewById(R.id.pop_classroom_name);
        pop_classroom_name.setText(places_list.get(item_num));
        //Log.d(Tag,places_list.get(item_num)+"");
        TextView pop_rawweeks_text = view.findViewById(R.id.pop_rawweeks_text);
        pop_rawweeks_text.setText(rawWeeks_list.get(item_num));
        TextView pop_class_number_text = view.findViewById(R.id.pop_class_number_text);
        int len = classNum + Integer.parseInt(length_list.get(item_num)) -1;
        pop_class_number_text.setText(classNum+"-"+ len +" 节");
        TextView pop_class_teacher_text = view.findViewById(R.id.pop_class_teacher_text );
        pop_class_teacher_text.setText(teacher_list.get(item_num));

        builder.setView(view);
        final AlertDialog alertDialog = builder.show();

        CardView cardView = view.findViewById(R.id.pop_card_view);
        cardView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        //alertDialog.setContentView(R.layout.pop_show_detial_layout);
        Window window = alertDialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        window.setWindowAnimations(R.style.Theme_AppCompat_Dialog_Alert);

    }

    private void initAlertDialogToChoose(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.pop_choose_one_class_layout,null);
        ListView class_item_listview = view.findViewById(R.id.class_item_listview);
        String [] data = new String[theListOfTheClassWhichContainsTheSelectTime.size()];
        String test = "";
        //Log.d(Tag,"theListOfTheClassWhichContainsTheSelectTime is : " + theListOfTheClassWhichContainsTheSelectTime);
        for(int i = 0 ; i < theListOfTheClassWhichContainsTheSelectTime.size(); i++){
            //if(!test.contains(class_name_list.get(theListOfTheClassWhichContainsTheSelectTime.get(i)))){
                data[i] = class_name_list.get(theListOfTheClassWhichContainsTheSelectTime.get(i));
                //test = test + class_name_list.get();
            //}
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1,data);

        class_item_listview.setAdapter(arrayAdapter);
        builder.setView(view);
        final AlertDialog alertDialog = builder.show();
        class_item_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                initAlerDialog(theListOfTheClassWhichContainsTheSelectTime.get(i));
                alertDialog.dismiss();
            }
        });
        Window window = alertDialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        window.setWindowAnimations(R.style.Theme_AppCompat_Dialog_Alert);
    }

    public int getTheClassNumber(){
        return class_name_list.size();
    }

    private void initData(AttributeSet attrs) {
        rawWeeks_list = new ArrayList<>();
        class_name_list = new ArrayList<>();
        weeks_list = new ArrayList<>();
        length_list = new ArrayList<>();
        places_list = new ArrayList<>();
        teacher_list = new ArrayList<>();
        SharedPreferences sharedPreferences = context.getSharedPreferences("User_YearCollege",Context.MODE_PRIVATE);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyScheduleButton);
        weekNum = typedArray.getInteger(R.styleable.MyScheduleButton_weekNum,1);
        classNum = typedArray.getInteger(R.styleable.MyScheduleButton_classNum,1);
        dayuanDailyDatabase = DayuanDailyDatabase.getInstance(context);
        schedule_id = sharedPreferences.getInt("schedule_id",9514);

        try {
            class_name_list = dayuanDailyDatabase.loadSchedules(DayuanDailyDatabase.TYPE_GET_SUB_NAME, schedule_id, weekNum, classNum);
        }catch (Exception e){
            Toast.makeText(context,"数据加载失败，请重试",Toast.LENGTH_SHORT).show();
        }

        //Log.d(Tag,"class_Name is : "+ class_name +" Schedule_id is : "+ schedule_id + " weekNum is : " + weekNum + " classNum is : " + classNum);
        if(class_name_list.size() > 0) {
            length_list = dayuanDailyDatabase.loadSchedules(DayuanDailyDatabase.TYPE_GET_SUB_LENGTH,schedule_id,weekNum,classNum);
            teacher_list = dayuanDailyDatabase.loadSchedules(DayuanDailyDatabase.TYPE_GET_SUB_TEACHER,schedule_id,weekNum,classNum);
            ///weeks = dayuanDailyDatabase.loadSchedules(DayuanDailyDatabase.TYPE_GET_SUB_WEEKS, schedule_id, weekNum, classNum);
            weeks_list = dayuanDailyDatabase.loadSchedules(DayuanDailyDatabase.TYPE_GET_SUB_WEEKS, schedule_id, weekNum, classNum);
            rawWeeks_list = dayuanDailyDatabase.loadSchedules(DayuanDailyDatabase.TYPE_GET_SUB_RAWWEEK,schedule_id,weekNum,classNum);
            places_list = dayuanDailyDatabase.loadSchedules(DayuanDailyDatabase.TYPE_GET_SUB_PLACE,schedule_id,weekNum,classNum);
            detial = dayuanDailyDatabase.loadSchedules(DayuanDailyDatabase.TYPE_GET_SUB_PLACE,schedule_id,weekNum,classNum)+" "+ dayuanDailyDatabase.loadSchedules(DayuanDailyDatabase.TYPE_GET_SUB_TEACHER,schedule_id,weekNum,classNum);
            if(weeks_list.size() > 0){
                for(int i = 0 ; i < weeks_list.size() ; i++) {
                    newString1 = weeks_list.get(i).replace("]", "");
                    newString2 = newString1.replace("[", "");
                    newString3 = newString2.replace(" ", "");
                    String [] temWeeksList = newString3.split(",");
                    List<Integer> weeksArrayList = new ArrayList<>();
                    for (int j = 0; j < temWeeksList.length; j++) {
                        weeksArrayList.add(Integer.parseInt(temWeeksList[j]));
                    }
                    all_class_week_list.add(weeksArrayList);
                }
            }
        }
    }
}
