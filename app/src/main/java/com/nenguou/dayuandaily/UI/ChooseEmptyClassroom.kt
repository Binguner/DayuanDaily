package com.nenguou.dayuandaily.UI

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.FragmentManager
import com.nenguou.dayuandaily.R
import com.nenguou.dayuandaily.UI.Fragments.FragmentClassroomResule
import com.nenguou.dayuandaily.UI.Fragments.FragmentSearchClassroom

class ChooseEmptyClassroom : AppCompatActivity() {

    //lateinit var searchFragment : FragmentSearchClassroom
    //lateinit var searchResuleResuleFragment: FragmentClassroomResule
    //lateinit var mfragmentManager: FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_empty_classroom)

        //initFragments()
    }

    /*fun initFragments(){
        if (null == searchFragment){
            searchFragment = FragmentSearchClassroom.newInstance()
        }
        if (null == searchResuleResuleFragment){
            searchResuleResuleFragment = FragmentClassroomResule.newInstance()
        }
        //mfragmentManager = supportFragmentManager
//        mfragmentManager.beginTransaction().add()
    }*/
}
