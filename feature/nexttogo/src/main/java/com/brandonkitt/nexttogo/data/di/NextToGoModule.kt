package com.brandonkitt.nexttogo.data.di

import com.brandonkitt.nexttogo.data.implementations.NextToGoRepositoryImpl
import com.brandonkitt.nexttogo.data.interfaces.NextToGoRepository
import com.brandonkitt.nexttogo.data.interfaces.NextToGoService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Specific module for this feature to implement DI specifically
 * related to this feature.
 */
@Module
@InstallIn(SingletonComponent::class)
object NextToGoModule {
    // Provides the WebService for this feature, utilizing the existing
    // retrofit core dependency
    @Provides
    @Singleton
    fun provideNextToGoService(retrofit: Retrofit): NextToGoService {
        return retrofit.create(NextToGoService::class.java)
    }
    // Provides the repository for this feature
    @Provides
    @Singleton
    fun provideNextToGoRepository(repositoryImpl: NextToGoRepositoryImpl): NextToGoRepository {
        return repositoryImpl
    }
}