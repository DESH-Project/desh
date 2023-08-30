package com.demo.desh.access.repository

import com.demo.desh.access.dao.MemberDao
import com.demo.desh.access.entity.Member
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MemberRepository(private val memberDao: MemberDao) {
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun insertMember(member: Member) {
        coroutineScope.launch(Dispatchers.IO) {
            memberDao.insertMember(member)
        }
    }
}