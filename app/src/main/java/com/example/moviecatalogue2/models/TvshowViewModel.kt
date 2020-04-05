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

class TvshowViewModel : ViewModel() {
    companion object {
        private const val API_KEY = "4e3d3fa6d498e3fafe801b5bcb54951e"
    }

    val listTvshow = MutableLiveData<ArrayList<TvShow>>()
    internal fun setTvshow() {
        val client = AsyncHttpClient()
        val listItems = ArrayList<TvShow>()
        val url = "https://api.themoviedb.org/3/discover/tv?api_key=$API_KEY&language=en-US"

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
                        val tvList = list.getJSONObject(i)
                        val tvShow = TvShow()
                        tvShow.id = tvList.getInt("id")
                        tvShow.photo = tvList.getString("poster_path")
                        tvShow.name = tvList.getString("name")
                        tvShow.score = tvList.getString("vote_average")
                        tvShow.year = tvList.getString("first_air_date")
                        tvShow.description = tvList.getString("overview")
                        listItems.add(tvShow)
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

    internal fun getTvshow(): LiveData<ArrayList<TvShow>> {
        return listTvshow
    }
}