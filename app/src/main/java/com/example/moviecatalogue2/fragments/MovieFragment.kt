package com.example.moviecatalogue2.fragments


import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviecatalogue2.R
import com.example.moviecatalogue2.R.layout.fragment_movie
import com.example.moviecatalogue2.activites.MovieDetailActivity
import com.example.moviecatalogue2.activites.MovieSearchActivity
import com.example.moviecatalogue2.adapters.ListMovieAdapter
import com.example.moviecatalogue2.entities.Movie
import com.example.moviecatalogue2.models.MovieViewModel
import kotlinx.android.synthetic.main.fragment_movie.*
import kotlin.collections.ArrayList

/**
 * A simple [Fragment] subclass.
 */

class MovieFragment : Fragment() {
    private val list = ArrayList<Movie>()
    private lateinit var movieViewModel: MovieViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(fragment_movie, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        rv_movies.setHasFixedSize(true)
        showRecyclerList()

        fab_search.setOnClickListener{
            val intent = Intent(context, MovieSearchActivity::class.java)
            startActivity(intent)
        }

    }

    private fun showRecyclerList() {
        rv_movies.layoutManager = LinearLayoutManager(context)
        val listMovieAdapter = ListMovieAdapter(list)
        rv_movies.adapter = listMovieAdapter

        showLoading(true)

        listMovieAdapter.setOnItemClickCallback(object : ListMovieAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Movie) {
                showSelectedMovie(data)
            }
        })

        movieViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MovieViewModel::class.java)

        movieViewModel.setMovie()

        movieViewModel.getMovie().observe(this, Observer { movie ->
            if (movie != null) {
                listMovieAdapter.setData(movie)
                showLoading(false)
            }
        })
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }

    private fun showSelectedMovie(movie: Movie) {
        val movieDetailIntent = Intent(context, MovieDetailActivity::class.java)
        movieDetailIntent.putExtra(MovieDetailActivity.EXTRA_MOVIE, movie)
        startActivity(movieDetailIntent)
    }

    /*override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)

        //val searchManager = getSystemService(SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.app_bar_search).actionView as SearchView

        //searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_movie_hint)
        searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(title: String?): Boolean {
                val mMovieSearchFragment = MovieSearchFragment()

                val mBundle = Bundle()
                mBundle.putString(MovieSearchFragment.EXTRA_TITLE, title)

                mMovieSearchFragment.arguments = mBundle

                val mFragmentManager = fragmentManager
                mFragmentManager?.beginTransaction()?.apply {
                    replace(R.id.nav_host_fragment, mMovieSearchFragment, MovieSearchFragment::class.java.simpleName )
                    addToBackStack(null)
                    commit()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        searchView.onFinishTemporaryDetach()

    }*/

    /*override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.app_bar_search) {

        }
        return super.onOptionsItemSelected(item)
    }*/
}
