package com.demo.desh

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.demo.desh.util.GoogleLogin
import com.demo.desh.viewmodel.LoginViewModel

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S){
            val splashScreen = installSplashScreen()
            splashScreen.setKeepOnScreenCondition { true }
        }

        Log.d(TAG, "onCreate()")
        viewModel.tryLogin(this)

        super.onCreate(savedInstanceState)
        val context = this

        lifecycleScope.launchWhenCreated {
            viewModel.loginResult.collect { isLogin ->
                if (isLogin) {
                    if (GoogleLogin.alreadyLoginCheck(context) != null) {
                        // 이미 로그인된 계정이 있으면 곧바로 이동
                        startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                    }
                } else {
                    startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                }
            }
        }

        setContent {
            Surface(color = Color.White) {
                Text(text = "로그인 확인중....", fontSize = 30.sp)
            }
        }
    }

    companion object {
        const val TAG = "SplashActivity"
    }
}