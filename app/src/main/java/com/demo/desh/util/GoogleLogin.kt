package com.demo.desh.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.demo.desh.LoginActivity
import com.demo.desh.R
import com.demo.desh.SurveyActivity
import com.demo.desh.api.ApiService
import com.demo.desh.api.RetrofitClient
import com.demo.desh.dto.User
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object GoogleLogin: SocialLogin {
    @SuppressLint("StaticFieldLeak")
    private lateinit var mGoogleSignInClient: GoogleSignInClient

    override fun init(context: Context) {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestIdToken(context.getString(R.string.GOOGLE_CLIENT_ID))
            .requestId()
            .requestProfile()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(context, gso)
    }

    fun getSignInClient(): GoogleSignInClient {
        return mGoogleSignInClient
    }

    fun alreadyLoginCheck(context: Context): GoogleSignInAccount? {
        return GoogleSignIn.getLastSignedInAccount(context)
    }

    fun handleLoginTask(context: Context, task: Task<GoogleSignInAccount>) {
        val account = task.result
        Log.d(LoginActivity.LOGIN_TAG, "구글 로그인 성공")
        Log.d(LoginActivity.LOGIN_TAG, account.toString())

        val user = User(
            email = account.email!!,
            nickname = account.account!!.name!!,
            profileImageUrl = account.photoUrl.toString()
        )

        send(context, user)
    }

    override fun send(context: Context, user: User) {
        val retrofit = RetrofitClient.getClient(RetrofitClient.domain)
        val apiService = retrofit.create(ApiService::class.java)
        val login = apiService.login(user)

        login.enqueue(object : Callback<Long> {
            override fun onResponse(call: Call<Long>, response: Response<Long>) {
                val id = response.body()

                user.id = id
                Toast.makeText(context, user.toString(), Toast.LENGTH_SHORT).show()

                val intent = Intent(context, SurveyActivity::class.java)
                intent.putExtra("user", user)
                context.startActivity(intent)
            }

            override fun onFailure(call: Call<Long>, t: Throwable) {
                Log.e(LoginActivity.LOGIN_TAG, "Google 로그인에 실패했습니다.")
                Log.e(LoginActivity.LOGIN_TAG, t.message!!)
            }
        })
    }
}
