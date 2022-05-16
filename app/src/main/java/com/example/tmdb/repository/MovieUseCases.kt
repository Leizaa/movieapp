package com.example.tmdb.repository

import com.example.tmdb.model.MovieListResponse
import com.example.tmdb.model.ResponseResult
import com.example.tmdb.model.ReviewListResponse
import javax.inject.Inject

interface MovieUseCases {
    suspend fun getTrending(type: String): ResponseResult<MovieListResponse>
    suspend fun getDiscover(type: String): ResponseResult<MovieListResponse>
    suspend fun getReviews(type: String, movieId: String): ResponseResult<ReviewListResponse>
}

class MovieUseCasesImplementation @Inject constructor(
    private val movieRepository: MovieRepository
) : MovieUseCases {
    override suspend fun getTrending(type: String): ResponseResult<MovieListResponse> {
        return movieRepository.getTrendingMovie(type)
    }

    override suspend fun getDiscover(type: String): ResponseResult<MovieListResponse> {
        return movieRepository.getDiscover(type)
    }

    override suspend fun getReviews(type: String, movieId: String): ResponseResult<ReviewListResponse> {
        return movieRepository.getReviewList(type, movieId)
    }
}