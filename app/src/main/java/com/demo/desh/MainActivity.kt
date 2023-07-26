package com.demo.desh

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import com.demo.desh.dto.User
import com.demo.desh.ui.screen.MainActivityScreen
import com.demo.desh.ui.theme.DeshprojectfeTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val user = intent.getSerializableExtra("user") as User

        setContent {
            DeshprojectfeTheme {
                Surface {
                    MainActivityScreen(this, user)
                }
            }
        }
    }
}