package com.nenguou.dayuandaily.UI.Fragments

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.constraint.ConstraintSet.PARENT_ID
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.view.GravityCompat
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nenguou.dayuandaily.DataBase.DayuanDailyDatabase
import com.nenguou.dayuandaily.R
import com.nenguou.dayuandaily.UI.ChooseEmptyClassroom
import kotlinx.android.synthetic.main.activity_main.view.*
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.cardview.v7.cardView
import org.jetbrains.anko.constraint.layout.constraintLayout

private lateinit var chooseEmptyClassroom: ChooseEmptyClassroom
private lateinit var mhandler: Handler
private lateinit var dayuanDailyDatabase: DayuanDailyDatabase

class FragmentSearchClassroom : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        chooseEmptyClassroom = activity as ChooseEmptyClassroom
        mhandler = chooseEmptyClassroom.handler
        dayuanDailyDatabase = DayuanDailyDatabase.getInstance(context)
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

    private var id_cardView = 0
    private var id_toolbar = 0
    private var id_sheduler_title = 0


    override fun createView(ui: AnkoContext<FragmentSearchClassroom>) = with(ui) {

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
                    textView {
                        id_sheduler_title = View.generateViewId()
                        id = id_sheduler_title
                        text = dayuanDailyDatabase.loadTerm()[0].n
                        textColor = ContextCompat.getColor(ctx,R.color.colorDeepBlackText)
                        textSize = 18f
                    }.lparams(){
                        topToTop = PARENT_ID
                        startToStart = PARENT_ID
                        leftMargin = dip(15)
                        topMargin = dip(10)
                    }
                }
            }.lparams(width = matchParent, height = dip(200)) {
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
