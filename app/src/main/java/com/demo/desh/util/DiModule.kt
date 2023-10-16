package com.demo.desh.util

import com.demo.desh.access.repository.UserRetrofitRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DiModule {
    @Singleton
    @Provides
    fun provideUserRetrofitRepository() : UserRetrofitRepository {
        return UserRetrofitRepository()
    }
}