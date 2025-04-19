package com.example.animelist.di

import com.apollographql.apollo3.ApolloClient
import com.example.animelist.data.api.ApolloMediaApi
import com.example.animelist.data.api.MediaApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
    fun provideAnimeClient(apolloClient: ApolloClient): MediaApi = ApolloMediaApi(apolloClient)
}
