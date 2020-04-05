package com.example.moviecatalogue2.fragments


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.moviecatalogue2.R
import com.example.moviecatalogue2.activites.TvshowDetailActivity
import com.example.moviecatalogue2.activites.TvshowFavoriteDetailActivity
import com.example.moviecatalogue2.adapters.TvshowFavoriteAdapter
import com.example.moviecatalogue2.db.FavoriteHelper
import com.example.moviecatalogue2.helper.MappingHelper
import com.example.moviecatalogue2.entities.TvShow
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_movie_favorite.progressBar
import kotlinx.android.synthetic.main.fragment_tvshow_favorite.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 */
class TvshowFavoriteFragment : Fragment() {
    private lateinit var adapter: TvshowFavoriteAdapter
    private lateinit var favoriteHelper: FavoriteHelper
    private var savedInstanceState :Bundle? = null

    companion object{
        private const val EXTRA_STATE = "EXTRA_STATE"
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tvshow_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        favoriteHelper = FavoriteHelper.getInstance(activity!!.applicationContext)
        favoriteHelper.open()
        onResume()

    }

    override fun onResume() {
        super.onResume()
        rv_favorite_tvshows.layoutManager = LinearLayoutManager(context)
        rv_favorite_tvshows.setHasFixedSize(true)
        adapter = TvshowFavoriteAdapter()
        rv_favorite_tvshows.adapter = adapter

        loadTvshowAsync()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_STATE, adapter.listTvshowFavorite)
        this.savedInstanceState = outState
    }

    private fun showSelectedTvshow(tvshow: TvShow) {
        val tvshowFavoriteDetailIntent = Intent(context, TvshowFavoriteDetailActivity::class.java)
        tvshowFavoriteDetailIntent.putExtra(TvshowDetailActivity.EXTRA_TVSHOW, tvshow)
        startActivity(tvshowFavoriteDetailIntent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (data != null) {
            when (requestCode) {
                TvshowFavoriteDetailActivity.REQUEST_DELETE_TVSHOW -> if (resultCode == TvshowFavoriteDetailActivity.RESULT_DELETE_TVSHOW) {
                    val position = data.getIntExtra(TvshowFavoriteDetailActivity.EXTRA_POSITION_TVSHOW, 0)
                    adapter.removeItem(position)
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun showSnackbarMessage(message: String) {
        Snackbar.make(rv_favorite_tvshows, message, Snackbar.LENGTH_SHORT).show()
    }

    private fun loadTvshowAsync() {
        GlobalScope.launch(Dispatchers.Main) {
            progressBar.visibility = View.VISIBLE
            val defferedTvshow = async(Dispatchers.IO) {
                val cursor = favoriteHelper.queryAllTvshow()
                MappingHelper.mapTvshowCursorToArrayList(cursor)
            }
            progressBar.visibility = View.INVISIBLE
            val tvshow = defferedTvshow.await()
            /*if (savedInstanceState == null){
                list.clear()
                list.addAll(movie)
                if(list != null){
                    adapter.listMovieFavorite = list
                }else{
                    showSnackbarMessage("Tidak ada data")
                }
            }else{
                val list = savedInstanceState?.getParcelableArrayList<Movie>(EXTRA_STATE)
                if (list!=null){
                    adapter.listMovieFavorite = list
                }
            }*/

            if (tvshow.size > 0) {
                //if (savedInstanceState == null){
                adapter.listTvshowFavorite = tvshow
                //}else{
                //  val list = savedInstanceState?.getParcelableArrayList<Movie>(EXTRA_STATE)
                //if (list!=null){
                //  adapter.listMovieFavorite = list
                //}
                //}
                adapter.setOnItemClickCallback(object : TvshowFavoriteAdapter.OnItemClickCallback {
                    override fun onItemClicked(tvshow: TvShow) {
                        showSelectedTvshow(tvshow)
                    }
                })
            } else {
                adapter.listTvshowFavorite = ArrayList()
                //showSnackbarMessage(getString(R.string.no_favorite))
            }
        }
    }

}
