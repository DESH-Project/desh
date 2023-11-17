package com.demo.desh.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.desh.model.User
import com.demo.desh.repository.RoomRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class RoomViewModel(private val roomRepository: RoomRepository) : ViewModel() {
    fun insertLocalUser(user: User) {
        viewModelScope.launch {
            async(Dispatchers.IO) { roomRepository.insertLocalUser(user) }.await()
        }
    }

    private var _user = MutableLiveData<User>()
    val user : LiveData<User> get() = _user
    fun findLocalUser() {
        viewModelScope.launch {
            runBlocking {
                val def = async(Dispatchers.IO) { roomRepository.findLocalUser() }.await()
                if (def != null) _user.value = def
            }
        }
    }
}