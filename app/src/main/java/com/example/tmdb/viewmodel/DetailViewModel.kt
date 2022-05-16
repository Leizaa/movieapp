package com.example.tmdb.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tmdb.databinding.ActivityDetailBinding
import com.example.tmdb.databinding.ActivityMainBinding
import com.example.tmdb.model.MovieListResponse
import com.example.tmdb.model.ResponseResult
import com.example.tmdb.model.ReviewListResponse
import com.example.tmdb.repository.MovieUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val movieUseCases: MovieUseCases
) : ViewModel(), CoroutineScope {
    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private val _reviewsResponse: MutableLiveData<ResponseResult<ReviewListResponse>> = MutableLiveData()
    val reviewsResponse: LiveData<ResponseResult<ReviewListResponse>> = _reviewsResponse


    fun getReviews(type: String, movieId : Int) = launch {
        _reviewsResponse.value = ResponseResult.Loading(true)
        _reviewsResponse.value = movieUseCases.getReviews(type, movieId.toString())
        _reviewsResponse.value = ResponseResult.Loading(false)

    }
}