package com.demo.desh.util

import android.app.Application
import com.demo.desh.access.CacheRoomDatabase
import com.demo.desh.repository.RoomRepository
import com.demo.desh.viewModel.RoomViewModel

object RoomManager {
    fun getRoomViewModel(application: Application) : RoomViewModel {
        val roomDb = CacheRoomDatabase.getInstance(application)
        val roomDao = roomDb.roomAccessDao()
        val roomRepository = RoomRepository(roomDao)
        return RoomViewModel(roomRepository)
    }
}