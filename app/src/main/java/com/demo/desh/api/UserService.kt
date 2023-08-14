package com.demo.desh.api

import com.demo.desh.model.Realty
import com.demo.desh.model.RecommendInfo
import com.demo.desh.model.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserService {
    /* 소셜 로그인 성공시 서버에 유저 정보 전달 */
    @POST("login")
    fun login(@Body user: User): Call<Long>

    /* 건물 정보 등록 */
    @POST("/realty")
    fun sendRealtyInfo(@Body realty: Realty): Call<Long>

    /* 내가 본 매물 정보 프리뷰 */

    /* 내가 본 매물 정보 상세 */

    /* 조회 가능한 허용된 서비스 업종 리스트 */

    /* 상권 추천 정보 */
    @GET("recommend")
    fun getRecommendationInfo(@Query("service") encodedServiceName: String): Call<RecommendInfo>
}