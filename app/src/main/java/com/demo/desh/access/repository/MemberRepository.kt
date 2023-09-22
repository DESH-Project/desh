package com.demo.desh.access.repository

import com.demo.desh.access.dao.MemberDao
import com.demo.desh.access.entity.Member


class MemberRepository(private val memberDao: MemberDao) {
    suspend fun insertMember(member: Member) {
        memberDao.insertMember(member)
    }

    suspend fun findAllMember() : List<Member> {
        return memberDao.findAllMember()
    }

    suspend fun deleteAllMember() {
        memberDao.deleteAllMember()
    }
}