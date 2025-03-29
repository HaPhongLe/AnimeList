package com.example.animelist.di

import android.app.Application
import android.content.Context
import com.example.animelist.ui.util.ResourceProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideContext(application: Application): Context = application.applicationContext

    @Provides
    @Singleton
    fun provideResourceProvider(context: Context): ResourceProvider = ResourceProvider(context)
}
