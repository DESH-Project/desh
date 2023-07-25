package com.demo.desh.dto

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class KakaoUser(
    var id : Long? = null,
    @SerializedName("nickname") val nickname : String,
    @SerializedName("email") val email : String,
    @SerializedName("profileImageUrl") val profileImageUrl : String,
    @SerializedName("ageRange") val ageRange : String,
    @SerializedName("gender") val gender : String
) : Serializable