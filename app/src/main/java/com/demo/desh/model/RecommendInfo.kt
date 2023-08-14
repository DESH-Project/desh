package com.demo.desh.model

import java.io.Serializable

data class RecommendInfo(
    val size: Int,
    val recommend: List<Recommend>
): Serializable

data class Recommend(
    val lat: Double,
    val lng: Double,
    val name: String
): Serializable