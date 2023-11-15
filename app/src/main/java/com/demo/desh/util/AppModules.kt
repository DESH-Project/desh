package com.demo.desh.util

import android.content.Context
import androidx.room.Room
import com.demo.desh.access.AppDatabase
import com.demo.desh.access.ChatRetrofitDao
import com.demo.desh.access.RoomAccessDao
import com.demo.desh.access.UserRetrofitDao
import com.demo.desh.repository.ChatRetrofitRepository
import com.demo.desh.repository.UserRetrofitRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModules {
    @Singleton
    @Provides
    fun provideUserRetrofitRepository(userRetrofitDao: UserRetrofitDao, roomAccessDao: RoomAccessDao) : UserRetrofitRepository {
        return UserRetrofitRepository(userRetrofitDao, roomAccessDao)
    }

    @Singleton
    @Provides
    fun provideChatRetrofitRepository(chatRetrofitDao: ChatRetrofitDao) : ChatRetrofitRepository {
        return ChatRetrofitRepository(chatRetrofitDao)
    }

    @Singleton
    @Provides
    fun provideRetrofit() : Retrofit {
        val host = "good-place.shop"
        val domain = "http://$host/"

        return Retrofit.Builder()
                .baseUrl(domain)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    @Singleton
    @Provides
    fun provideUserRetrofitDao(retrofit: Retrofit) : UserRetrofitDao {
        return retrofit.create(UserRetrofitDao::class.java)
    }

    @Singleton
    @Provides
    fun provideChatRetrofitDao(retrofit: Retrofit) : ChatRetrofitDao {
        return retrofit.create(ChatRetrofitDao::class.java)
    }

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context) : AppDatabase {
        return Room
            .databaseBuilder(context, AppDatabase::class.java, "desh.db")
            .build()
    }

    @Singleton
    @Provides
    fun provideRoomAccessDao(appDatabase: AppDatabase) : RoomAccessDao {
        return appDatabase.RoomAccessDao()
    }
}