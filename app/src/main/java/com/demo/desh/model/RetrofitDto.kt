package com.demo.desh.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class RecommendInfo(
    val size: Int,
    val list: List<Recommend>
): Serializable

data class Recommend(
    val lat: Double,
    val lng: Double,
    val service: String,
    val district: String,
    val predict: Long
): Serializable

data class ServiceList(
    val size: Int,
    val list: List<String>
): Serializable

data class User(
    var id : Long? = null,
    @SerializedName("nickname") val nickname : String,
    @SerializedName("email") val email : String,
    @SerializedName("profileImageUrl") val profileImageUrl : String,
) : Serializable

data class Realty(
    @SerializedName("name") val name: String,
    @SerializedName("price") val price: Double,
    @SerializedName("address") val address: String,
    @SerializedName("pyung") val pyung: Long,
    @SerializedName("squareMeter") val squareMeter: Double,
    @SerializedName("userId") val userId: Long
) : Serializable