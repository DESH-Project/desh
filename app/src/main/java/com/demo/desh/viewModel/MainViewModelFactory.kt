package com.demo.desh.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.demo.desh.access.repository.UserRetrofitRepository

class MainViewModelFactory(
    private val userRetrofitRepository: UserRetrofitRepository = UserRetrofitRepository()
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(userRetrofitRepository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}