package com.example.tmdb.providers

import com.example.tmdb.repository.MovieUseCases
import com.example.tmdb.repository.MovieUseCasesImplementation
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseProvider {

    @Singleton
    @Provides
    fun providesMovieUseCase(
        useCases: MovieUseCasesImplementation
    ): MovieUseCases = useCases
}