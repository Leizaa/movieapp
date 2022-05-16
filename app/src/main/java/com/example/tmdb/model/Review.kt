package com.example.tmdb.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Review(
    @SerializedName("id")
    val id: Int,
    @SerializedName("author")
    val author: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("avatar_path")
    val avatarUrl: String?,
    @SerializedName("rating")
    val rating: Int?
) : Parcelable