package com.demo.desh.dto

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class User(
    var id : Long? = null,
    @SerializedName("nickname") val nickname : String,
    @SerializedName("email") val email : String,
    @SerializedName("profileImageUrl") val profileImageUrl : String,
) : Serializable