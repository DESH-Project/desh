package com.demo.desh.viewModel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.demo.desh.access.repository.MemberRepository
import com.demo.desh.access.repository.UserRetrofitRepository
import com.demo.desh.viewModel.UserViewModel

class MainViewModelFactory(
    private val userRetrofitRepository: UserRetrofitRepository = UserRetrofitRepository(),
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            return UserViewModel(userRetrofitRepository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}