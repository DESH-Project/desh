package com.demo.desh.access.dao

import androidx.room.Dao
import androidx.room.Insert
import com.demo.desh.access.entity.Member

@Dao
interface MemberDao {
    @Insert
    fun insertMember(vararg member: Member)
}