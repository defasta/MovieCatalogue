package com.example.moviecatalogue2.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.moviecatalogue2.db.DatabaseContract.MovieColumns.Companion.TABLE_MOVIE
import com.example.moviecatalogue2.db.DatabaseContract.TvshowColumns.Companion.TABLE_TVSHOW

internal class DatabaseHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){

    companion object{

        private const val DATABASE_NAME = "dbmovies"
        private const val DATABASE_VERSION = 5
        private const val SQL_CREATE_TABLE_MOVIE= "CREATE TABLE IF NOT EXISTS $TABLE_MOVIE" +
                "(${DatabaseContract.MovieColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                " ${DatabaseContract.MovieColumns.PHOTO} TEXT NOT NULL," +
                " ${DatabaseContract.MovieColumns.NAME} TEXT NOT NULL," +
                " ${DatabaseContract.MovieColumns.DESCRIPTION} TEXT NOT NULL," +
                " ${DatabaseContract.MovieColumns.SCORE} TEXT NOT NULL," +
                " ${DatabaseContract.MovieColumns.YEAR} TEXT NOT NULL)"
        private const val SQL_CREATE_TABLE_TVSHOW= "CREATE TABLE IF NOT EXISTS $TABLE_TVSHOW" +
                "(${DatabaseContract.TvshowColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                " ${DatabaseContract.TvshowColumns.PHOTO} TEXT NOT NULL," +
                " ${DatabaseContract.TvshowColumns.NAME} TEXT NOT NULL," +
                " ${DatabaseContract.TvshowColumns.DESCRIPTION} TEXT NOT NULL," +
                " ${DatabaseContract.TvshowColumns.SCORE} TEXT NOT NULL," +
                " ${DatabaseContract.TvshowColumns.YEAR} TEXT NOT NULL)"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE_MOVIE)
        db.execSQL(SQL_CREATE_TABLE_TVSHOW)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_MOVIE")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_TVSHOW")
        onCreate(db)
    }
}