package com.demo.desh

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.compose.runtime.livedata.observeAsState
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.demo.desh.util.RoomManager
import com.demo.desh.viewModel.RoomViewModel
import com.kakao.sdk.common.KakaoSdk
import kotlinx.coroutines.runBlocking

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)

        KakaoSdk.init(this, getString(R.string.KAKAO_NATIVE_APP_KEY))

        val roomViewModel = RoomManager.getRoomViewModel(application)
        runBlocking {
            runBlocking { roomViewModel.findLocalUser() }
            Log.e("=========================", roomViewModel.user.value.toString())
            lifecycleScope.launchWhenCreated {
                splashScreen.setKeepOnScreenCondition { true }
                val localUser = roomViewModel.user.value
                val intent =
                    if (localUser == null) Intent(this@SplashActivity, LoginActivity::class.java)
                    else Intent(this@SplashActivity, MainActivity::class.java)

                startActivity(intent)
                finish()
            }
        }
    }
}