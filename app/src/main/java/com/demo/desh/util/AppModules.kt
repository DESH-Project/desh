package com.demo.desh.util

import com.demo.desh.access.ChatRetrofitDao
import com.demo.desh.access.UserRetrofitDao
import com.demo.desh.repository.ChatRetrofitRepository
import com.demo.desh.repository.UserRetrofitRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModules {
    @Singleton
    @Provides
    fun provideUserRetrofitRepository(userRetrofitDao: UserRetrofitDao) : UserRetrofitRepository {
        return UserRetrofitRepository(userRetrofitDao)
    }

    @Singleton
    @Provides
    fun provideChatRetrofitRepository(chatRetrofitDao: ChatRetrofitDao) : ChatRetrofitRepository {
        return ChatRetrofitRepository(chatRetrofitDao)
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
    fun provideRetrofit() : Retrofit {
        val host = "good-place.shop"
        val domain = "http://$host/"

        return Retrofit.Builder()
            .baseUrl(domain)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}