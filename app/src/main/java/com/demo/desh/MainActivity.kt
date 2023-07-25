package com.demo.desh

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.unit.dp
import com.demo.desh.api.ApiService
import com.demo.desh.api.RetrofitClient
import com.demo.desh.dto.KakaoUser
import com.demo.desh.ui.theme.DeshprojectfeTheme
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@SuppressLint("StaticFieldLeak")
private lateinit var mGoogleSignInClient: GoogleSignInClient
private const val LOGIN_TAG = "MainActivity_LOGIN"

class MainActivity : ComponentActivity() {
    private val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestEmail()
        .build()

    private var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == 1) {
            val data = result.data
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        val account = completedTask.result
        val email = account?.email
        val familyName = account?.familyName

        Log.d(LOGIN_TAG, "구글 로그인 성공")
        Log.d(LOGIN_TAG, "email=$email, familyName=$familyName")
    }

    fun signIn(context: Context) {
        mGoogleSignInClient = GoogleSignIn.getClient(context, gso)
        val signInIntent = mGoogleSignInClient.signInIntent
        resultLauncher.launch(signInIntent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val nativeAppKey = applicationContext.resources.getString(R.string.KAKAO_NATIVE_APP_KEY)
        KakaoSdk.init(this, nativeAppKey)

        Log.d(LOGIN_TAG, "keyhash : ${Utility.getKeyHash(this)}")
        Log.d(LOGIN_TAG, "kakao_native_app_key : $nativeAppKey")

        setContent {
            val navController = rememberNavController()
            DeshprojectfeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainActivityScreen()
                }
            }
        }
    }
}

@Composable
fun MainActivityScreen() {
    val loginWithKakaoText = "Sign In With Kakao"
    val loginWithGoogleText = "Sign In With Google"
    val context = LocalContext.current
//    val intent = Intent(context, SurveyActivity::class.java)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        SocialLoginButton(
            text = loginWithKakaoText,
            imageResource = R.drawable.kakao_login_large_narrow,
            onClick = { kakaoLogin(context) }
        )

        Spacer(modifier = Modifier.padding(16.dp))

        SocialLoginButton(
            text = loginWithGoogleText,
            imageResource = R.drawable.btn_google_signin_light_focus_web,
            onClick = { MainActivity().signIn(context) }
        )

        Spacer(modifier = Modifier.padding(120.dp))

        TestLoginButtonWithMockUser(context = context)

    }
}

@Composable
fun TestCICDRequest() {
    val client = RetrofitClient.getClient(RetrofitClient.domain)
    val create = client.create(ApiException::class.java)
}

@Composable
fun SocialLoginButton(
    text: String,
    imageResource: Int,
    onClick: () -> Unit = {}
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
            .padding(start = 16.dp, end = 16.dp),
        shape = RoundedCornerShape(6.dp)
    ) {
        Image(
            painter = painterResource(id = imageResource),
            contentDescription = text
        )
    }
}

private fun kakaoLogin(context: Context) {
    val mCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) Log.e(LOGIN_TAG, "로그인 실패 $error")
        else if (token != null) Log.e(LOGIN_TAG, "로그인 성공 ${token.accessToken} ${token.idToken}")
    }

    val instance = UserApiClient.instance
    var kakaoUser: KakaoUser? = null

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

            // 로그인 성공
            else if (token != null) {
            }
        }
    }

    instance.me { user, error ->
        if (error != null) Log.e(LOGIN_TAG, "사용자 정보 요청 실패 $error")
        else if (user != null) {
            Log.e(LOGIN_TAG, "사용자 정보 요청 성공 $user")

            val account = user.kakaoAccount

            val nickname = account?.profile?.nickname
            val ageRange = account?.ageRange?.toString()
            val email = account?.email
            val profileImageUrl = account?.profile?.profileImageUrl
            val gender = account?.gender?.toString()

            kakaoUser = KakaoUser(
                nickname = nickname!!,
                ageRange = ageRange!!,
                email = email!!,
                profileImageUrl = profileImageUrl!!,
                gender = gender!!
            )

            Log.d(LOGIN_TAG, "서버에 전송합니다")
            sendToServer(context, kakaoUser!!)
        }
    }
}

private fun sendToServer(context: Context, kakaoUser: KakaoUser) {
    val retrofit = RetrofitClient.getClient(RetrofitClient.domain)
    val apiService = retrofit.create(ApiService::class.java)
    val result = apiService.login(kakaoUser)

    result.enqueue(object : Callback<Long> {
        override fun onResponse(call: Call<Long>, response: Response<Long>) {
            val id = response.body()

            kakaoUser.id = id
            Toast.makeText(context, kakaoUser.toString(), Toast.LENGTH_SHORT).show()

            val intent = Intent(context, SurveyActivity::class.java)
            intent.putExtra("user", kakaoUser)
            context.startActivity(intent)
        }

        override fun onFailure(call: Call<Long>, t: Throwable) {
            Log.e(LOGIN_TAG, "로그인에 실패했습니다.")
            Log.e(LOGIN_TAG, t.message!!)
        }
    })
}

@Composable
private fun TestLoginButtonWithMockUser(context: Context) {
    val onButtonClick = {
        val mockUser = KakaoUser(
            id = -1L,
            nickname = "황승수",
            ageRange = "AGE_20_29",
            profileImageUrl = "https://k.kakaocdn.net/dn/JEY2d/btsmuprjeuP/BZOMvMtSrWze5Ymq2hoJX1/img_640x640.jpg",
            email = "h970126@gmail.com",
            gender = "MAIL"
        )

        val intent = Intent(context, SurveyActivity::class.java)
        intent.putExtra("user", mockUser)
        context.startActivity(intent)
    }

    Button(onClick = onButtonClick) {
        Text(text = "Test Button To Next Step")
    }
}

@Preview(showBackground = true)
@Composable
fun MainActivityScreenPreview() {
    DeshprojectfeTheme {
        MainActivityScreen()
    }
}
