package com.example.animelist.di

import com.example.animelist.data.repository.MediaRepositoryImpl
import com.example.animelist.domain.repository.MediaRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DomainModule {

    @Binds
    abstract fun bindMediaRepository(mediaRepositoryImpl: MediaRepositoryImpl): MediaRepository
}
