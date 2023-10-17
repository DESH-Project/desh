package com.demo.desh.util.login

import android.content.Context
import com.demo.desh.model.User


abstract class SocialLogin {
    abstract fun login(context: Context, goToStartScreenWithUser: (User?) -> Unit)
}