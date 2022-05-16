package com.example.tmdb.repository

import com.example.tmdb.model.Movie
import com.example.tmdb.model.MovieListResponse
import com.example.tmdb.model.ReviewListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieRemoteSource {

    @GET("trending/{type}/day")
    suspend fun getTrending(@Path("type") type: String): Response<MovieListResponse>

    @GET("discover/{type}")
    suspend fun getDiscover(@Path("type") type: String): Response<MovieListResponse>

    @GET("{type}/{movie-id}/reviews")
    suspend fun getMovieReviewList(
        @Path("type") type: String,
        @Path("movie-id") movieId: String
    ): Response<ReviewListResponse>
}