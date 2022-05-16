package com.example.tmdb.view.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.tmdb.BuildConfig
import com.example.tmdb.databinding.DashboardFragmentBinding
import com.example.tmdb.model.Movie
import com.example.tmdb.model.ResponseResult
import com.example.tmdb.utility.Constants
import com.example.tmdb.view.detail.DetailActivity
import com.example.tmdb.view.main.adapter.MovieAdapter
import com.example.tmdb.viewmodel.PageViewModel
import com.synnapps.carouselview.ImageListener
import dagger.hilt.android.AndroidEntryPoint

/**
 * A placeholder fragment containing a simple view.
 */
@AndroidEntryPoint
class PageFragment : Fragment(), MovieAdapter.ItemClickListener {

    private val pageViewModel: PageViewModel by lazy {
        ViewModelProvider(this).get(PageViewModel::class.java)
    }

    private var _binding: DashboardFragmentBinding? = null
    private val binding get() = _binding!!

    private val trendingAdapter = MovieAdapter(this)
    private val discoverAdapter = MovieAdapter(this)

    private var section: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeState()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DashboardFragmentBinding.inflate(inflater, container, false)

        section = arguments?.get(ARG_SECTION_NUMBER) as Int

        initTrendingRecyclerView()
        initDiscoverRecyclerView()
        setUpView()
        setUpCarousel()
        return binding.root
    }

    private fun setUpView() {
        pageViewModel.getTrending(
            getType(section)
        )

        pageViewModel.getDiscover(
            getType(section)
        )
    }

    private fun setUpCarousel() {
        val carouselView = binding.carouselView
        carouselView.pageCount = Constants.CAROUSEL_PAGE_COUNT
        carouselView.setImageListener(imageListener)
    }

    private var imageListener: ImageListener = ImageListener {
            position,
            imageView ->
        run {
            val imageUrl = if (getType(this.section) == Constants.MOVIE) {
                BuildConfig.IMAGE_BASE_URL + Constants.MOVIE_ARRAY[position]
            } else {
                BuildConfig.IMAGE_BASE_URL + Constants.TV_ARRAY[position]
            }
            Glide.with(requireContext())
                .load(imageUrl)
                .centerCrop()
                .into(imageView)
        }
    }

    private fun initTrendingRecyclerView() {
        binding.trendingRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.trendingRecyclerView.adapter = trendingAdapter
    }

    private fun initDiscoverRecyclerView() {
        binding.discoverRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.discoverRecyclerView.adapter = discoverAdapter
    }

    private fun observeState() {
        pageViewModel.trendingResponse.observe(this) {
            when (it) {
                is ResponseResult.Loading -> handleLoading(it.isLoading)
                is ResponseResult.Success -> handleTrendingSuccess(it.data?.results)
                is ResponseResult.Error -> handleError(it.httpStatus)
            }
        }

        pageViewModel.discoverResponse.observe(this) {
            when (it) {
                is ResponseResult.Success -> handleDiscoverSuccess(it.data?.results)
                is ResponseResult.Error -> handleError(it.httpStatus)
                else -> {}
            }
        }
    }

    private fun handleLoading(isLoading: Boolean) {
        binding.loadingLayout.isVisible = isLoading
    }

    private fun handleTrendingSuccess(movieList: List<Movie>?) {
        Log.d("trending", movieList.toString())
        movieList?.let { trendingAdapter.setMovie(it) }
    }

    private fun handleDiscoverSuccess(movieList: List<Movie>?) {
        Log.d("discover", movieList.toString())
        movieList?.let { discoverAdapter.setMovie(it) }
    }

    private fun handleError(httpStatus: Int) {
        Toast.makeText(requireContext(), httpStatus, Toast.LENGTH_SHORT)
            .show()
    }

    companion object {

        private const val ARG_SECTION_NUMBER = "section_number"

        @JvmStatic
        fun newInstance(sectionNumber: Int): PageFragment {
            return PageFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getType(section: Int): String {
        return if (section == 1)
            Constants.TYPE_MOVIE
        else {
            Constants.TYPE_TV
        }
    }

    override fun onItemClick(movie: Movie) {
        val intent = Intent(context, DetailActivity::class.java)
        intent.putExtra(Constants.MOVIE, movie)
        intent.putExtra(Constants.MOVIE_TYPE, getType(section as Int))

        Log.d("page", "movie passed $movie")
        context?.startActivity(intent)
    }
}