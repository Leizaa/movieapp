package com.example.tmdb.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieListResponse(
    @SerializedName("results")
    val results: List<Movie>? = listOf(),
    @SerializedName("page")
    val page: Int,
    @SerializedName("total_pages")
    val totalPage: Int
) : Parcelable