package com.example.moviecatalogue2.helper

import android.database.Cursor
import android.provider.Settings.Global.getInt
import android.provider.Settings.Global.getString
import com.example.moviecatalogue2.db.DatabaseContract
import com.example.moviecatalogue2.entities.Movie
import com.example.moviecatalogue2.entities.TvShow

object MappingHelper{

    fun mapMovieCursorToArrayList(movieCursor: Cursor?): ArrayList<Movie>{
        val movieList = ArrayList<Movie>()
        movieCursor?.apply {
            movieCursor.moveToFirst()
            if (movieCursor.count>0){
                do{
                    val id = movieCursor.getInt(movieCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns._ID))
                    val photo = movieCursor.getString(movieCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.PHOTO))
                    val name = movieCursor.getString(movieCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.NAME))
                    val description = movieCursor.getString(movieCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.DESCRIPTION))
                    val score = movieCursor.getString(movieCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.SCORE))
                    val year = movieCursor.getString(movieCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.YEAR))
                    movieList.add(
                        Movie(
                            id,
                            photo,
                            name,
                            description,
                            score,
                            year
                        )
                    )
                    movieCursor.moveToNext()
                }while (!movieCursor.isAfterLast)
            }
        }
        return movieList
    }

    fun mapTvshowCursorToArrayList(tvshowCursor: Cursor): ArrayList<TvShow>{
        val tvshowList = ArrayList<TvShow>()

        tvshowCursor.moveToFirst()
        if (tvshowCursor.count>0){
            do{
                val id = tvshowCursor.getInt(tvshowCursor.getColumnIndexOrThrow(DatabaseContract.TvshowColumns._ID))
                val photo = tvshowCursor.getString(tvshowCursor.getColumnIndexOrThrow(DatabaseContract.TvshowColumns.PHOTO))
                val name = tvshowCursor.getString(tvshowCursor.getColumnIndexOrThrow(DatabaseContract.TvshowColumns.NAME))
                val description = tvshowCursor.getString(tvshowCursor.getColumnIndexOrThrow(DatabaseContract.TvshowColumns.DESCRIPTION))
                val score = tvshowCursor.getString(tvshowCursor.getColumnIndexOrThrow(DatabaseContract.TvshowColumns.SCORE))
                val year = tvshowCursor.getString(tvshowCursor.getColumnIndexOrThrow(DatabaseContract.TvshowColumns.YEAR))
                tvshowList.add(
                    TvShow(
                        id,
                        photo,
                        name,
                        description,
                        score,
                        year
                    )
                )
                tvshowCursor.moveToNext()
            }while (!tvshowCursor.isAfterLast)
        }
        return tvshowList
    }

    fun mapMovieCursorToObject(movieCursor: Cursor):Movie{
        var movie = Movie()
        movieCursor.apply {
            moveToFirst()
            val id = getInt(getColumnIndexOrThrow(DatabaseContract.MovieColumns._ID))
            val photo = getString(getColumnIndexOrThrow(DatabaseContract.MovieColumns.PHOTO))
            val name = getString(getColumnIndexOrThrow(DatabaseContract.MovieColumns.NAME))
            val description = getString(getColumnIndexOrThrow(DatabaseContract.MovieColumns.DESCRIPTION))
            val score = getString(getColumnIndexOrThrow(DatabaseContract.MovieColumns.SCORE))
            val year = getString(getColumnIndexOrThrow(DatabaseContract.MovieColumns.YEAR))
            movie = Movie(id, photo, name, description, score, year)
        }
        return movie
    }

    /*fun mapMovieCursorToObject(movieCursor: Cursor):Movie{
        var movie = Movie()
        movieCursor.moveToFirst()
        if(movieCursor.count>0){
            do{
                val id = movieCursor.getInt(movieCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns._ID))
                val photo = movieCursor.getString(movieCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.PHOTO))
                val name = movieCursor.getString(movieCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.NAME))
                val description = movieCursor.getString(movieCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.DESCRIPTION))
                val score = movieCursor.getString(movieCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.SCORE))
                val year = movieCursor.getString(movieCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.YEAR))
                movie = Movie(id, photo, name, description, score, year)
                movieCursor.moveToFirst()
            }while (!movieCursor.isAfterLast)
        }
        return movie
    }*/
}