package com.example.tmdb.view.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tmdb.BuildConfig
import com.example.tmdb.databinding.MovieItemViewBinding
import com.example.tmdb.model.Movie

class MovieAdapter(var itemClickListener: ItemClickListener) : RecyclerView.Adapter<MovieViewHolder>() {

    var movies = mutableListOf<Movie>()

    fun setMovie(movies: List<Movie>) {
        this.movies = movies.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MovieItemViewBinding.inflate(inflater, parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]

        holder.itemView.setOnClickListener {
            itemClickListener.onItemClick(movie)
        }

        Glide.with(holder.itemView.context)
            .load(BuildConfig.IMAGE_BASE_URL + movie.posterPath)
            .into(holder.binding.movieImage)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    public interface ItemClickListener {
        fun onItemClick(movie: Movie)
    }
}

class MovieViewHolder(
    val binding: MovieItemViewBinding
) : RecyclerView.ViewHolder(binding.root) {
}