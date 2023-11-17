package com.demo.desh.repository

import android.util.Log
import com.demo.desh.access.RoomAccessDao
import com.demo.desh.model.User

class RoomRepository(private val roomAccessDao: RoomAccessDao) {
    companion object {
        private const val TAG = "RoomRepository"

        private fun logging(method: String, res: Any) {
            Log.e(TAG, "method = $method, res = $res")
        }
    }


    fun insertLocalUser(user: User) =
        user.toRoomUser()?.let {
                roomAccessDao.insertUser(it)
                logging("insertLocalUser", Unit)
            }

    fun findLocalUser() : User? =
        roomAccessDao.findLocalUser()
            ?.toUser()
            ?.let {
                logging("findLocalUser", it.toString())
                it
            }
}