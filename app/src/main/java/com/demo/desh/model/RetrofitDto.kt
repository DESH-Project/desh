package com.demo.desh.model

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
    val nickname : String,
    val email : String,
    val profileImageUrl : String
) : Serializable

data class Realty(
    val name: String,
    val price: Double,
    val address: String,
    val pyung: Long,
    val squareMeter: Double,
    val userId: Long
) : Serializable

data class DistrictInfo(
    val size: Int,
    val list: MutableList<District>,
    val status: Int
)

data class District(
    val id: Long,
    val address: String,
    val image: String,
    val price: Double
)