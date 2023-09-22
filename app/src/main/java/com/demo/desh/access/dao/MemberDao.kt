package com.demo.desh.access.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.demo.desh.access.entity.Member

@Dao
interface MemberDao {
    @Insert
    suspend fun insertMember(vararg member: Member)

    @Query("select * from member")
    suspend fun findAllMember() : List<Member>

    @Query("delete from member")
    suspend fun deleteAllMember() : Unit
}