package com.nenguou.dayuandaily.UI

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v4.app.FragmentManager
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.View
import com.nenguou.dayuandaily.R
import com.nenguou.dayuandaily.UI.Fragments.FragmentClassroomResule
import com.nenguou.dayuandaily.UI.Fragments.FragmentSearchClassroom
import org.jetbrains.anko.*
import org.jetbrains.anko.constraint.layout.constraintLayout

class ChooseEmptyClassroom : AppCompatActivity() {

    var searchFragment : FragmentSearchClassroom? = null
    var searchResuleResuleFragment: FragmentClassroomResule? = null
    var mfragmentManager: FragmentManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.myAppTheme)
        ChooseEmptyClassroomUi().setContentView(this)
        initFragments()
    }

    fun initFragments(){
        if ( null == searchFragment ){
            searchFragment = FragmentSearchClassroom.newInstance()
        }
        if (null == searchResuleResuleFragment){
            searchResuleResuleFragment = FragmentClassroomResule.newInstance()
        }
        mfragmentManager = supportFragmentManager
        mfragmentManager?.beginTransaction()?.add(containerId!!,searchFragment)?.add(containerId!!,searchResuleResuleFragment)?.commit()
        mfragmentManager?.beginTransaction()?.hide(searchResuleResuleFragment)?.commit()
    }

    val handler = Handler{
        when(it.what){

        }
        true
    }


}
var containerId : Int? = null

class ChooseEmptyClassroomUi:AnkoComponent<ChooseEmptyClassroom> {

    override fun createView(ui: AnkoContext<ChooseEmptyClassroom>)= with(ui){
        frameLayout{
            containerId = View.generateViewId()
            id = containerId!!.toInt()
            backgroundColor = ContextCompat.getColor(ctx,R.color.colorBackground )
        }

    }

}
