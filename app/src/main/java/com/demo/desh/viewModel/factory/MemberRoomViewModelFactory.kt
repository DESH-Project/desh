package com.demo.desh.viewModel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.demo.desh.access.repository.MemberRepository
import com.demo.desh.viewModel.MemberRoomViewModel

class MemberRoomViewModelFactory(
    private val memberRepository: MemberRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MemberRoomViewModel::class.java)) {
            return MemberRoomViewModel(memberRepository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}