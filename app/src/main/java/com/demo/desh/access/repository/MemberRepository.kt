package com.demo.desh.access.repository

import android.util.Log
import com.demo.desh.access.dao.MemberDao
import com.demo.desh.access.entity.Member


class MemberRepository(private val memberDao: MemberDao) {
    companion object {
        private const val TAG = "MemberRepository"
    }

    private fun logging(method: String, res: Any) {
        Log.e(TAG, "method = $method, res = $res")
    }

    suspend fun insertMember(member: Member) =
        memberDao
            .insertMember(member)
            .also { logging("insertMember", it) }

    suspend fun findAllMember() : List<Member> =
        memberDao
            .findAllMember()
            .also { logging("findAllMember", it) }

    suspend fun deleteAllMember() : Unit =
        memberDao
            .deleteAllMember()
            .also { logging("deleteAllMember", it) }

}