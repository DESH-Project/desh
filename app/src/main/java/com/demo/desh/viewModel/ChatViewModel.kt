package com.demo.desh.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.desh.model.ChatData
import com.demo.desh.model.ChatInfo
import com.demo.desh.repository.ChatRetrofitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatRetrofitRepository: ChatRetrofitRepository
) : ViewModel() {

    private var _open = MutableLiveData<Boolean>(false)
    val open : LiveData<Boolean> get() = _open

    /* 유저 아이디로 채팅방 리스트 가져오기 */
    private var _chatInfo = MutableLiveData<List<ChatInfo>>()
    val chatInfo : LiveData<List<ChatInfo>> get() = _chatInfo

    fun getChatroomList(userId: Long) {
        _open.value = true

        viewModelScope.launch {
            val def = async(Dispatchers.IO) { chatRetrofitRepository.getChatroomList(userId) }.await()

            if (def.isSuccessful) {
                val res = def.body()!!
                _chatInfo.value = res.data
                _open.value = false
            }
        }
    }

    /* 채팅방 상세조회 */
    private var _chatDetail = MutableLiveData<ChatData>()
    val chatDetail : LiveData<ChatData> get() = _chatDetail

    fun getChatDetail(chatroomId: Long) {
        _open.value = true

        viewModelScope.launch {
            val def = async(Dispatchers.IO) { chatRetrofitRepository.getChatDetail(chatroomId) }.await()

            if (def.isSuccessful) {
                val res = def.body()!!
                _chatDetail.value = res.data
                _open.value = false
            }
        }
    }
}