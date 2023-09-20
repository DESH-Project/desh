package com.demo.desh.model

data class PreviewInfo(
    val imageUrl: String,
    val deposit: Int,
    val monthly: Int
) {
    companion object {
        val testData = listOf<PreviewInfo>(
            PreviewInfo(
                imageUrl = "https://artfolio-bucket.s3.ap-northeast-2.amazonaws.com/static/artPiece/start_screen1.jpg",
                deposit = 150000000,
                monthly = 500
            ),

            PreviewInfo(
                imageUrl = "https://artfolio-bucket.s3.ap-northeast-2.amazonaws.com/static/artPiece/start_screen2.jpg",
                deposit = 250000000,
                monthly = 600
            ),

            PreviewInfo(
                imageUrl = "https://artfolio-bucket.s3.ap-northeast-2.amazonaws.com/static/artPiece/start_screen3.jpg",
                deposit = 350000000,
                monthly = 700
            ),

            PreviewInfo(
                imageUrl = "https://artfolio-bucket.s3.ap-northeast-2.amazonaws.com/static/artPiece/start_screen4.jpg",
                deposit = 450000000,
                monthly = 800
            ),

            PreviewInfo(
                imageUrl = "https://artfolio-bucket.s3.ap-northeast-2.amazonaws.com/static/artPiece/start_screen2.jpg",
                deposit = 550000000,
                monthly = 900
            ),
        )
    }
}