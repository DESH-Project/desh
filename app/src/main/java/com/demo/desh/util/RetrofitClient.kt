package com.demo.desh.util

import com.demo.desh.access.dao.UserRetrofitClient
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

    val userRetrofitClient: UserRetrofitClient by lazy {
        retrofit.create(UserRetrofitClient::class.java)
    }
}