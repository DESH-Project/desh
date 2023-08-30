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

    abstract fun login(context: Context)

    protected fun send(context: Context, user: User) {
        val result = userRetrofitRepository.login(user)

        result.enqueue(object : Callback<Long> {
            override fun onResponse(call: Call<Long>, response: Response<Long>) {
                user.id = response.body()

                val memberDb = MemberRoomDatabase.getInstance(context)
                val memberDao = memberDb.memberDao()
                val memberRepository = MemberRepository(memberDao)

                val member = Member(user.nickname, user.email, user.profileImageUrl)

                memberRepository.insertMember(member)
                toNext(context, user)
            }

            override fun onFailure(call: Call<Long>, t: Throwable) {
                val loginFailText = "Login에 실패하였습니다"
                Toast.makeText(context, loginFailText, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun toNext(context: Context, user: User) {
        val intent = Intent(context, MainActivity::class.java)
        intent.putExtra("user", user)
        context.startActivity(intent)
    }
}