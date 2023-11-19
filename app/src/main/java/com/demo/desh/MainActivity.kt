package com.demo.desh

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.demo.desh.nav.BottomNavigationBar
import com.demo.desh.nav.NavigationHost
import com.demo.desh.ui.LoadingDialog
import com.demo.desh.ui.TopBarContent
import com.demo.desh.ui.theme.DefaultBackgroundColor
import com.demo.desh.ui.theme.DeshprojectfeTheme
import com.demo.desh.util.RoomManager
import com.demo.desh.viewModel.ChatViewModel
import com.demo.desh.viewModel.RoomViewModel
import com.demo.desh.viewModel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        val chatViewModel = ViewModelProvider(this)[ChatViewModel::class.java]
        val roomViewModel = RoomManager.getRoomViewModel(application)

        setContent {
            DeshprojectfeTheme {
                Root(userViewModel, chatViewModel, roomViewModel)
            }
        }
    }
}

@Composable
fun Root(
    userViewModel: UserViewModel,
    chatViewModel: ChatViewModel,
    roomViewModel: RoomViewModel
) {
    val navController = rememberNavController()
    val uld by userViewModel.open.observeAsState(initial = false)
    val cld by chatViewModel.open.observeAsState(initial = false)

    Scaffold(
        scaffoldState = rememberScaffoldState(),
        backgroundColor = DefaultBackgroundColor,
        contentColor = Color.White,
        modifier = Modifier.fillMaxSize(),

        topBar = { TopBarContent() },
        bottomBar = { BottomNavigationBar(navController) }

    ) { innerPadding ->
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            NavigationHost(
                navController = navController,
                userViewModel = userViewModel,
                chatViewModel = chatViewModel,
                roomViewModel = roomViewModel
            )
        }
    }
}