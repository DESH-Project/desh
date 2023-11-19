package com.demo.desh.access

import com.demo.desh.model.ChatDetail
import com.demo.desh.model.ChatPreview
import com.demo.desh.model.ServerResponse
import com.demo.desh.model.ServerResponseObj
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ChatRetrofitDao {
    /* 유저 아이디로 채팅방 내역 조회 */
    @GET("chatroom")
    suspend fun getChatRoomList(@Query("user-id") userId: Long) : Response<ServerResponse<ChatPreview>>

    /* 채팅 상세내역 조회 */
    @GET("chat")
    suspend fun getChatDetail(@Query("chatroom-id") chatroomId: Long) : Response<ServerResponseObj<ChatDetail>>
}