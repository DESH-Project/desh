package com.demo.desh.util

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.demo.desh.MainActivity
import com.demo.desh.api.RetrofitClient
import com.demo.desh.model.User
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object KakaoLogin: SocialLogin {
    private const val TAG = "KakaoLoginObject"

    fun login(context: Context) {
        val mCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) Log.e(TAG, "로그인 실패 $error")
            else if (token != null) Log.e(TAG, "로그인 성공 ${token.accessToken} ${token.idToken}")
        }

        val instance = UserApiClient.instance
        var kakaoUser: User? = null

        if (instance.isKakaoTalkLoginAvailable(context)) {
            instance.loginWithKakaoTalk(context) { token, error ->
                // 로그인 실패
                if (error != null) {
                    Log.e(TAG, "로그인 실패 $error")

                    // 사용자가 취소 버튼을 눌렀을 때
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }

                    else {
                        instance.loginWithKakaoAccount(context, callback = mCallback)
                    }
                }
            }
        }

        instance.me { user, error ->
            if (error != null) Log.e(TAG, "사용자 정보 요청 실패 $error")
            else if (user != null) {
                Log.e(TAG, "사용자 정보 요청 성공 $user")

                val account = user.kakaoAccount

                val nickname = account?.profile?.nickname
                val email = account?.email
                val profileImageUrl = account?.profile?.profileImageUrl

                kakaoUser = User(
                    nickname = nickname!!,
                    email = email!!,
                    profileImageUrl = profileImageUrl!!,
                )

                Log.d(TAG, "서버에 전송합니다")
                send(context, kakaoUser!!)
            }
        }

    }

    override fun send(context: Context, user: User) {
        val userService = RetrofitClient.userService
        val result = userService.login(user)

        result.enqueue(object : Callback<Long> {
            override fun onResponse(call: Call<Long>, response: Response<Long>) {
                user.id = response.body()
                intentMain(context, user)
            }

            override fun onFailure(call: Call<Long>, t: Throwable) {
                val loginFailText = "Kakao Login에 실패하였습니다"

                Log.e(TAG, loginFailText)
                Log.e(TAG, t.message!!)
                Toast.makeText(context, loginFailText, Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun intentMain(context: Context, user: User) {
        val intent = Intent(context, MainActivity::class.java)
        intent.putExtra("user", user)
        context.startActivity(intent)
    }
}