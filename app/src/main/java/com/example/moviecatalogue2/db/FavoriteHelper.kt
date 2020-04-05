package com.example.moviecatalogue2.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.moviecatalogue2.db.DatabaseContract.MovieColumns.Companion.DESCRIPTION
import com.example.moviecatalogue2.db.DatabaseContract.MovieColumns.Companion.NAME
import com.example.moviecatalogue2.db.DatabaseContract.MovieColumns.Companion.PHOTO
import com.example.moviecatalogue2.db.DatabaseContract.MovieColumns.Companion.SCORE
import com.example.moviecatalogue2.db.DatabaseContract.MovieColumns.Companion.TABLE_MOVIE
import com.example.moviecatalogue2.db.DatabaseContract.MovieColumns.Companion.YEAR
import com.example.moviecatalogue2.db.DatabaseContract.MovieColumns.Companion._ID
import com.example.moviecatalogue2.db.DatabaseContract.TvshowColumns.Companion.TABLE_TVSHOW
import com.example.moviecatalogue2.entities.Movie
import java.sql.SQLException

class FavoriteHelper(context: Context){

    companion object{
        private const val DATABASE_TABLE1 = TABLE_MOVIE
        private const val DATABASE_TABLE2 = TABLE_TVSHOW
        private lateinit var databaseHelper: DatabaseHelper
        private lateinit var database : SQLiteDatabase
        private var INSTANCE: FavoriteHelper? = null

        fun getInstance(context: Context): FavoriteHelper{
            if(INSTANCE == null){
                synchronized(SQLiteOpenHelper::class.java){
                    if(INSTANCE == null){
                        INSTANCE = FavoriteHelper(context)
                    }
                }
            }
            return INSTANCE as FavoriteHelper
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


    fun queryAllMovie():Cursor{
        return database.query(
            DATABASE_TABLE1,
            null,
            null,
            null,
            null,
            null,
            "$_ID ASC")
    }

    fun queryAllTvshow():Cursor{
        return database.query(
            DATABASE_TABLE2,
            null,
            null,
            null,
            null,
            null,
            "$_ID ASC")
    }

    fun queryMovieById(id: String): Cursor{
        return database.query(
            DATABASE_TABLE1,
            null,
            "$_ID = ?",
            arrayOf(id),
            null,
            null,
            null,
            null
        )
    }

    fun queryTvshowById(id: String): Cursor{
        return database.query(
            DATABASE_TABLE2,
            null,
            "$_ID = ?",
            arrayOf(id),
            null,
            null,
            null,
            null
        )
    }

    fun insertMovie(values: ContentValues?): Long{
        return database.insert(DATABASE_TABLE1, null, values)
    }

    fun insertTvshow(values: ContentValues?): Long{
        return database.insert(DATABASE_TABLE2, null, values)
    }

    fun updateMovie(id: String, values: ContentValues?): Int{
        return database.update(DATABASE_TABLE1, values, "$_ID = ?", arrayOf(id))
    }

    fun updateTvshow(id: String, values: ContentValues?): Int{
        return database.update(DATABASE_TABLE2, values, "$_ID = ?", arrayOf(id))
    }


    fun deleteMovieById(id: String): Int{
        return database.delete(DATABASE_TABLE1, "$_ID = $id", null)
    }

    fun deleteTvshowById(id: String): Int{
        return database.delete(DATABASE_TABLE2, "$_ID = $id", null)
    }

    fun checkMovie(id: Int?): Boolean{
        val query = database.rawQuery("SELECT * FROM $DATABASE_TABLE1 WHERE $_ID = $id", null)
        return query.count > 0
    }

    fun checkTvshow(id: Int?): Boolean{
        val query = database.rawQuery("SELECT * FROM $DATABASE_TABLE2 WHERE $_ID = $id", null)
        return query.count > 0
    }

}