package com.demo.desh.model

data class PreviewInfo(
    val imageUrl: String,
    val deposit: Int,
    val monthly: Int
) {
    companion object {
        val testData = listOf<PreviewInfo>(
            PreviewInfo(
                imageUrl = "https://ddakdae-s3-bucket.s3.ap-northeast-2.amazonaws.com/flow_photo/copernico-p_kICQCOM4s-unsplash.jpg",
                deposit = 150000000,
                monthly = 500
            ),

            PreviewInfo(
                imageUrl = "https://ddakdae-s3-bucket.s3.ap-northeast-2.amazonaws.com/flow_photo/damir-kopezhanov-luseu9GtYzM-unsplash.jpg",
                deposit = 250000000,
                monthly = 600
            ),

            PreviewInfo(
                imageUrl = "https://ddakdae-s3-bucket.s3.ap-northeast-2.amazonaws.com/flow_photo/jose-losada-DyFjxmHt3Es-unsplash.jpg",
                deposit = 350000000,
                monthly = 700
            ),
        )
    }
}