package com.demo.desh.model

import java.io.Serializable
import java.time.LocalDateTime

data class ServerResponse<out T> (
    val size: Int,
    val data: List<T>,
)

data class ServerResponseObj<out T> (
    val size: Int,
    val data: T,
)

data class Recommend(
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
) : Serializable

data class RealtyCreationReq(
    val name: String,
    val price: Double,
    val address: String,
    val pyung: Long,
    val squareMeter: Double,
    val userId: Long
)

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

data class RealtyPreviewInfoForStar(
    val id: Long = 1L,
    val deposit: Long = 10000L,
    val monthlyRental: Int = 100,
    val images: List<String> = listOf(""),
    val address: String = "",
)

data class RealtyPreviewInfoForReg(
    val id: Long = 1L,
    val name: String = "",
    val monthlyRental: Int = 1,
    val previewImage: String = ""
)

data class RealtyPreview(
    val id: Long,
    val address: String,
    val deposit: Long,
    val monthly: Int,
    val previewImage: String,
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