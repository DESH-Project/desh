package com.demo.desh.login

import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.demo.desh.MainActivity
import com.demo.desh.api.RetrofitClient
import com.demo.desh.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

interface SocialLogin {
    fun login(context: Context)

    fun send(context: Context, user: User) {
        val userService = RetrofitClient.userService
        val result = userService.login(user)

        result.enqueue(object : Callback<Long> {
            override fun onResponse(call: Call<Long>, response: Response<Long>) {
                user.id = response.body()
                intentMain(context, user)
            }

            override fun onFailure(call: Call<Long>, t: Throwable) {
                val loginFailText = "Login에 실패하였습니다"
                Toast.makeText(context, loginFailText, Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun intentMain(context: Context, user: User) {
        val intent = Intent(context, MainActivity::class.java)
        intent.putExtra("user", user)
        context.startActivity(intent)
    }
}