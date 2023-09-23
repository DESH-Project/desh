package com.demo.desh.login

/*
import android.content.Context
import android.widget.Toast
import com.demo.desh.model.User
import com.demo.desh.viewModel.MainViewModel
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.NidOAuthLogin
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.navercorp.nid.profile.NidProfileCallback
import com.navercorp.nid.profile.data.NidProfileResponse

object NaverLogin : SocialLogin() {
    override fun login(
        context: Context,
        viewModel: MainViewModel,
        goToMapScreen: () -> Unit
    ) {
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

                    send(user, viewModel, goToMapScreen)
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
                getUserInfo()
            }
        }

        NaverIdLoginSDK.authenticate(context, oauthLoginCallback)
    }
}
*/