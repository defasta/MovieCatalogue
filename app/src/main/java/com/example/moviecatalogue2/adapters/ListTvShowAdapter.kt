package com.example.moviecatalogue2.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviecatalogue2.R
import com.example.moviecatalogue2.entities.TvShow
import kotlinx.android.synthetic.main.item_tvshow.view.*

class ListTvShowAdapter(private val listTvShow: ArrayList<TvShow>) :
    RecyclerView.Adapter<ListTvShowAdapter.ListViewHolder>() {
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setData(items: ArrayList<TvShow>) {
        listTvShow.clear()
        listTvShow.addAll(items)
        notifyDataSetChanged()
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val view =
            LayoutInflater.from(viewGroup.context).inflate(R.layout.item_tvshow, viewGroup, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int = listTvShow.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listTvShow[position])
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
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