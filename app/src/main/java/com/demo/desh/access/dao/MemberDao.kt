package com.demo.desh.access.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.demo.desh.access.entity.Member

@Dao
interface MemberDao {
    @Insert
    suspend fun insertMember(vararg member: Member)

    @Query("SELECT * FROM member WHERE nickname = :nickname LIMIT 1")
    suspend fun findMemberByNickname(nickname: String) : Member?

    @Query("SELECT * FROM member WHERE email = :email LIMIT 1")
    suspend fun findMemberByEmail(email: String) : Member?

    @Query("delete from member")
    suspend fun deleteAllMember() : Unit

    @Query("SELECT EXISTS(SELECT * from member where nickname = :nickname limit 1)")
    suspend fun existsMemberByNickname(nickname: String) : Boolean

    @Query("SELECT EXISTS(select * from member where email = :email limit 1)")
    suspend fun existsMemberByEmail(email: String) : Boolean
}