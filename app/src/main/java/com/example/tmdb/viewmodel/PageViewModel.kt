package com.example.tmdb.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tmdb.model.MovieListResponse
import com.example.tmdb.model.ResponseResult
import com.example.tmdb.repository.MovieUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class PageViewModel @Inject constructor(
    private val movieUseCases: MovieUseCases
) : ViewModel(), CoroutineScope {

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private val _trendingResponse: MutableLiveData<ResponseResult<MovieListResponse>> = MutableLiveData()
    val trendingResponse: LiveData<ResponseResult<MovieListResponse>> = _trendingResponse

    private val _discoverResponse: MutableLiveData<ResponseResult<MovieListResponse>> = MutableLiveData()
    val discoverResponse: LiveData<ResponseResult<MovieListResponse>> = _discoverResponse

    fun getTrending(type: String) = launch {
        _trendingResponse.value = ResponseResult.Loading(true)
        _trendingResponse.value = movieUseCases.getTrending(type)
        _trendingResponse.value = ResponseResult.Loading(false)
    }

    fun getDiscover(type: String) = launch {
        _discoverResponse.value = movieUseCases.getDiscover(type)
    }
}