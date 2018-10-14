package com.nenguou.dayuandaily.Model


data class ClassBean(
    val code: Int,
    val msg: String,
    val data: List<ClassBeanData>
)

data class ClassBeanData(
    val name: String,
    val suffix: String,
    val teacher: String,
    val rawWeek: String,
    val weeks: List<Int>,
    val details: List<Detail>
)

data class Detail(
    val build: String,
    val campus: String,
    val room: String,
    val day: Int,
    val start: Int,
    val length: Int
)