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
import com.example.moviecatalogue2.adapters.MovieSearchAdapter
import com.example.moviecatalogue2.entities.Movie
import com.example.moviecatalogue2.models.MovieSearchViewModel
import kotlinx.android.synthetic.main.activity_movie_search.*
import kotlinx.android.synthetic.main.activity_movie_search.progressBar
import kotlinx.android.synthetic.main.activity_movie_search.rv_movies


class MovieSearchActivity : AppCompatActivity() {
    private val list = ArrayList<Movie>()
    private lateinit var adapter: MovieSearchAdapter
    private lateinit var movieSearchViewModel: MovieSearchViewModel
    private lateinit var searchManager: SearchManager
    private lateinit var searchView:SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_search)

        adapter = MovieSearchAdapter(list)
        adapter.notifyDataSetChanged()

        adapter.setOnItemClickCallback(object : MovieSearchAdapter.OnItemClickCallback{
            override fun onItemClicked(data: Movie) {
                showSelectedMovie(data)
            }
        })

        searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager

        rv_movies.layoutManager = LinearLayoutManager(this)
        rv_movies.adapter = adapter

        movieSearchViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MovieSearchViewModel::class.java)

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)

        searchView = menu.findItem(R.id.app_bar_search).actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_movie_hint)
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                movieSearchViewModel.setMovie(query)
                movieSearchViewModel.getMovie().observe(this@MovieSearchActivity, Observer { movieItems ->
                    if (movieItems!=null){
                        showLoading(true)
                        adapter.setData(movieItems)
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

    private fun showSelectedMovie(movie: Movie) {
        val movieDetailIntent = Intent(this, MovieDetailActivity::class.java)
        movieDetailIntent.putExtra(MovieDetailActivity.EXTRA_MOVIE, movie)
        startActivity(movieDetailIntent)
    }

    private fun showLoading(state: Boolean){
        if(state){
            progressBar.visibility = View.VISIBLE
        }else{
            progressBar.visibility = View.GONE
        }
    }
}
