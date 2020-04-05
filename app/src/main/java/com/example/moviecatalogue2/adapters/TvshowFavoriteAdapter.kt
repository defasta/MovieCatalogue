package com.example.moviecatalogue2.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviecatalogue2.R
import com.example.moviecatalogue2.entities.TvShow
import kotlinx.android.synthetic.main.item_tvshow_favorite.view.img_photo
import kotlinx.android.synthetic.main.item_tvshow_favorite.view.txt_description
import kotlinx.android.synthetic.main.item_tvshow_favorite.view.txt_name

class TvshowFavoriteAdapter: RecyclerView.Adapter<TvshowFavoriteAdapter.TvshowFavoriteViewHolder>() {
    private var onItemClickCallback: OnItemClickCallback? = null
    var listTvshowFavorite = ArrayList<TvShow>()
        set(listTvshowFavorite){
//        if (listMovieFavorite.size>0){
            this.listTvshowFavorite.clear()
            //      }
            this.listTvshowFavorite.addAll(listTvshowFavorite)
            notifyDataSetChanged()
        }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun addItem(tvshow: TvShow){
        this.listTvshowFavorite.add(tvshow)
        notifyItemInserted(this.listTvshowFavorite.size -1)
    }

    fun updateItem(position: Int, tvshow: TvShow){
        this.listTvshowFavorite[position] = tvshow
        notifyItemChanged(position, tvshow)
    }

    fun removeItem(position: Int){
        this.listTvshowFavorite.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, this.listTvshowFavorite.size)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TvshowFavoriteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_tvshow_favorite, parent, false)
        return TvshowFavoriteViewHolder(view)
    }

    override fun getItemCount(): Int = this.listTvshowFavorite.size

    override fun onBindViewHolder(
        holder: TvshowFavoriteViewHolder,
        position: Int
    ) {
        holder.bind(listTvshowFavorite[position])
    }

    inner class TvshowFavoriteViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(tvshow: TvShow) {
            val url = "https://image.tmdb.org/t/p/w185/${tvshow.photo}"
            with(itemView) {
                Glide.with(itemView.context)
                    .load(url)
                    .into(img_photo)
                txt_name.text = tvshow.name
                txt_description.text = tvshow.description
                itemView.setOnClickListener {
                    onItemClickCallback?.onItemClicked(tvshow)
                }
            }
        }
    }
    interface OnItemClickCallback {
        fun onItemClicked(tvshow: TvShow)
    }
}