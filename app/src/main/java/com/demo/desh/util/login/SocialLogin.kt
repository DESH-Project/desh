package com.demo.desh.util.login

import android.content.Context
import com.demo.desh.model.User


abstract class SocialLogin {
    abstract suspend fun login(context: Context) : User?
}