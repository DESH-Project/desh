package com.demo.desh.access

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.demo.desh.model.RoomUser
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

@Database(entities = [(RoomUser::class)], version = 1)
abstract class CacheRoomDatabase : RoomDatabase() {
    abstract fun roomAccessDao() : RoomAccessDao companion object {
        private var INSTANCE : CacheRoomDatabase? = null

        @OptIn(InternalCoroutinesApi::class)
        fun getInstance(context: Context) : CacheRoomDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        CacheRoomDatabase::class.java,
                        "desh_db"
                    )
                        .fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}