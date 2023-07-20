package com.demo.desh.dto

import java.io.Serializable

data class KakaoUser(
    var id: Long? = null,
    val nickname: String,
    val ageRange: String,
    val email: String,
    val profileImageUrl: String,
    val gender: String,
    var accessToken: String? = null,
    var refreshToken: String? = null
) : Serializable