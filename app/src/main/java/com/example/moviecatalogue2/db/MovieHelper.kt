package com.example.moviecatalogue2.db

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.moviecatalogue2.db.DatabaseContract.MovieColumns.Companion._ID
import com.example.moviecatalogue2.entities.Movie
import java.sql.SQLException

class MovieHelper(context: Context){
    companion object{
        private const val DATABASE_TABLE1 = DatabaseContract.MovieColumns.TABLE_MOVIE
        private lateinit var databaseHelper: DatabaseHelper
        private lateinit var database : SQLiteDatabase
        private var INSTANCE: MovieHelper? = null

        fun getInstance(context: Context): MovieHelper{
            if(INSTANCE == null){
                synchronized(SQLiteOpenHelper::class.java){
                    if(INSTANCE == null){
                        INSTANCE = MovieHelper(context)
                    }
                }
            }
            return INSTANCE as MovieHelper
        }

    }


    init {
        databaseHelper = DatabaseHelper(context)
    }

    @Throws(SQLException::class)
    fun open(){
        database = databaseHelper.writableDatabase
    }

    fun close(){
        databaseHelper.close()

        if(database.isOpen)
            database.close()
    }


}