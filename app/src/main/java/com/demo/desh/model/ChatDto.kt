package com.demo.desh.model

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class ChatPreview(
    val id: Long,
    val chatroom: ChatMetaInfo
)

data class ChatMetaInfo(
    val chatroomId: Long,
    val name: String,
    val image: String,
    val chats: List<Chat> = listOf(),
    val lastMessage: String
)

data class Chat(
    val id: Long,
    val writerId: Long,
    val writer: String,
    val message: String,
    val date: LocalDateTime
)

data class ChatDetail(
    val chatroomId: Long,
    val name: String,
    val image: String,
    val chats: MutableList<Chat> = mutableListOf(),
    @SerializedName("uid1") val senderId: Long,
    @SerializedName("nickname1") val senderNickname: String,
    @SerializedName("profile1") val senderProfileUrl: String,
    @SerializedName("uid2") val receiverId: Long,
    @SerializedName("nickname2") val receiverNickname: String,
    @SerializedName("profile2") val receiverProfileUrl: String
)