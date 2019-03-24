package com.nenguou.dayuandaily.UI.Fragments

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.PorterDuff
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.constraint.ConstraintSet.PARENT_ID
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.view.GravityCompat
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.qqtheme.framework.entity.Province
import cn.qqtheme.framework.picker.AddressPicker
import com.nenguou.dayuandaily.DataBase.DayuanDailyDatabase
import com.nenguou.dayuandaily.Listener.CallbackListener
import com.nenguou.dayuandaily.R
import com.nenguou.dayuandaily.UI.ChooseEmptyClassroom
import com.nenguou.dayuandaily.Utils.RxDayuan
import kotlinx.android.synthetic.main.activity_main.view.*
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.cardview.v7.cardView
import org.jetbrains.anko.constraint.layout.constraintLayout
import org.jetbrains.anko.sdk27.coroutines.onClick

private lateinit var chooseEmptyClassroom: ChooseEmptyClassroom
private lateinit var mhandler: Handler

class FragmentSearchClassroom : Fragment() {
    private lateinit var dayuanDailyDatabase: DayuanDailyDatabase
    private lateinit var rxDayuan: RxDayuan
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        chooseEmptyClassroom = activity as ChooseEmptyClassroom
        mhandler = chooseEmptyClassroom.handler
        dayuanDailyDatabase = DayuanDailyDatabase.getInstance(context)
        rxDayuan = RxDayuan(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val container = FragmentSearchClassroomUI().createView(AnkoContext.Companion.create(context!!,FragmentSearchClassroom()))
        return container
    }

    fun onButtonPressed(uri: Uri) {
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

    }

    override fun onDetach() {
        super.onDetach()
    }


    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {

        @JvmStatic
        fun newInstance() =
                FragmentSearchClassroom().apply {
                }
    }
}

class FragmentSearchClassroomUI : AnkoComponent<FragmentSearchClassroom> {

    private lateinit var dayuanDailyDatabase: DayuanDailyDatabase
    private lateinit var rxDayuan: RxDayuan

    private var id_cardView = 0
    private var id_toolbar = 0
    private var id_sheduler_title = 0
    private var id_sheduler_place = 0
    private var id_sheduler_place_choose = 0
    private var id_sheduler_week = 0
    private var id_sheduler_week_choose = 0
    private var id_sheduler_time = 0
    private var id_sheduler_time_choose = 0
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var addressPicker: AddressPicker
    val campusList = mutableListOf<String>()
    //val campusMap = dayuanDailyDatabase.campusMap
    var campusMap = mutableMapOf<String,String>()
    var buildMap = mutableMapOf<String,String>()
    //val list = mutableListOf(Province)

