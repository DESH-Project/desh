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

// ----- test dummy data -----
data class ChatRoomPreviewInfo(
    val senderName: String,
    val senderImage: String,
    val chatPreview: String,
    val badgeCount: Int
) {
    companion object {
        val testData = listOf(
            ChatRoomPreviewInfo(
                senderName = "황승수",
                senderImage = "https://goodplacebucket.s3.ap-northeast-2.amazonaws.com/87114f2a-3c05-4c12-82d2-1d996f6a51d2.png",
                chatPreview = "안녕하세요! 부동산 관련해서 문의 드립니다.",
                badgeCount = 1
            ),

            ChatRoomPreviewInfo(
                senderName = "홍길동",
                senderImage = "https://goodplacebucket.s3.ap-northeast-2.amazonaws.com/21e33cf3-b1dc-439d-af3a-0bd5d728a581.png",
                chatPreview = "좋은 거래 감사합니다!",
                badgeCount = 3
            ),

            ChatRoomPreviewInfo(
                senderName = "어그리먼트",
                senderImage = "https://goodplacebucket.s3.ap-northeast-2.amazonaws.com/d3dbe6e2-6513-44a7-a261-c04ff6328bd1.png",
                chatPreview = "내일 5시에 뵙겠습니다 ㅎㅎ",
                badgeCount = 2
            ),

            ChatRoomPreviewInfo(
                senderName = "투비씨앤씨",
                senderImage = "https://goodplacebucket.s3.ap-northeast-2.amazonaws.com/87114f2a-3c05-4c12-82d2-1d996f6a51d2.png",
                chatPreview = "사무실 이전 관련 문의드립니다.",
                badgeCount = 1
            ),

            ChatRoomPreviewInfo(
                senderName = "공인중개사 김철수",
                senderImage = "https://goodplacebucket.s3.ap-northeast-2.amazonaws.com/21e33cf3-b1dc-439d-af3a-0bd5d728a581.png",
                chatPreview = "가격 좋은 매물 나왔으니 부동산 한번 들려보세요!",
                badgeCount = 1
            ),

            ChatRoomPreviewInfo(
                senderName = "황승수2",
                senderImage = "https://goodplacebucket.s3.ap-northeast-2.amazonaws.com/87114f2a-3c05-4c12-82d2-1d996f6a51d2.png",
                chatPreview = "안녕하세요! 부동산 관련해서 문의 드립니다.",
                badgeCount = 0
            ),

            ChatRoomPreviewInfo(
                senderName = "홍길동2",
                senderImage = "https://goodplacebucket.s3.ap-northeast-2.amazonaws.com/21e33cf3-b1dc-439d-af3a-0bd5d728a581.png",
                chatPreview = "좋은 거래 감사합니다!",
                badgeCount = 0
            ),

            ChatRoomPreviewInfo(
                senderName = "어그리먼트2",
                senderImage = "https://goodplacebucket.s3.ap-northeast-2.amazonaws.com/d3dbe6e2-6513-44a7-a261-c04ff6328bd1.png",
                chatPreview = "내일 5시에 뵙겠습니다 ㅎㅎ",
                badgeCount = 0
            ),

            ChatRoomPreviewInfo(
                senderName = "투비씨앤씨2",
                senderImage = "https://goodplacebucket.s3.ap-northeast-2.amazonaws.com/87114f2a-3c05-4c12-82d2-1d996f6a51d2.png",
                chatPreview = "사무실 이전 관련 문의드립니다.",
                badgeCount = 0
            ),

            ChatRoomPreviewInfo(
                senderName = "공인중개사 김철수2",
                senderImage = "https://goodplacebucket.s3.ap-northeast-2.amazonaws.com/21e33cf3-b1dc-439d-af3a-0bd5d728a581.png",
                chatPreview = "가격 좋은 매물 나왔으니 부동산 한번 들려보세요!",
                badgeCount = 0
            )
        )
    }
}

val dummyImageList = listOf(
    "https://goodplacebucket.s3.ap-northeast-2.amazonaws.com/87114f2a-3c05-4c12-82d2-1d996f6a51d2.png",
    "https://goodplacebucket.s3.ap-northeast-2.amazonaws.com/21e33cf3-b1dc-439d-af3a-0bd5d728a581.png",
    "https://goodplacebucket.s3.ap-northeast-2.amazonaws.com/d3dbe6e2-6513-44a7-a261-c04ff6328bd1.png",
    "https://goodplacebucket.s3.ap-northeast-2.amazonaws.com/87114f2a-3c05-4c12-82d2-1d996f6a51d2.png",
    "https://goodplacebucket.s3.ap-northeast-2.amazonaws.com/21e33cf3-b1dc-439d-af3a-0bd5d728a581.png",
    "https://goodplacebucket.s3.ap-northeast-2.amazonaws.com/d3dbe6e2-6513-44a7-a261-c04ff6328bd1.png",
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

