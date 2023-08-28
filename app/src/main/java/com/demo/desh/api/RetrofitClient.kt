package com.demo.desh.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val host = "good-place.shop"
    private const val domain = "http://$host/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(domain)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val userClient: UserClient by lazy {
        retrofit.create(UserClient::class.java)
    }
}