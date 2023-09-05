package com.demo.desh.model

import java.io.Serializable

data class ServerResponse<out T>(
    val size: Int,
    val data: List<T>,
    val status: Int = 200
)

data class Recommend(
    val lat: Double,
    val lng: Double,
    val service: String,
    val district: String,
    val predict: Long
): Serializable

data class User(
    var id : Long? = null,
    val nickname : String,
    val email : String,
    val profileImageUrl : String
) : Serializable

data class RealtyCreationReq(
    val name: String,
    val price: Double,
    val address: String,
    val pyung: Long,
    val squareMeter: Double,
    val userId: Long
) : Serializable

data class District(
    val id: Long,
    val address: String,
    val image: String,
    val price: Double
)

data class Realty(
    val id: Long,
    val name: String,
    val price: Double,
    val address: String,
    val pyung: Long,
    val squareMeter: Double,
    val image: String,
    val nearby: String,
    val userId: Long
)