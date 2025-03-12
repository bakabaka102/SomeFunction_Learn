package com.app.func.coroutine_demo.retrofit.aaa

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.func.base_content.DiffCallBack
import com.app.func.databinding.ItemMovieBinding
import com.bumptech.glide.Glide

class MovieAdapter : RecyclerView.Adapter<MainViewHolder>() {

    private var movieList = mutableListOf<Movie>()

    fun setMovies(movies: List<Movie>) {
        val diffResult = calculateDiff(movies)
        movieList.clear()
        movieList.addAll(movies)
        diffResult.dispatchUpdatesTo(this)
    }

    private fun calculateDiff(newList: List<Movie>): DiffUtil.DiffResult = DiffUtil.calculateDiff(
        DiffCallBack(
            oldList = movieList,
            newList = newList,
            areItemsTheSame = { oldItem, newItem ->
                oldItem.name == newItem.name
            },
            areContentsTheSame = { oldItem, newItem ->
                oldItem == newItem
            }
        )
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMovieBinding.inflate(inflater, parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {

        val movie = movieList[position]
        if (ValidationUtil.isValidateMovies(movie)) {
            holder.binding.name.text = movie.name
            Glide.with(holder.itemView.context).load(movie.imageUrl).into(holder.binding.imageview)
        }
    }

    override fun getItemCount(): Int {
        return movieList.size
    }
}

class MainViewHolder(val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root)