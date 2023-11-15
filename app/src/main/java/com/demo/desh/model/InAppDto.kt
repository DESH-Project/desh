package com.demo.desh.model

import androidx.compose.ui.graphics.Color

data class DropdownItem(
    val text: String,
    val textColor: Color
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
                impactText = "직접\n소통하세요",
                explainText = "제공자와 1:1 대화를 통해\n안전하고 확실한 상권을 알아보세요!"
            ),

            LoginPreviewInfo(
                introduceText = "주변 상가 정보 알아보기",
                impactText = "쉽게\n확인하세요",
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

data class BuildingInfo(
    val name: String,
    val price: Int,
    val address: String,
    val pyung: Double,
    val squareMeter: Double,
    val images: List<String>,
    val ownerNickname: String,
    val ownerProfileImage: String,
    val star: Int
)

data class BuildingPreviewInfo(
    val name: String,
    val price: Int,
    val previewImage: String
)

// 인근 상가 정보 목록
val buildingPreviewDummy = listOf(
    BuildingPreviewInfo(
        name = "다니엘관",
        previewImage = "https://goodplacebucket.s3.ap-northeast-2.amazonaws.com/87114f2a-3c05-4c12-82d2-1d996f6a51d2.png",
        price = 100000
    ),

    BuildingPreviewInfo(
        name = "사무엘관",
        previewImage = "https://goodplacebucket.s3.ap-northeast-2.amazonaws.com/21e33cf3-b1dc-439d-af3a-0bd5d728a581.png",
        price = 150000
    ),

    BuildingPreviewInfo(
        name = "요한관",
        previewImage = "https://goodplacebucket.s3.ap-northeast-2.amazonaws.com/d3dbe6e2-6513-44a7-a261-c04ff6328bd1.png",
        price = 200000
    ),
)

val buildingInfo = BuildingInfo(
    name = "오도로 빌딩",
    price = 210000000,
    address = "서울시 노원구 상계 1동 345-56",
    pyung = 33.33,
    squareMeter = 33.33 * 3.3,
    images = listOf(
        "https://goodplacebucket.s3.ap-northeast-2.amazonaws.com/87114f2a-3c05-4c12-82d2-1d996f6a51d2.png",
        "https://goodplacebucket.s3.ap-northeast-2.amazonaws.com/21e33cf3-b1dc-439d-af3a-0bd5d728a581.png",
        "https://goodplacebucket.s3.ap-northeast-2.amazonaws.com/d3dbe6e2-6513-44a7-a261-c04ff6328bd1.png",
    ),
    ownerNickname = "h970126",
    ownerProfileImage = "https://goodplacebucket.s3.ap-northeast-2.amazonaws.com/87114f2a-3c05-4c12-82d2-1d996f6a51d2.png",
    star = 16
)

