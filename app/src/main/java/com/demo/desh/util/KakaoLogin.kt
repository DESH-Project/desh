package com.demo.desh.util

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.demo.desh.LoginActivity.Companion.LOGIN_TAG
import com.demo.desh.R
import com.demo.desh.SurveyActivity
import com.demo.desh.api.ApiService
import com.demo.desh.api.RetrofitClient
import com.demo.desh.dto.User
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object KakaoLogin: SocialLogin {
    override fun init(context: Context) {
        val nativeAppKey = context.resources.getString(R.string.KAKAO_NATIVE_APP_KEY)
        KakaoSdk.init(context, nativeAppKey)

        Log.d(LOGIN_TAG, "keyhash : ${Utility.getKeyHash(context)}")
        Log.d(LOGIN_TAG, "kakao_native_app_key : $nativeAppKey")
    }

    fun login(context: Context) {
        val mCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) Log.e(LOGIN_TAG, "로그인 실패 $error")
            else if (token != null) Log.e(LOGIN_TAG, "로그인 성공 ${token.accessToken} ${token.idToken}")
        }

        val instance = UserApiClient.instance
        var kakaoUser: User? = null

        Log.d(LOGIN_TAG, "Kakao Login 시도")

        if (instance.isKakaoTalkLoginAvailable(context)) {
            instance.loginWithKakaoTalk(context) { token, error ->
                // 로그인 실패
                if (error != null) {
                    Log.e(LOGIN_TAG, "로그인 실패 $error")

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
            if (error != null) Log.e(LOGIN_TAG, "사용자 정보 요청 실패 $error")
            else if (user != null) {
                Log.e(LOGIN_TAG, "사용자 정보 요청 성공 $user")

                val account = user.kakaoAccount

                val nickname = account?.profile?.nickname
                val email = account?.email
                val profileImageUrl = account?.profile?.profileImageUrl

                kakaoUser = User(
                    nickname = nickname!!,
                    email = email!!,
                    profileImageUrl = profileImageUrl!!,
                )

                Log.d(LOGIN_TAG, "서버에 전송합니다")
                send(context, kakaoUser!!)
            }
        }
    }

    override fun send(context: Context, user: User) {
        val retrofit = RetrofitClient.getClient(RetrofitClient.domain)
        val apiService = retrofit.create(ApiService::class.java)
        val result = apiService.login(user)

        result.enqueue(object : Callback<Long> {
            override fun onResponse(call: Call<Long>, response: Response<Long>) {
                val id = response.body()

                user.id = id
                Toast.makeText(context, user.toString(), Toast.LENGTH_SHORT).show()

                val intent = Intent(context, SurveyActivity::class.java)
                intent.putExtra("user", user)
                context.startActivity(intent)
            }

            override fun onFailure(call: Call<Long>, t: Throwable) {
                Log.e(LOGIN_TAG, "Kakao 로그인에 실패했습니다.")
                Log.e(LOGIN_TAG, t.message!!)
            }
        })
    }
}