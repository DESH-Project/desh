package com.demo.desh.dto

import java.io.Serializable

data class KakaoUser(
    var id : Long? = null,
    val nickname : String,
    val email : String,
    val profileImageUrl : String,
    val ageRange : String,
    val gender : String
) : Serializable