package com.example.moviecatalogue2.activites

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviecatalogue2.R

import com.example.moviecatalogue2.adapters.TvshowSearchAdapter
import com.example.moviecatalogue2.entities.TvShow
import com.example.moviecatalogue2.models.TvshowSearchViewModel
import kotlinx.android.synthetic.main.activity_tvshow_search.*
import kotlinx.android.synthetic.main.activity_tvshow_search.progressBar

class TvshowSearchActivity : AppCompatActivity() {
    private val list = ArrayList<TvShow>()
    private lateinit var adapter: TvshowSearchAdapter
    private lateinit var tvshowSearchViewModel: TvshowSearchViewModel
    private lateinit var searchView : SearchView
    private lateinit var searchManager : SearchManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tvshow_search)

        adapter = TvshowSearchAdapter(list)
        adapter.notifyDataSetChanged()

        adapter.setOnItemClickCallback(object :TvshowSearchAdapter.OnItemClickCallback{
            override fun onItemClicked(data: TvShow) {
                showSelectedTvshow(data)
            }
        })

        searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager

        rv_tvshows.layoutManager = LinearLayoutManager(this)
        rv_tvshows.adapter = adapter

        tvshowSearchViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(TvshowSearchViewModel::class.java)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)

        searchView = menu.findItem(R.id.app_bar_search).actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint =getString(R.string.search_tvshow)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                tvshowSearchViewModel.setTvshow(query)
                tvshowSearchViewModel.getTvshow().observe(this@TvshowSearchActivity, Observer { tvshowItems ->
                    if(tvshowItems!=null){
                        adapter.setData(tvshowItems)
                        showLoading(false)
                    }
                })
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    private fun showSelectedTvshow(tvshow:TvShow){
        val tvshowDetailIntent = Intent(this, TvshowDetailActivity::class.java)
        tvshowDetailIntent.putExtra(TvshowDetailActivity.EXTRA_TVSHOW, tvshow)
        startActivity(tvshowDetailIntent)
    }

    private fun showLoading(state: Boolean){
        if(state){
            progressBar.visibility = View.VISIBLE
        }else{
            progressBar.visibility = View.GONE
        }
    }
}
