package com.demo.desh.model

import java.time.LocalDateTime

data class ServerResponse<out T> (
    val size: Int,
    val data: List<T>,
)

data class ServerResponseObj<out T> (
    val size: Int,
    val data: T,
)

data class RecommendDistrict(
    val lat: Double,
    val lng: Double,
    val service: String,
    val district: String,
    val predict: Long
)

data class User(
    var id : Long? = null,
    var nickname : String,
    var email : String,
    var profileImageUrl : String,
    var description: String = ""
)

data class RealtyCreationReq(
    val name: String,
    val price: Double,
    val address: String,
    val pyung: Long,
    val squareMeter: Double,
    val userId: Long
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

data class RealtyPreview(
    val id: Long,
    val deposit: Long,
    val monthlyRental: Int,
    val previewImage: String,
    val address: String,
    val star: Long
)

/* Chat Dto */
data class ChatInfo(
    val id: Long,
    val chatroom: ChatData
)

data class ChatData(
    val id: Long,
    val roomId: String,
    val name: String,
    var image: String?,
    val chat: List<Chat>
)

data class Chat(
    val id: Long,
    val writerId: Long,
    val writer: String,
    val message: String,
    val date: LocalDateTime
)