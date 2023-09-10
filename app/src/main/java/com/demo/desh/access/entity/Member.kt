package com.demo.desh.access.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "member")
class Member {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "memberId")
    var id: Int = 0

    @ColumnInfo(name = "nickname")
    var nickname: String? = null

    @ColumnInfo(name = "email")
    var email: String? = null

    @ColumnInfo(name = "image")
    var profileImageUrl: String? = null

    constructor() {}

    constructor(nickname: String, email: String, imageUrl: String) {
        this.nickname = nickname
        this.email = email
        this.profileImageUrl = imageUrl
    }
}