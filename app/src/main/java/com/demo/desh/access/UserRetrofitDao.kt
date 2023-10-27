package com.demo.desh.access

import com.demo.desh.model.District
import com.demo.desh.model.IntroStore
import com.demo.desh.model.Realty
import com.demo.desh.model.RealtyCreationReq
import com.demo.desh.model.Recommend
import com.demo.desh.model.ServerResponse
import com.demo.desh.model.ServerResponseObj
import com.demo.desh.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserRetrofitDao {
    /* 소셜 로그인 성공시 서버에 유저 정보 전달 */
    @POST("login")
    suspend fun login(@Body user: User): Response<Long>

    /* 건물 정보 등록 */
    @POST("realty")
    suspend fun sendRealtyInfo(@Body realty: RealtyCreationReq): Response<Long>

    /* 내가 본 매물 정보 프리뷰 */

    /* 내가 본 매물 정보 상세 */

    /* 조회 가능한 허용된 서비스 업종 리스트 */
    @GET("service")
    suspend fun getServiceList(): Response<ServerResponseObj<Map<String, List<String>>>>

    /* 전체 상권 추천 정보 */
    @GET("recommend-all")
    suspend fun getRecommendationAllInfo(): Response<ServerResponse<Recommend>>

    /* 상권 추천 정보 */
    @GET("recommend")
    suspend fun getRecommendationInfo(@Query("service") encodedServiceName: String): Response<ServerResponse<Recommend>>

    /* 상권에 따른 상가 리스트 조회 */
    @GET("stores")
    suspend fun getStoreList(@Query("district") encodedDistrictName: String): Response<ServerResponse<District>>

    @GET("store")
    suspend fun getRealtyDetail(@Query("store_id") realtyId: Long, @Query("user_id") userId: Long): Response<ServerResponse<Realty>>

    /* 초기 이미지 */
    @GET("intro/image")
    suspend fun getIntroImage() : Response<ServerResponse<String>>

    /* 시작 스크린 상가 추천 */
    @GET("intro/list")
    suspend fun getIntroStore() : Response<ServerResponse<IntroStore>>

    /* 상가 찜하기 */
    @GET("dibs")
    suspend fun sendPickedStore(
        @Query("realty-id") userId: Long,
        @Query("user-id") realtyId: Long
    ) : Response<ServerResponse<Int>>
}