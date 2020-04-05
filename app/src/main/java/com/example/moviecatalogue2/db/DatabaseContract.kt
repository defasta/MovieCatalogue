package com.example.moviecatalogue2.db

import android.net.Uri
import android.provider.BaseColumns

object DatabaseContract {

    const val AUTHORITY = "com.example.moviecatalogue2"
    const val SCHEME = "content"

    internal class MovieColumns: BaseColumns{
        companion object{
            const val TABLE_MOVIE = "movie"
            const val _ID = "_id"
            const val PHOTO = "photo"
            const val NAME = "name"
            const val DESCRIPTION = "description"
            const val SCORE = "score"
            const val YEAR = "year"

            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_MOVIE)
                .build()
        }
    }
    internal class TvshowColumns: BaseColumns{
        companion object{
            const val TABLE_TVSHOW ="tvshow"
            const val _ID = "_id"
            const val PHOTO = "photo"
            const val NAME = "name"
            const val DESCRIPTION = "description"
            const val SCORE = "score"
            const val YEAR = "year"
        }
    }
}