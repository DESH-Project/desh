package com.demo.desh.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.desh.access.entity.Member
import com.demo.desh.access.repository.MemberRepository
import kotlinx.coroutines.launch

class MemberRoomViewModel(
    private val memberRepository: MemberRepository
) : ViewModel() {
    private val _member = MutableLiveData<Member?>()
    val member: LiveData<Member?> get() = _member

    fun deleteAllMember() {
        viewModelScope.launch {
            memberRepository.deleteAllMember()
        }
    }
}