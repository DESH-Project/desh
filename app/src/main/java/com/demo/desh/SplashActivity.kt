package com.demo.desh

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.Utility

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S){
            val splashScreen = installSplashScreen()
            splashScreen.setKeepOnScreenCondition { true }
        }

        super.onCreate(savedInstanceState)

        KakaoSdk.init(this, getString(R.string.KAKAO_NATIVE_APP_KEY))
        val keyHash = Utility.getKeyHash(this)

        Log.d(TAG, "keyHash : $keyHash")

        startActivity(Intent(this, LoginActivity::class.java))
    }

    companion object {
        const val TAG = "SplashActivity"
    }
}