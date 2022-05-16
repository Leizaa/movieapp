package com.example.tmdb.view.detail

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.tmdb.BuildConfig
import com.example.tmdb.databinding.ActivityDetailBinding
import com.example.tmdb.model.Movie
import com.example.tmdb.model.ResponseResult
import com.example.tmdb.model.Review
import com.example.tmdb.utility.Constants
import com.example.tmdb.viewmodel.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val viewModel by lazy {
        ViewModelProvider(this).get(DetailViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val movie: Movie = intent.extras!!.get(Constants.MOVIE) as Movie
        val movieType = intent.extras!!.get(Constants.MOVIE_TYPE) as String

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(true)

        val title = movie.title ?: movie.name!!

        supportActionBar!!.title = title

        setUpView(movie)
        getReview(movieType, movie.id)
        observeState()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            super.onBackPressed()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    private fun setUpView(movie: Movie) {
        binding.overviewContent.text = movie.overview

        val releaseDate = movie.releaseDate ?: movie.firstAirDate!!
        binding.releaseDate.text = releaseDate

        binding.voteTextView.text = movie.voteAverage.toString()

        Glide.with(applicationContext)
            .load(BuildConfig.IMAGE_BASE_URL + movie.posterPath)
            .into(binding.mainImage)
    }

    private fun getReview(movieType: String, movieId: Int) {
        viewModel.getReviews(movieType, movieId)
    }

    private fun observeState() {
        viewModel.reviewsResponse.observe(this) {
            when (it) {
                is ResponseResult.Loading -> handleLoading(it.isLoading)
                is ResponseResult.Success -> handleSuccess(it.data?.results)
                is ResponseResult.Error -> handleError(it.httpStatus)
            }
        }
    }

    private fun handleError(httpStatus: Int) {
        Toast.makeText(applicationContext, httpStatus, Toast.LENGTH_SHORT)
            .show()
    }

    private fun handleSuccess(reviews: List<Review>?) {
        Log.d("detail", reviews.toString())
    }

    private fun handleLoading(isLoading: Boolean) {
        binding.loadingLayout.isVisible = isLoading
    }
}