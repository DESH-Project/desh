package com.demo.desh.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    val host = "15.164.105.26"
    val port = "8080"
    val domain = "http://$host:$port"

    private var retrofit: Retrofit? = null

    internal fun getClient(baseUrl: String) : Retrofit {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        return retrofit!!
    }
}