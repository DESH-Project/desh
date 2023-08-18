package com.demo.desh

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.Utility
import com.navercorp.nid.NaverIdLoginSDK

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

        val naverClientId = getString(R.string.NAVER_OAUTH_CLIENT_ID)
        val naverClientSecret = getString(R.string.NAVER_OAUTH_CLIENT_SECRET)
        val naverClientName = getString(R.string.APP_NAME)
        NaverIdLoginSDK.initialize(this, naverClientId, naverClientSecret, naverClientName)

        startActivity(Intent(this, LoginActivity::class.java))
    }

    companion object {
        const val TAG = "SplashActivity"
    }
}