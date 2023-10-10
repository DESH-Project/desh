package com.demo.desh.access.dao

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    /* UserDao */
    private const val host = "good-place.shop"
    private const val domain = "http://$host/"

    private val userRetrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(domain)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val userRetrofitDao: UserRetrofitDao by lazy {
        userRetrofit.create(UserRetrofitDao::class.java)
    }
}