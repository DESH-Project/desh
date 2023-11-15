package com.demo.desh.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "local_user")
data class RoomUser(
    @PrimaryKey val uid: Long,
    val nickname: String,
    val email: String,
    @ColumnInfo(name = "profile_image_url") val profileImageUrl: String,
    var description: String = ""
)