package com.demo.desh.access.repository

import com.demo.desh.util.RetrofitClient
import com.demo.desh.model.Realty
import com.demo.desh.model.RecommendInfo
import com.demo.desh.model.ServiceList
import com.demo.desh.model.User
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Query

class UserRepository {
    private val userRetrofitClient = RetrofitClient.userRetrofitClient

    fun login(user: User): Call<Long> {
        return userRetrofitClient.login(user)
    }

    suspend fun sendRealtyInfo(@Body realty: Realty): Response<Long> {
        return userRetrofitClient.sendRealtyInfo(realty)
    }

    suspend fun getServiceList(): Response<ServiceList> {
        return userRetrofitClient.getServiceList()
    }

    suspend fun getRecommendationAllInfo(): Response<RecommendInfo> {
        return userRetrofitClient.getRecommendationAllInfo()
    }

    suspend fun getRecommendationInfo(@Query("service") encodedServiceName: String): Response<RecommendInfo> {
        return userRetrofitClient.getRecommendationInfo(encodedServiceName)
    }
}