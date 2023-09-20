package com.demo.desh.model

data class LoginPreviewInfo(
    val imageUrl: String,
    val introduceText: String,
    val impactText: String,
    val explainText: String
) {
    companion object {
        val testData = listOf<LoginPreviewInfo>(
            LoginPreviewInfo(
                imageUrl = "https://ddakdae-s3-bucket.s3.ap-northeast-2.amazonaws.com/flow_photo/copernico-p_kICQCOM4s-unsplash.jpg",
                introduceText = "지금 시작해보세요!",
                impactText = "GOODPLACE",
                explainText = "소상공인을 위한 상권추천 서비스! 지금 바로 시작하세요!"
            ),

            LoginPreviewInfo(
                imageUrl = "https://ddakdae-s3-bucket.s3.ap-northeast-2.amazonaws.com/flow_photo/damir-kopezhanov-luseu9GtYzM-unsplash.jpg",
                introduceText = "1:1로 다이렉트 대화하기",
                impactText = "소통하세요",
                explainText = "제공자와 1:1 대화 커뮤니티를 통한 안전하고 확실한 상권을 알아보세요!"
            ),

            LoginPreviewInfo(
                imageUrl = "https://ddakdae-s3-bucket.s3.ap-northeast-2.amazonaws.com/flow_photo/jose-losada-DyFjxmHt3Es-unsplash.jpg",
                introduceText = "GoodPlace에 오신 것을 환영합니다!",
                impactText = "안녕하세요!",
                explainText = "상권을 알아보고 계신가요?"
            ),

        )
    }
}