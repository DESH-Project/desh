package com.demo.desh

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.demo.desh.util.RoomManager
import com.kakao.sdk.common.KakaoSdk
import kotlinx.coroutines.delay

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)

        KakaoSdk.init(this, getString(R.string.KAKAO_NATIVE_APP_KEY))

        val roomViewModel = RoomManager.getRoomViewModel(application)
        roomViewModel.findLocalUser()
        roomViewModel.deleteAll()

        lifecycleScope.launchWhenCreated {
            splashScreen.setKeepOnScreenCondition { true }
            val localUser = roomViewModel.user.value
            val intent = Intent(this@SplashActivity, LoginActivity::class.java)
                /*
                if (localUser == null) Intent(this@SplashActivity, LoginActivity::class.java)
                else Intent(this@SplashActivity, MainActivity::class.java)
                */
            delay(1500L)

            startActivity(intent)
            finish()
        }
    }
}