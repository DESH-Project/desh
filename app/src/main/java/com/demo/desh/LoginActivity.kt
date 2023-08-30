package com.demo.desh

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.demo.desh.ui.screens.LoginScreen
import com.demo.desh.ui.theme.DeshprojectfeTheme


class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DeshprojectfeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    LoginScreen()
                }
            }
        }
    }
}

