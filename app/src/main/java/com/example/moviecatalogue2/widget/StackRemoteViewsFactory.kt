package com.example.moviecatalogue2.widget

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Binder
import android.util.Log
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import androidx.core.os.bundleOf
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.moviecatalogue2.R
import com.example.moviecatalogue2.db.DatabaseContract.MovieColumns.Companion.CONTENT_URI
import com.example.moviecatalogue2.db.FavoriteHelper
import com.example.moviecatalogue2.db.MovieHelper
import com.example.moviecatalogue2.entities.Movie
import com.example.moviecatalogue2.helper.MappingHelper
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

internal class StackRemoteViewsFactory(private val mContext : Context): RemoteViewsService.RemoteViewsFactory{

    private val mWidgetItems = ArrayList<Bitmap>()
    private var cursor : Cursor? = null
    private lateinit var uriId: Uri
    private var listMovies = ArrayList<Movie>()
    private lateinit var movie : Movie
    override fun onCreate() {

    }

    @SuppressLint("Recycle")
    override fun onDataSetChanged() {
        //val values = ContentValues()
        val identityToken = Binder.clearCallingIdentity()

        //uriId = mContext.contentResolver.insert(CONTENT_URI, values)!!

        cursor = mContext.contentResolver.query(CONTENT_URI, null, null, null, null)!!

        listMovies = MappingHelper.mapMovieCursorToArrayList(cursor)

        for (movie : Movie in listMovies){
            try{
                val bitmap = Glide.with(mContext)
                    .asBitmap()
                    .load("https://image.tmdb.org/t/p/w185/${movie.photo}")
                    .submit()
                    .get()
                mWidgetItems.add(bitmap)
            }catch (e: Exception){
                Log.d("Exception", e.message.toString())
            }
        }

        Binder.restoreCallingIdentity(identityToken)

    }

    override fun getLoadingView(): RemoteViews? = null

    override fun getItemId(position: Int): Long = 0


    override fun getViewAt(position: Int): RemoteViews {
        val rv = RemoteViews(mContext.packageName, R.layout.widget_item)

        rv.setImageViewBitmap(R.id.imageView, mWidgetItems.get(position))
        val extras = bundleOf(
            MovieFavoriteWidget.EXTRA_MOVIE to position
        )
        val fillIntent = Intent()
        fillIntent.putExtras(extras)

        rv.setOnClickFillInIntent(R.id.imageView, fillIntent)
        return rv
    }

    override fun getCount(): Int = mWidgetItems.size

    override fun getViewTypeCount(): Int = 1

    override fun onDestroy() {
        cursor?.close()
    }

    override fun hasStableIds(): Boolean = false

}