package com.demo.desh

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import com.demo.desh.ui.screens.LoginScreen
import com.demo.desh.ui.theme.DeshprojectfeTheme
import com.demo.desh.util.RoomManager
import com.demo.desh.viewModel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val roomViewModel = RoomManager.getRoomViewModel(application)
        val userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        val intent = Intent(this, MainActivity::class.java)

        setContent {
            DeshprojectfeTheme {
                LoginScreen(userViewModel, roomViewModel, intent)
            }
        }
    }
}