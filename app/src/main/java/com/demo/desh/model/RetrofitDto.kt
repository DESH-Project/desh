package com.demo.desh.model

import java.io.Serializable

data class ServerResponse<out T>(
    val size: Int,
    val data: List<T>,
    val status: Int = 200
)

data class ServerResponseObj<out T>(
    val size: Int,
    val data: T,
    val status: Int = 200
)

data class IntroStore(
    val id: Int,
    val name: String,
    val address: String,
    val price: Int,
    val pyung: Int,
    val images: List<String>,
    val monthly: Int,
    val deposit: Int
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
    var profileImageUrl : String
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

data class LoginPreviewInfo(
    val introduceText: String,
    val impactText: String,
    val explainText: String
) {
    companion object {
        val textData = listOf(
            LoginPreviewInfo(
                introduceText = "1:1로 다이렉트 대화하기",
                impactText = "소통하세요",
                explainText = "제공자와 1:1 대화를 통해\n안전하고 확실한 상권을 알아보세요!"
            ),

            LoginPreviewInfo(
                introduceText = "주변 상가 정보 알아보기",
                impactText = "확인하세요",
                explainText = "혹시 상권을 알아보고 계신가요?\n지도를 통해 한눈에 상권 정보를 보여드릴게요!"
            ),

            LoginPreviewInfo(
                introduceText = "지금 시작해보세요",
                impactText = "GOODPLACE",
                explainText = "소상공인을 위한 상권추천 서비스\n지금 바로 시작하세요!"
            ),
        )
    }
}