package com.demo.desh.access.repository

import com.demo.desh.access.dao.MemberDao
import com.demo.desh.access.entity.Member
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking


class MemberRepository(private val memberDao: MemberDao) {
    fun insertMember(member: Member) = runBlocking(Dispatchers.IO) {
        memberDao.insertMember(member)
    }

    fun findAllMember() : List<Member> = runBlocking(Dispatchers.IO) {
        async { memberDao.findAllMember() }.await()
    }

    fun deleteAllMember() : Unit = runBlocking(Dispatchers.IO) {
        memberDao.deleteAllMember()
    }
}