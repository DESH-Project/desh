package com.demo.desh.util.login

import android.content.Context
import com.demo.desh.model.User
import com.demo.desh.access.repository.UserRetrofitRepository


abstract class SocialLogin {
    private val userRetrofitRepository = UserRetrofitRepository()

    abstract suspend fun login(context: Context) : User?
}