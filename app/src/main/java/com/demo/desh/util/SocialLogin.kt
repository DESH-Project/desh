package com.demo.desh.util

import android.content.Context
import com.demo.desh.dto.User

interface SocialLogin {
    fun init(context: Context)
    fun send(context: Context, user: User)
}