package com.example.animelist.di

import com.apollographql.apollo3.ApolloClient
import com.example.animelist.data.ApolloAnimeClient
import com.example.animelist.domain.AnimeClient
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
    fun provideApolloClient(): ApolloClient =
        ApolloClient.Builder()
            .serverUrl("https://graphql.anilist.co")
            .build()

    @Provides
    @Singleton
    fun provideAnimeClient(apolloClient: ApolloClient): AnimeClient = ApolloAnimeClient(apolloClient)
}
