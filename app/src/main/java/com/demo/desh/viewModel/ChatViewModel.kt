package com.demo.desh.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.desh.model.Chat
import com.demo.desh.model.ChatDetail
import com.demo.desh.model.ChatPreview
import com.demo.desh.repository.ChatRetrofitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatRetrofitRepository: ChatRetrofitRepository
) : ViewModel() {

    private var _open = MutableLiveData<Boolean>(false)
    val open : LiveData<Boolean> get() = _open

    /* 유저 아이디로 채팅방 리스트 가져오기 */
    private var _chatPreviewList = MutableLiveData<List<ChatPreview>>()
    val chatPreviewList : LiveData<List<ChatPreview>> get() = _chatPreviewList

    fun getChatroomList(userId: Long) {
        _open.value = true

        viewModelScope.launch {
            val def = async(Dispatchers.IO) { chatRetrofitRepository.getChatroomList(userId) }.await()

            if (def.isSuccessful) {
                val res = def.body()!!
                _chatPreviewList.value = res.data
                _open.value = false
            }
        }
    }



    /* 채팅방 상세조회 */
    private var _chatDetail = MutableLiveData<ChatDetail>()
    val chatDetail : LiveData<ChatDetail> get() = _chatDetail

    fun getChatDetail(chatroomId: Long) {
        _open.value = true

        viewModelScope.launch {
            val def = async(Dispatchers.IO) { chatRetrofitRepository.getChatDetail(chatroomId) }.await()

            if (def.isSuccessful) {
                val res = def.body()!!

                // 더미 주입
                res.data.chats.addAll(makeFakeChat())

                _chatDetail.value = res.data
                _open.value = false
            }
        }
    }

    // dummy chat generator
    fun makeFakeChat() : List<Chat> {
        return listOf(
            Chat(
                id = 1,
                writerId = 1,
                writer = "황승수",
                message = "안녕하세요",
                date = LocalDateTime.now().minusHours(2)
            ),

            Chat(
                id = 2,
                writerId = 2,
                writer = "김동열",
                message = "네 반갑습니다. 날씨가 좋네요",
                date = LocalDateTime.now().minusHours(1)
            ),


            Chat(
                id = 1,
                writerId = 1,
                writer = "황승수",
                message = "이 에러에 대해 아시나요? 2023-11-15 20:03:34.311  1598-5473  WindowManager           system_server                        E  win=Window{15515f3 u0 com.demo.desh/com.demo.desh.MainActivity} destroySurfaces: appStopped=true cleanupOnResume=false win.mWindowRemovalAllowed=false win.mRemoveOnExit=false win.mViewVisibility=8 caller=com.android.server.wm.ActivityRecord.destroySurfaces:6776 com.android.server.wm.ActivityRecord.destroySurfaces:6757 com.android.server.wm.ActivityRecord.notifyAppStopped:6821 com.android.server.wm.ActivityRecord.activityStopped:7427 com.android.server.wm.ActivityClientController.activityStopped:263 android.app.IActivityClientController\$Stub.onTransact:621 com.android.server.wm.ActivityClientController.onTransact:141 \n",
                date = LocalDateTime.now().minusMinutes(30)
            ),

            Chat(
                id = 2,
                writerId = 2,
                writer = "김동열",
                message = "아니요......................",
                date = LocalDateTime.now().minusMinutes(20)
            )
        )
    }
}