package com.demo.desh.access

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.demo.desh.model.RoomUser

@Dao
interface RoomAccessDao {
    @Insert
    fun insertUser(user: RoomUser)

    @Query("select * from local_user")
    fun findLocalUser() : List<RoomUser>

    @Query("delete from local_user")
    fun deleteAll()
}