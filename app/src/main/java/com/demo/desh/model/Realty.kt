package com.demo.desh.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Realty(
    @SerializedName("name") val name: String,
    @SerializedName("price") val price: Double,
    @SerializedName("address") val address: String,
    @SerializedName("pyung") val pyung: Long,
    @SerializedName("squareMeter") val squareMeter: Double,
    @SerializedName("userId") val userId: Long
) : Serializable