package com.demo.desh.model

import java.io.Serializable

data class RecommendInfo(
    val size: Int,
    val list: List<Recommend>
): Serializable

data class Recommend(
    val lat: Double,
    val lng: Double,
    val service: String,
    val district: String,
    val predict: Long
): Serializable