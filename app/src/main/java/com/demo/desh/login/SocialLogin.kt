package com.demo.desh.login

import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.demo.desh.MainActivity
import com.demo.desh.access.entity.Member
import com.demo.desh.access.repository.MemberRepository
import com.demo.desh.model.User
import com.demo.desh.access.repository.UserRetrofitRepository
import com.demo.desh.access.room.MemberRoomDatabase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

abstract class SocialLogin {
    private val userRetrofitRepository = UserRetrofitRepository()

    abstract fun login(context: Context, goToMapScreen: () -> Unit)

    protected fun send(context: Context, user: User, goToMapScreen: () -> Unit) {
        val result = userRetrofitRepository.login(user)

        result.enqueue(object : Callback<Long> {
            override fun onResponse(call: Call<Long>, response: Response<Long>) {
                user.id = response.body()

                saveMemberIntoRoomDB(context, user)
                goToMapScreen()
            }

            override fun onFailure(call: Call<Long>, t: Throwable) {
                val loginFailText = "Login에 실패하였습니다"
                Toast.makeText(context, loginFailText, Toast.LENGTH_SHORT).show()
            }
        })
    }

    protected fun saveMemberIntoRoomDB(context: Context, user: User) {
        val member = Member(user)
        val roomDatabase = MemberRoomDatabase.getInstance(context)
        val memberDao = roomDatabase.memberDao()
        val memberRepository = MemberRepository(memberDao)

        memberRepository.insertMember(member)
    }
}