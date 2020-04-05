package com.example.moviecatalogue2.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviecatalogue2.R
import com.example.moviecatalogue2.entities.Movie
import kotlinx.android.synthetic.main.item_movie_search.view.*

class MovieSearchAdapter(private val listMovie: ArrayList<Movie>) :
    RecyclerView.Adapter<MovieSearchAdapter.MovieSearchViewHolder>() {
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setData(items: ArrayList<Movie>) {
        listMovie.clear()
        listMovie.addAll(items)
        notifyDataSetChanged()
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MovieSearchViewHolder {
        val view =
            LayoutInflater.from(viewGroup.context).inflate(R.layout.item_movie_search, viewGroup, false)
        return MovieSearchViewHolder(view)
    }

    override fun getItemCount(): Int = listMovie.size

    override fun onBindViewHolder(holder: MovieSearchViewHolder, position: Int) {
        holder.bind(listMovie[position])
    }

    inner class MovieSearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(movie: Movie) {
            val url = "https://image.tmdb.org/t/p/w185/${movie.photo}"
            with(itemView) {
                Glide.with(itemView.context)
                    .load(url)
                    .into(img_photo)
                txt_name.text = movie.name
                txt_description.text = movie.description

                itemView.setOnClickListener {
                    onItemClickCallback?.onItemClicked(movie)
                }
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Movie)
    }
}