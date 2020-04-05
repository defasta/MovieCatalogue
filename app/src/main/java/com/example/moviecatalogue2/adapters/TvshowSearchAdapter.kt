package com.example.moviecatalogue2.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviecatalogue2.R
import com.example.moviecatalogue2.entities.TvShow
import kotlinx.android.synthetic.main.item_tvshow_search.view.*

class TvshowSearchAdapter(private val listTvshow: ArrayList<TvShow>) :
    RecyclerView.Adapter<TvshowSearchAdapter.TvshowSearchViewHolder>() {
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setData(items: ArrayList<TvShow>) {
        listTvshow.clear()
        listTvshow.addAll(items)
        notifyDataSetChanged()
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): TvshowSearchViewHolder {
        val view =
            LayoutInflater.from(viewGroup.context).inflate(R.layout.item_tvshow_search, viewGroup, false)
        return TvshowSearchViewHolder(view)
    }

    override fun getItemCount(): Int = listTvshow.size

    override fun onBindViewHolder(holder: TvshowSearchViewHolder, position: Int) {
        holder.bind(listTvshow[position])
    }

    inner class TvshowSearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(tvShow: TvShow) {
            val url = "https://image.tmdb.org/t/p/w185/${tvShow.photo}"
            with(itemView) {
                Glide.with(itemView.context)
                    .load(url)
                    .into(img_photo)
                txt_name.text = tvShow.name
                txt_description.text = tvShow.description

                itemView.setOnClickListener { onItemClickCallback?.onItemClicked(tvShow) }
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: TvShow)
    }
}