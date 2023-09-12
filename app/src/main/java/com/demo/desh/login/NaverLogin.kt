package com.demo.desh.login

import android.content.Context
import android.widget.Toast
import com.demo.desh.model.User
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.NidOAuthLogin
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.navercorp.nid.profile.NidProfileCallback
import com.navercorp.nid.profile.data.NidProfileResponse

object NaverLogin : SocialLogin() {
    override fun login(context: Context) {
        fun getUserInfo() {
            NidOAuthLogin().callProfileApi(object : NidProfileCallback<NidProfileResponse> {
                override fun onError(errorCode: Int, message: String) {
                    onFailure(errorCode, message)
                }

                override fun onFailure(httpStatus: Int, message: String) {
                    val errorCode = NaverIdLoginSDK.getLastErrorCode().code
                    val errorDesc = NaverIdLoginSDK.getLastErrorDescription()
                    Toast.makeText(
                        context,
                        "errorCode=$errorCode, errorDesc=$errorDesc",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onSuccess(result: NidProfileResponse) {
                    val email = result.profile?.email
                    val nickname = result.profile?.nickname
                    val profileImage = result.profile?.profileImage

                    val user = User(
                        email = email!!,
                        nickname = nickname!!,
                        profileImageUrl = profileImage!!
                    )

                    saveMemberIntoRoomDB(context, user)
                    send(context, user)
                }
            })
        }

        val oauthLoginCallback = object : OAuthLoginCallback {
            override fun onError(errorCode: Int, message: String) {
                onFailure(errorCode, message)
            }

            override fun onFailure(httpStatus: Int, message: String) {
                val errorCode = NaverIdLoginSDK.getLastErrorCode().code
                val errorDesc = NaverIdLoginSDK.getLastErrorDescription()
                Toast.makeText(context, "errorCode=$errorCode, errorDesc=$errorDesc", Toast.LENGTH_SHORT).show()
            }

            override fun onSuccess() {
                val accessToken = NaverIdLoginSDK.getAccessToken()
                val refreshToken = NaverIdLoginSDK.getRefreshToken()
                val expiresAt = NaverIdLoginSDK.getExpiresAt().toString()
                val tokenType = NaverIdLoginSDK.getTokenType()
                val state = NaverIdLoginSDK.getState().toString()

                getUserInfo()
            }
        }

        NaverIdLoginSDK.authenticate(context, oauthLoginCallback)
    }
}