    override fun createView(ui: AnkoContext<FragmentSearchClassroom>) = with(ui) {
        dayuanDailyDatabase = DayuanDailyDatabase.getInstance(ctx)
        campusMap = dayuanDailyDatabase.campusMap
        rxDayuan = RxDayuan(ctx)
        sharedPreferences = ctx.getSharedPreferences("RestClass",0)

        for (values in campusMap.values){
            if (null!=values && !values.equals("")){
                campusList.add(values)
            }
        }
        buildMap = dayuanDailyDatabase.getBuildMap(campusList[0])
        for (entry in campusMap.entries){
            Log.d("campusListTag",entry.key + " = " + entry.value)
        }

        for(entry in buildMap.entries){
            Log.d("campusListTag",entry.key + " = " + entry.value)
        }




        constraintLayout {
            backgroundColor = ContextCompat.getColor(ctx,R.color.colorBackground)

            toolbar {
                id_toolbar = View.generateViewId()
                id = id_toolbar
                backgroundColor = ContextCompat.getColor(ctx, R.color.colorToolbar)
                navigationIcon = ContextCompat.getDrawable(ctx,R.mipmap.back_bg)
                setNavigationOnClickListener {
                    chooseEmptyClassroom.finish()
                }
                textView("空教室查询"){
                    textSize = 20f
                    textColor = ContextCompat.getColor(ctx,R.color.colorWhite)
                }.lparams(){
                    gravity = Gravity.CENTER
                }
            }.lparams(width = matchParent) {
                topToTop = PARENT_ID
                startToStart = PARENT_ID
                endToEnd = PARENT_ID
            }

            cardView {
                id_cardView = View.generateViewId()
                id = id_cardView
                radius = 20f
                cardElevation = 30f
                background.setColorFilter(ContextCompat.getColor(ctx,R.color.colorWhite),PorterDuff.Mode.SRC_ATOP)
                constraintLayout {
                    // term 2018-2019
                    textView {
                        id_sheduler_title = View.generateViewId()
                        id = id_sheduler_title
                        text = sharedPreferences.getString("term_name","暂无数据");
                        textColor = ContextCompat.getColor(ctx,R.color.colorDeepBlackText)
                        textSize = 20f
                    }.lparams(){
                        topToTop = PARENT_ID
                        startToStart = PARENT_ID
                        leftMargin = dip(15)
                        topMargin = dip(10)
                    }

                    // place 地点
                    textView {
                        id_sheduler_place = View.generateViewId()
                        id = id_sheduler_place
                        textSize = 20f
                        text = "地点"
                    }.lparams(){
                        topToBottom = id_sheduler_title
                        startToStart = PARENT_ID
                        topMargin = dip(25)
                        leftMargin = dip(30)
                    }

                    // week number 周数
                    textView{
                        id_sheduler_week = View.generateViewId()
                        id = id_sheduler_week
                        text = "周数"
                        textSize = 20f
                    }.lparams(){
                        topToBottom = id_sheduler_place
                        startToStart = PARENT_ID
                        topMargin = dip(25)
                        leftMargin = dip(30)
                    }

                    // time 时间
                    textView{
                        id_sheduler_time = View.generateViewId()
                        id = id_sheduler_time
                        text = "时间"
                        textSize = 20f
                    }.lparams(){
                        topToBottom = id_sheduler_week
                        startToStart = PARENT_ID
                        topMargin = dip(25)
                        leftMargin = dip(30)
                    }

                    // place choose
                    textView{
                        id_sheduler_place_choose = View.generateViewId()
                        id = id_sheduler_place_choose
                        text = "请选择"
                        textColor = ContextCompat.getColor(ctx,R.color.colorLittleDeepBlackText)
                        textSize = 20f
                        leftPadding = dip(70)
                        onClick {
                            toast("choose place")
                            addressPicker = AddressPicker(ctx,campusList)
                        }
                    }.lparams(width = matchParent){
                        topToTop = id_sheduler_place
                        startToEnd = id_sheduler_place
                        rightToRight = PARENT_ID
                        bottomToBottom = id_sheduler_place
                        leftMargin = dip(60)
                    }

                    // week number choose
                    textView {
                        id_sheduler_week_choose = View.generateViewId()
                        id = id_sheduler_week_choose
                        text = "请选择"
                        textColor = ContextCompat.getColor(ctx,R.color.colorLittleDeepBlackText)
                        textSize = 20f
                        leftPadding = dip(70)
                        onClick {
                            toast("choose week")
                        }
                    }.lparams(width = matchParent){
                        topToTop = id_sheduler_week
                        startToEnd = id_sheduler_week
                        rightToRight = PARENT_ID
                        bottomToBottom = id_sheduler_week
                        leftMargin = dip(60)
                    }

                    // time choose
                    textView {
                        id_sheduler_time_choose = View.generateViewId()
                        id = id_sheduler_time_choose
                        text = "请选择"
                        textColor = ContextCompat.getColor(ctx,R.color.colorLittleDeepBlackText)
                        textSize = 20f
                        leftPadding = dip(70)
                        onClick {
                            toast("choose time")
                        }
                    }.lparams(width = matchParent){
                        topToTop = id_sheduler_time
                        startToEnd = id_sheduler_time
                        rightToRight = PARENT_ID
                        bottomToBottom = id_sheduler_time
                        leftMargin = dip(60)
                    }

                    textView("▶︎") {

                    }.lparams(){
                        topToTop = id_sheduler_place
                        bottomToBottom = id_sheduler_place
                        endToEnd = PARENT_ID
                        rightMargin = dip(20)
                    }

                    textView("▶︎") {

                    }.lparams(){
                        topToTop = id_sheduler_week
                        bottomToBottom = id_sheduler_week
                        endToEnd = PARENT_ID
                        rightMargin = dip(20)
                    }

                    textView("▶︎") {

                    }.lparams(){
                        topToTop = id_sheduler_time
                        bottomToBottom = id_sheduler_time
                        endToEnd = PARENT_ID
                        rightMargin = dip(20)
                    }

                    // query btn
                    button("自习一下"){
                        onClick {
                            /*rxDayuan.getRestClass(object :CallbackListener{
                                override fun callBack(status: Int, msg: String) {
                                    toast(msg)
                                }

                            })*/

                            val buildLst = mutableListOf<String>()



                            val buildsMap = dayuanDailyDatabase.getBuildMap("01")
                            for (values in buildsMap.values){
                                if(null != values && !values.equals("")){
                                    buildLst.add(values)
                                }else{
                                    toast("没有查询到数据")
                                }
                            }



                        }
                    }.lparams(){
                        topToBottom = id_sheduler_time
                        startToStart = PARENT_ID
                        endToEnd = PARENT_ID
                        bottomToBottom = PARENT_ID
                        topMargin = dip(20)
                        bottomMargin = dip(20)
                    }

                }.lparams(width = matchParent, height = wrapContent)
            }.lparams(width = matchParent, height = wrapContent) {
                leftMargin = dip(15)
                topMargin = dip(15)
                rightMargin = dip(15)
                topToBottom = id_toolbar
                startToStart = PARENT_ID
                endToEnd = PARENT_ID
            }


        }
    }

}
