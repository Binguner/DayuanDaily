package com.nenguou.dayuandaily.UI

import android.content.Context
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v4.app.FragmentManager
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.View
import com.nenguou.dayuandaily.R
import com.nenguou.dayuandaily.UI.Fragments.FragmentClassroomResult
import com.nenguou.dayuandaily.UI.Fragments.FragmentSearchClassroom
import org.jetbrains.anko.*
import org.jetbrains.anko.constraint.layout.constraintLayout

/**
 * SharedPreferences : RestClass
 * term_name: 2018-2019学年秋(两学期)
 * term_value: 2018-2019-1-1
 * last_load_time: 1540472923274
 */

class ChooseEmptyClassroom : AppCompatActivity() {

    private var searchFragment : FragmentSearchClassroom? = null
    private var searchResultResultFragment: FragmentClassroomResult? = null
    private var mfragmentManager: FragmentManager? = null
    lateinit var editor: SharedPreferences.Editor
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.myAppTheme)
        ChooseEmptyClassroomUi().setContentView(this)
        initFragments()
        editor = getSharedPreferences("RestClass", Context.MODE_PRIVATE).edit()
        sharedPreferences = getSharedPreferences("RestClass", Context.MODE_PRIVATE)
    }

    fun initFragments(){
        if ( null == searchFragment ){
            searchFragment = FragmentSearchClassroom.newInstance()
        }
        if (null == searchResultResultFragment){
            searchResultResultFragment = FragmentClassroomResult.newInstance()
        }
        mfragmentManager = supportFragmentManager
        mfragmentManager?.beginTransaction()?.add(containerId!!,searchFragment)?.add(containerId!!,searchResultResultFragment)?.commit()
        mfragmentManager?.beginTransaction()?.hide(searchResultResultFragment)?.commit()
    }

    val handler = Handler{
        when(it.what){

        }
        true
    }


}
var containerId : Int? = null
lateinit var sharedPreferences: SharedPreferences

class ChooseEmptyClassroomUi:AnkoComponent<ChooseEmptyClassroom> {

    override fun createView(ui: AnkoContext<ChooseEmptyClassroom>)= with(ui){
        frameLayout{
            containerId = View.generateViewId()
            id = containerId!!.toInt()
            backgroundColor = ContextCompat.getColor(ctx,R.color.colorBackground )
        }

    }

}
