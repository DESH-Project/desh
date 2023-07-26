package com.demo.desh.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.desh.dto.User
import com.demo.desh.repository.UserRepository
import kotlinx.coroutines.launch
import retrofit2.Call

class UserViewModel : ViewModel() {
    private val repository = UserRepository()
    private val _user = MutableLiveData<Call<Long>>()
    val user: LiveData<Call<Long>> = _user

    fun fetchUserLogin(user: User) {
        viewModelScope.launch {
            try {
                val user = repository.login(user)
                _user.value = user
            } catch (e: Exception) {
                Log.e(TAG, "로그인 실패 ${e.printStackTrace()}")
            }
        }
    }

    companion object {
        private const val TAG = "UserViewModel"
    }
}