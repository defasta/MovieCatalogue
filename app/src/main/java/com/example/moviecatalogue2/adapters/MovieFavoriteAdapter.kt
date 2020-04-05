package com.example.moviecatalogue2.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviecatalogue2.R
import com.example.moviecatalogue2.entities.Movie
import kotlinx.android.synthetic.main.item_movie_favorite.view.img_photo
import kotlinx.android.synthetic.main.item_movie_favorite.view.txt_description
import kotlinx.android.synthetic.main.item_movie_favorite.view.txt_name

class MovieFavoriteAdapter: RecyclerView.Adapter<MovieFavoriteAdapter.MovieFavoriteViewHolder>() {
    private var onItemClickCallback: OnItemClickCallback? = null
    var listMovieFavorite = ArrayList<Movie>()
    set(listMovieFavorite){
//        if (listMovieFavorite.size>0){
            this.listMovieFavorite.clear()
  //      }
        this.listMovieFavorite.addAll(listMovieFavorite)
        notifyDataSetChanged()
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun addItem(movie: Movie){
        this.listMovieFavorite.add(movie)
        notifyItemInserted(this.listMovieFavorite.size -1)
    }

    fun updateItem(position: Int, movie: Movie){
        this.listMovieFavorite[position] = movie
        notifyItemChanged(position, movie)
    }

    fun removeItem(position: Int){
        this.listMovieFavorite.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, this.listMovieFavorite.size)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieFavoriteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie_favorite, parent, false)
        return MovieFavoriteViewHolder(view)
    }

    override fun getItemCount(): Int = this.listMovieFavorite.size

    override fun onBindViewHolder(
        holder: MovieFavoriteViewHolder,
        position: Int
    ) {
        holder.bind(listMovieFavorite[position])
    }

    inner class MovieFavoriteViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
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