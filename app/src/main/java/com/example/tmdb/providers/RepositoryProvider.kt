package com.example.tmdb.providers

import com.example.tmdb.repository.MovieRepository
import com.example.tmdb.repository.MovieRepositoryImplementation
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryProvider {

    @Singleton
    @Provides
    fun providesMovieRepository(
        movieRepositoryImplementation: MovieRepositoryImplementation
    ): MovieRepository = movieRepositoryImplementation
}