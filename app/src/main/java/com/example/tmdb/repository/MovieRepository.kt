package com.example.tmdb.repository

import com.example.tmdb.model.Movie
import com.example.tmdb.model.MovieListResponse
import com.example.tmdb.model.ResponseResult
import com.example.tmdb.model.ReviewListResponse
import com.example.tmdb.utility.FunctionalExtension
import javax.inject.Inject

interface MovieRepository {
    suspend fun getTrendingMovie(type: String): ResponseResult<MovieListResponse>
    suspend fun getDiscover(type: String): ResponseResult<MovieListResponse>
    suspend fun getReviewList(type: String, movieId: String): ResponseResult<ReviewListResponse>
}

class MovieRepositoryImplementation @Inject constructor(
    private val movieRemoteSource: MovieRemoteSource
) : MovieRepository {
    override suspend fun getTrendingMovie(type: String): ResponseResult<MovieListResponse> {
        return FunctionalExtension.await {
            movieRemoteSource.getTrending(type)
        }
    }

    override suspend fun getDiscover(type: String): ResponseResult<MovieListResponse> {
        return FunctionalExtension.await {
            movieRemoteSource.getDiscover(type)
        }
    }

    override suspend fun getReviewList(
        type: String,
        movieId: String
    ): ResponseResult<ReviewListResponse> {
        return FunctionalExtension.await {
            movieRemoteSource.getMovieReviewList(type, movieId)
        }
    }

}