package com.demo.desh.repository

import android.util.Log
import com.demo.desh.access.ChatRetrofitDao
import com.demo.desh.model.ChatDetail
import com.demo.desh.model.ChatPreview
import com.demo.desh.model.ServerResponse
import com.demo.desh.model.ServerResponseObj
import retrofit2.Response
import javax.inject.Inject

class ChatRetrofitRepository @Inject constructor(private val chatRetrofitDao: ChatRetrofitDao) {
    companion object {
        private const val TAG = "UserRetrofitRepository"

        private fun logging(method: String, res: Any) {
            Log.e(TAG, "method = $method, res = $res")
        }
    }

    suspend fun getChatroomList(userId: Long) : Response<ServerResponse<ChatPreview>> =
        chatRetrofitDao
            .getChatRoomList(userId)
            .also { logging("getChatroomList", it) }

    suspend fun getChatDetail(chatroomId: Long) : Response<ServerResponseObj<ChatDetail>> =
        chatRetrofitDao
            .getChatDetail(chatroomId)
            .also { logging("getChatDetail", it) }
}