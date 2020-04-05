package com.example.moviecatalogue2.models
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviecatalogue2.entities.Movie
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class MovieSearchViewModel : ViewModel(){
    companion object {
        private const val API_KEY = com.example.moviecatalogue2.BuildConfig.API_KEY
    }

    val listMovie = MutableLiveData<ArrayList<Movie>>()

    fun setMovie(title: String?){
        val listItems = ArrayList<Movie>()
        val url = "https://api.themoviedb.org/3/search/movie?api_key=$API_KEY&language=en-US&query=$title"

        val client = AsyncHttpClient()
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray
            ) {
                try {
                    val result = String(responseBody)
                    val responseObject = JSONObject(result)
                    val list = responseObject.getJSONArray("results")

                    for (i in 0 until list.length()) {
                        val movieList = list.getJSONObject(i)
                        var movie = Movie()
                        movie.id = movieList.getInt("id")
                        movie.photo = movieList.getString("poster_path")
                        movie.name = movieList.getString("title")
                        movie.score = movieList.getString("vote_average")
                        movie.year = movieList.getString("release_date")
                        movie.description = movieList.getString("overview")
                        listItems.add(movie)
                    }
                    listMovie.postValue(listItems)
                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray,
                error: Throwable
            ) {
                Log.d("onFailure", error.message.toString())
            }
        })
    }

    internal fun getMovie() : LiveData<ArrayList<Movie>>{
        return listMovie
    }
}