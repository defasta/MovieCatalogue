package com.example.moviecatalogue2.models
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviecatalogue2.entities.TvShow
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class TvshowSearchViewModel: ViewModel(){
    companion object{
        private const val API_KEY = com.example.moviecatalogue2.BuildConfig.API_KEY
    }

    val listTvshow = MutableLiveData<ArrayList<TvShow>>()

    fun setTvshow(title: String?){
        val listItems = ArrayList<TvShow>()
        val url = "https://api.themoviedb.org/3/search/tv?api_key=$API_KEY&language=en-US&query=$title"

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
                        val tvshowList = list.getJSONObject(i)
                        val tvshow = TvShow()
                        tvshow.id = tvshowList.getInt("id")
                        tvshow.photo = tvshowList.getString("poster_path")
                        tvshow.name = tvshowList.getString("name")
                        tvshow.score = tvshowList.getString("vote_average")
                        tvshow.year = tvshowList.getString("first_air_date")
                        tvshow.description = tvshowList.getString("overview")
                        listItems.add(tvshow)
                    }
                    listTvshow.postValue(listItems)
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

    internal fun getTvshow() : LiveData<ArrayList<TvShow>> {
        return listTvshow
    }
}