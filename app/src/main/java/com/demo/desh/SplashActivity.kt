package com.demo.desh

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.demo.desh.access.repository.MemberRepository
import com.demo.desh.access.room.MemberRoomDatabase
import com.demo.desh.model.User
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

        /* 네아로 SDK 초기화 이슈 : https://github.com/naver/naveridlogin-sdk-android/issues/47
        val naverClientId = getString(R.string.NAVER_OAUTH_CLIENT_ID)
        val naverClientSecret = getString(R.string.NAVER_OAUTH_CLIENT_SECRET)
        val naverClientName = getString(R.string.APP_NAME)
        NaverIdLoginSDK.initialize(this, naverClientId, naverClientSecret, naverClientName)
        */

        val room = MemberRoomDatabase.getInstance(this)
        val memberDao = room.memberDao()
        val memberRepository = MemberRepository(memberDao)

        val members = memberRepository.findAllMember()
        Log.e(TAG, "member = $members")

        val user = User(
            id = 1L,
            email = "test@gmail.com",
            nickname = "박은향",
            profileImageUrl = "https://artfolio-bucket.s3.ap-northeast-2.amazonaws.com/static/artPiece/1/%E1%84%80%E1%85%A9%E1%84%8B%E1%85%A3%E1%86%BC%E1%84%8B%E1%85%B5.png"
        )

        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("user", user)
        startActivity(intent)
        /*
        if (members.isEmpty()) {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        else {
            val intent = Intent(this, MainActivity::class.java)
            val member = members.first()
            val user = User(
                id = member.id.toLong(),
                email = member.email!!,
                nickname = member.nickname!!,
                profileImageUrl = member.profileImageUrl!!
            )

            intent.putExtra("user", user)
            startActivity(intent)
        }
        */
    }

    companion object {
        const val TAG = "SplashActivity"
    }
}