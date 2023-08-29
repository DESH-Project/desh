package com.demo.desh

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.compose.material.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.demo.desh.model.User
import com.demo.desh.util.BottomNavigationBar
import com.demo.desh.ui.theme.DeshprojectfeTheme
import com.demo.desh.util.MainNavigationHost
import com.demo.desh.viewModel.MainViewModel
import com.demo.desh.viewModel.MainViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val user = intent.getSerializableExtra("user") as User
        Log.d("MainActivity", "user = $user")

        viewModel = ViewModelProvider(this, MainViewModelFactory())[MainViewModel::class.java]

        setContent {
            DeshprojectfeTheme {
                Surface {
                    App(viewModel, user)
                }
            }
        }
    }
}

@Composable
fun App(
    viewModel: MainViewModel,
    user: User
) {
    val navController = rememberNavController()

    Scaffold(
        content = { it
            MainNavigationHost(
                navController = navController,
                viewModel = viewModel
            )
        },

        bottomBar = { BottomNavigationBar(navController = navController) }
    )
}
