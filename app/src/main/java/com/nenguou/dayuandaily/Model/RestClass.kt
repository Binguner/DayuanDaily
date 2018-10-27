package com.nenguou.dayuandaily.Model


data class RestClass(
    val code: Int,
    val msg: String,
    val data: Data
)

data class Data(
    val term: String,
    val termValue: String,
    val campus: List<Campu>
)

data class Campu(
    val name: String,
    val value: String,
    val builds: List<Build>
)

data class Build(
    val name: String,
    val value: String
)