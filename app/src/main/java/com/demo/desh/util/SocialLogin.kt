package com.demo.desh.util

import android.content.Context
import com.demo.desh.model.User

interface SocialLogin {
    fun send(context: Context, user: User)
    fun intentMain(context: Context, user: User)
}