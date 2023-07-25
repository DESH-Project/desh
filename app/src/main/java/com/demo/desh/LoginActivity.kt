package com.demo.desh

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.demo.desh.ui.LoginActivityScreen
import com.demo.desh.ui.theme.DeshprojectfeTheme
import com.demo.desh.util.GoogleLogin
import com.demo.desh.util.KakaoLogin

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        GoogleLogin.init(this)
        KakaoLogin.init(this)

        setContent {
            DeshprojectfeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    LoginActivityScreen()
                }
            }
        }
    }

    companion object {
        const val LOGIN_TAG = "LoginActivity"
    }
}

