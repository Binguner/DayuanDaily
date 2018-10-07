package com.nenguou.dayuandaily.Model

data class LoginBean(
    val code: Int,
    val msg: String,
    val data: Data33
)

data class Data33(
    val id: Int,
    val nickname: String,
    val username: String,
    val avatar: Any,
    val telephone: Any,
    val mail: Any,
    val remark: Any
)