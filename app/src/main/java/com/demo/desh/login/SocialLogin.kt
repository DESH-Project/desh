package com.demo.desh.login

import android.content.Context
import com.demo.desh.access.entity.Member
import com.demo.desh.model.User
import com.demo.desh.access.repository.UserRetrofitRepository
import com.demo.desh.viewModel.MainViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

abstract class SocialLogin {
    private val userRetrofitRepository = UserRetrofitRepository()

    abstract fun login(context: Context, viewModel: MainViewModel, goToMapScreen: () -> Unit)

    protected fun send(user: User, viewModel: MainViewModel, goToMapScreen: () -> Unit) {
        val result = userRetrofitRepository.login(user)

        result.enqueue(object : Callback<Long> {
            override fun onResponse(call: Call<Long>, response: Response<Long>) {
                user.id = response.body()

                saveMemberIntoRoomDB(user, viewModel)
                goToMapScreen()
            }

            override fun onFailure(call: Call<Long>, t: Throwable) {

            }
        })
    }

    protected fun saveMemberIntoRoomDB(user: User, viewModel: MainViewModel) {
        val member = Member(user)
        viewModel.insertMember(member)
    }
}