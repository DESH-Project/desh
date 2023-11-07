package com.demo.desh

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.kakao.sdk.common.KakaoSdk

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)

        KakaoSdk.init(this, getString(R.string.KAKAO_NATIVE_APP_KEY))
        Thread.sleep(1500)

        lifecycleScope.launchWhenCreated {
            splashScreen.setKeepOnScreenCondition { true }
            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}