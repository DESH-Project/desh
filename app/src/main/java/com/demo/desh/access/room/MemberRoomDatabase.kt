package com.demo.desh.access.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.demo.desh.access.dao.MemberDao
import com.demo.desh.access.entity.Member

@Database(entities = [(Member::class)], version = 1)
abstract class MemberRoomDatabase : RoomDatabase() {

    abstract fun memberDao() : MemberDao

    companion object {
        private var INSTANCE: MemberRoomDatabase? = null

        fun getInstance(context: Context) : MemberRoomDatabase {
            synchronized(this) {
                return INSTANCE
                    ?:
                Room.databaseBuilder(
                    context.applicationContext,
                    MemberRoomDatabase::class.java,
                    "member_database"
                    )
                    .fallbackToDestructiveMigration()
                    .build()
            }
        }
    }
}