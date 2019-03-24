package com.nenguou.dayuandaily.Model


data class YearCollege(
    val code: Int,
    val msg: String,
    val data: DataYearCollege
)

data class DataYearCollege(
    val terms: List<Term>,
    val colleges: List<College>
)

data class Term(
    val n: String,
    val v: String
)

data class College(
    val id: String,
    val name: String,
    val majors: Any
)