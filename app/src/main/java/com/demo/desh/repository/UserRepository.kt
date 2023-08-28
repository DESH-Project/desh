package com.demo.desh.repository

import com.demo.desh.api.RetrofitClient
import com.demo.desh.model.Realty
import com.demo.desh.model.RecommendInfo
import com.demo.desh.model.ServiceList
import com.demo.desh.model.User
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Query

class UserRepository {
    private val userClient = RetrofitClient.userClient

    fun login(user: User): Call<Long> {
        return userClient.login(user)
    }

    suspend fun sendRealtyInfo(@Body realty: Realty): Response<Long> {
        return userClient.sendRealtyInfo(realty)
    }

    suspend fun getServiceList(): Response<ServiceList> {
        return userClient.getServiceList()
    }

    suspend fun getRecommendationAllInfo(): Response<RecommendInfo> {
        return userClient.getRecommendationAllInfo()
    }

    suspend fun getRecommendationInfo(@Query("service") encodedServiceName: String): Response<RecommendInfo> {
        return userClient.getRecommendationInfo(encodedServiceName)
    }
}