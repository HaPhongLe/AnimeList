package com.example.animelist.di

import android.content.Context
import androidx.room.Room
import com.apollographql.apollo3.ApolloClient
import com.example.animelist.data.api.ApolloMediaApi
import com.example.animelist.data.api.MediaApi
import com.example.animelist.data.local.AppDatabase
import com.example.animelist.data.local.MIGRATION_1_2
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideApolloClient(): ApolloClient =
        ApolloClient.Builder()
            .serverUrl("https://graphql.anilist.co")
            .build()

    @Provides
    @Singleton
    fun provideMediaClient(apolloClient: ApolloClient): MediaApi = ApolloMediaApi(apolloClient)

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder<AppDatabase>(
            context = context,
            name = AppDatabase.TABLE_NAME
        )
            .addMigrations(MIGRATION_1_2)
            .build()

    @Provides
    fun provideMediaDao(appDatabase: AppDatabase) = appDatabase.mediaDao()
}
