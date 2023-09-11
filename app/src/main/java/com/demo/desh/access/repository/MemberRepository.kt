package com.demo.desh.access.repository

import com.demo.desh.access.dao.MemberDao
import com.demo.desh.access.entity.Member
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MemberRepository(private val memberDao: MemberDao) {
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    fun insertMember(member: Member) {
        coroutineScope.launch {
            memberDao.insertMember(member)
        }
    }

    fun findAllMember() : List<Member> {
        val result : MutableList<Member> = mutableListOf()

        runBlocking {
            coroutineScope.launch {
                val def = async {
                    memberDao.findAllMember()
                }

                val r = def.await()
                r.forEach { result.add(it) }
            }
        }

        return result
    }
}