package com.demo.desh.model


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
    var userId: Long? = null,
    var nickname: String,
    var email: String,
    var profileImageUrl: String,
    var description: String = ""
) {
    fun toRoomUser() =
        RoomUser(
            uid = this.userId!!,
            nickname = this.nickname,
            email = this.email,
            profileImageUrl = this.profileImageUrl,
            description = this.description
        )
}

data class UserPreview(
    val userId: Long,
    val nickname: String,
    val profileImage: String
)

data class RealtyCreationReq(
    val name: String,
    val deposit: Int,
    val monthlyRental: Int,
    val address: String,
    val pyung: Int,
    val squareMeter: Double,
    val userId: Long,
    val service: String
)

data class Realty(
    val realtyId: Long,
    val name: String,
    val address: String,
    val deposit: Int,
    val monthlyRental: Int,
    val pyung: Int,
    val squareMeter: Double,
    val images: List<String>,
    val ownerId: Long
)

data class RealtyPreview(
    val realtyId: Long,
    val deposit: Long,
    val monthlyRental: Int,
    val previewImage: String,
    val star: Long
)

