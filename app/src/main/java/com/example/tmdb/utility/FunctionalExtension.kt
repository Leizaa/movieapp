package com.example.tmdb.utility

import android.util.Log
import com.example.tmdb.R
import com.example.tmdb.model.ResponseResult
import retrofit2.Response

object FunctionalExtension {

    suspend fun <T> await(
        apiCall: suspend () -> Response<T>?
    ): ResponseResult<T> {
        return try {
            val callResult = apiCall()
            callResult?.let { it ->
                if (it.isSuccessful) return ResponseResult.Success(callResult.body())
                Log.d("call", callResult.errorBody().toString())
                return ResponseResult.Error(R.string.failed_response)
            }
            ResponseResult.Error(R.string.error_response)
        } catch (exception: Exception) {
            exception.printStackTrace()
            return ResponseResult.Error(R.string.error_response)
        }
    }
}