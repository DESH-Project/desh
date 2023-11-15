package com.demo.desh.access

import androidx.room.Dao
import androidx.room.Insert
import com.demo.desh.model.RoomUser

@Dao
interface RoomAccessDao {
    @Insert
    fun insertUser(user: RoomUser)
}