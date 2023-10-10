package com.demo.desh.util.login

import android.content.Context
import android.util.Log
import com.demo.desh.model.User
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

object KakaoLogin: SocialLogin() {
    private const val TAG = "KakaoLoginObject"

    override suspend fun login(context: Context) : User? {
        var kakaoUser: User?

        coroutineScope {
            val def = async {
                val mCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
                    if (error != null) Log.e(TAG, "로그인 실패 $error")
                    else if (token != null) Log.e(
                        TAG,
                        "로그인 성공 ${token.accessToken} ${token.idToken}"
                    )
                }

                val instance = UserApiClient.instance
                var kUser: User? = null

                if (instance.isKakaoTalkLoginAvailable(context)) {
                    instance.loginWithKakaoTalk(context) { token, error ->
                        // 로그인 실패
                        if (error != null) {
                            Log.e(TAG, "로그인 실패 $error")

                            // 사용자가 취소 버튼을 눌렀을 때
                            if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                                return@loginWithKakaoTalk
                            } else {
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

                        kUser = User(
                            nickname = nickname!!,
                            email = email!!,
                            profileImageUrl = profileImageUrl!!,
                        )
                    }
                }

                kUser

            }.await()

            kakaoUser = def
        }

        return kakaoUser
    }
}