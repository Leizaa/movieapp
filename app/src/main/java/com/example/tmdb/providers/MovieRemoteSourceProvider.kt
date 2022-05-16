package com.example.tmdb.providers

import com.example.tmdb.repository.MovieRemoteSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MovieRemoteSourceProvider {

    @Singleton
    @Provides
    fun provideMovieRemoteSourceProvider(retrofit: Retrofit): MovieRemoteSource {
        return retrofit.create(MovieRemoteSource::class.java)
    }
}