package com.demo.desh.access.repository

import android.util.Log
import com.demo.desh.access.dao.MemberDao
import com.demo.desh.access.entity.Member


class MemberRepository(private val memberDao: MemberDao) {
    companion object {
        private const val TAG = "MemberRepository"
    }

    private fun logging(method: String, res: Any?) {
        Log.e(TAG, "method = $method, res = $res")
    }

    suspend fun insertMember(member: Member) =
        memberDao
            .insertMember(member)
            .also { logging("insertMember", it) }

    suspend fun deleteAllMember() : Unit =
        memberDao
            .deleteAllMember()
            .also { logging("deleteAllMember", it) }

    suspend fun findMemberByEmail(email: String) : Member? =
        memberDao
            .findMemberByEmail(email)
            .also { logging("findMemberByEmail", it) }

    suspend fun findMemberByNickname(nickname: String) : Member? =
        memberDao
            .findMemberByNickname(nickname)
            .also { logging("findMemberByNickname", it) }

    suspend fun existsMemberByEmail(email: String) : Boolean =
        memberDao
            .existsMemberByEmail(email)
            .also { logging("existsMemberByEmail", it) }

    suspend fun existsMemberByNickname(nickname: String) : Boolean =
        memberDao
            .existsMemberByNickname(nickname)
            .also { logging("existsMemberByNickname", it) }
}