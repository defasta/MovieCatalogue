package com.example.moviecatalogue2.fragments


import android.content.ContentResolver
import android.content.Intent
import android.database.ContentObserver
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviecatalogue2.R
import com.example.moviecatalogue2.activites.MovieDetailActivity
import com.example.moviecatalogue2.activites.MovieFavoriteDetailActivity
import com.example.moviecatalogue2.adapters.MovieFavoriteAdapter
import com.example.moviecatalogue2.db.DatabaseContract.MovieColumns.Companion.CONTENT_URI
import com.example.moviecatalogue2.db.FavoriteHelper
import com.example.moviecatalogue2.helper.MappingHelper
import com.example.moviecatalogue2.entities.Movie
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_movie_favorite.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 */
class MovieFavoriteFragment : Fragment() {
    private lateinit var adapter: MovieFavoriteAdapter
    //private lateinit var favoriteHelper: FavoriteHelper
    private var savedInstanceState :Bundle? = null
    //private var movie : Movie? = null
    //private lateinit var uriWithId :Uri

    companion object{
        private const val EXTRA_STATE = "EXTRA_STATE"
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //favoriteHelper = FavoriteHelper.getInstance(activity!!.applicationContext)
        //favoriteHelper.open()
        //onResume()
        //movie = activity?.intent?.getParcelableExtra(MovieDetailActivity.EXTRA_MOVIE) as? Movie

       // uriWithId = Uri.parse(CONTENT_URI.toString()+"/"+ movie?.id)

        showRecyclerList()
    }

    /*override fun onResume() {
        super.onResume()

        //movie = activity?.intent?.getParcelableExtra(MovieDetailActivity.EXTRA_MOVIE) as? Movie

        //uriWithId = Uri.parse(CONTENT_URI.toString()+"/"+ movie?.id )



    }*/

    private fun showRecyclerList(){
        rv_favorite_movies.layoutManager = LinearLayoutManager(context)
        rv_favorite_movies.setHasFixedSize(true)
        adapter = MovieFavoriteAdapter()
        adapter.notifyDataSetChanged()
        rv_favorite_movies.adapter = adapter
        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)

        val myObserver = object : ContentObserver(handler){
            override fun onChange(selfChange: Boolean) {
                loadMoviesAsync()
            }
        }
        activity?.contentResolver?.registerContentObserver(CONTENT_URI, true, myObserver)
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_STATE, adapter.listMovieFavorite)
        this.savedInstanceState = outState
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (data != null) {
            when (requestCode) {
                MovieFavoriteDetailActivity.REQUEST_DELETE_MOVIE -> if (resultCode == MovieFavoriteDetailActivity.RESULT_DELETE_MOVIE) {
                    val position = data.getIntExtra(MovieFavoriteDetailActivity.EXTRA_POSITION_MOVIE, 0)
                    adapter.removeItem(position)
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }


    private fun showSelectedMovie(movie: Movie) {
        val movieFavoriteDetailIntent = Intent(context, MovieFavoriteDetailActivity::class.java)
        movieFavoriteDetailIntent.putExtra(MovieDetailActivity.EXTRA_MOVIE, movie)
        startActivity(movieFavoriteDetailIntent)
    }

    private fun showSnackbarMessage(message: String) {
        Snackbar.make(rv_favorite_movies, message, Snackbar.LENGTH_SHORT).show()
    }

    private fun loadMoviesAsync() {
        GlobalScope.launch(Dispatchers.Main) {
            //progressBar.visibility = View.VISIBLE
            val defferedMovies = async(Dispatchers.IO) {
                //val cursor = favoriteHelper.queryAllMovie
                val cursor = activity?.contentResolver?.query(CONTENT_URI, null, null, null, null)
                MappingHelper.mapMovieCursorToArrayList(cursor)
            }
            //
            // progressBar.visibility = View.INVISIBLE
            val movie = defferedMovies.await()

            if (movie.size > 0) {
                adapter.listMovieFavorite = movie

                adapter.setOnItemClickCallback(object : MovieFavoriteAdapter.OnItemClickCallback {
                    override fun onItemClicked(data: Movie) {
                        showSelectedMovie(data)
                    }
                })
            } else {
                adapter.listMovieFavorite = ArrayList()
            }
        }
    }
}