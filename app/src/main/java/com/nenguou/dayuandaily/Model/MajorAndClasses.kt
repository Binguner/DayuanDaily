package com.nenguou.dayuandaily.Model


data class MajorAndClasses(
    val code: Int,
    val msg: String,
    val data: MajorAndClassedData
)

data class MajorAndClassedData(
    val id: String,
    val name: String,
    val majors: List<Major>
)

data class Major(
    val name: String,
    val classes: List<Classe>
)

data class Classe(
    val number: String,
    val name: String
)