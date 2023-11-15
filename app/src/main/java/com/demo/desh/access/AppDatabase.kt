package com.demo.desh.access

import androidx.room.Database
import androidx.room.RoomDatabase
import com.demo.desh.model.RoomUser

@Database(entities = [RoomUser::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun RoomAccessDao() : RoomAccessDao
}