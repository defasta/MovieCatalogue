package com.example.moviecatalogue2.activites

import android.content.ContentValues
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.moviecatalogue2.R
import com.example.moviecatalogue2.db.DatabaseContract
import com.example.moviecatalogue2.db.DatabaseContract.MovieColumns.Companion.CONTENT_URI
import com.example.moviecatalogue2.db.FavoriteHelper
import com.example.moviecatalogue2.entities.Movie
import com.example.moviecatalogue2.helper.MappingHelper
import com.example.moviecatalogue2.provider.MovieProvider
import kotlinx.android.synthetic.main.activity_movie_detail.*
import kotlinx.android.synthetic.main.activity_movie_detail.img_photo
import kotlinx.android.synthetic.main.activity_movie_detail.txt_description
import kotlinx.android.synthetic.main.activity_movie_detail.txt_name
import java.util.*
import java.util.Objects.requireNonNull


class MovieDetailActivity : AppCompatActivity() , View.OnClickListener{

    private var position: Int = 0
    private lateinit var favoriteHelper: FavoriteHelper
    private lateinit var uriWithId: Uri
    private lateinit var movie: Movie

    companion object {
        const val EXTRA_MOVIE = "extra_movie"
        const val EXTRA_POSITION_MOVIE  = "extra_position_movie"
        const val RESULT_ADD_MOVIE = 102
        const val RESULT_DELETE_MOVIE = 302
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        movie = intent.getParcelableExtra(EXTRA_MOVIE) as Movie

        uriWithId = Uri.parse(CONTENT_URI.toString()+"/"+movie.id )



        if(checkMovie()){
            btn_movie.text = getString(R.string.delete)
            /*if (cursor!=null){
                movie = MappingHelper.mapMovieCursorToObject(cursor!!)
            }*/
        }else{
            btn_movie.text = getString(R.string.add)
        }


        val url = "https://image.tmdb.org/t/p/w342/${movie.photo}"
        Glide.with(this)
            .load(url)
            .into(img_photo)
        txt_name.text = movie.name
        txt_description.text = movie.description
        txt_score.text = movie.score
        txt_year.text = movie.year

        /*if (favoriteHelper.checkMovie(movie.id))/*(checkMovie())*/{
            btn_movie.text = getString(R.string.delete)
        }else{
            btn_movie.text = getString(R.string.add)
        }*/

        /*if (checkMovie(cursor))/*(checkMovie())*/{
            btn_movie.text = getString(R.string.delete)
        }else{
            btn_movie.text = getString(R.string.add)
        }*/

        btn_movie.setOnClickListener(this)
    }

    private  fun checkMovie(): Boolean{

            var hasInserted = false
            val cursor: Cursor = contentResolver.query(CONTENT_URI, null, null, null, null)!!
            if(cursor!=null){
                if (cursor.moveToFirst()){
                    val id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns._ID))
                    val idFromUri = Integer.valueOf(Objects.requireNonNull(uriWithId.lastPathSegment).toString())
                    if(id == idFromUri){
                        hasInserted = true
                    }

                }
            }
            return hasInserted
    }

    override fun onClick(v: View) {
        if (v.id == R.id.btn_movie) {
            val movie = intent.getParcelableExtra(EXTRA_MOVIE) as Movie
            val id = movie.id
            val photo = movie.photo
            val name = movie.name
            val description = movie.description
            val score = movie.score
            val year = movie.year

            val intent = Intent()
            intent.putExtra(EXTRA_MOVIE, movie)
            intent.putExtra(EXTRA_POSITION_MOVIE, position)

            val values = ContentValues()
            values.put(DatabaseContract.MovieColumns._ID, id)
            values.put(DatabaseContract.MovieColumns.PHOTO, photo)
            values.put(DatabaseContract.MovieColumns.NAME, name)
            values.put(DatabaseContract.MovieColumns.DESCRIPTION, description)
            values.put(DatabaseContract.MovieColumns.SCORE, score)
            values.put(DatabaseContract.MovieColumns.YEAR, year)

            if (checkMovie()) {
                //val result = favoriteHelper.deleteMovieById(movie.id.toString()).toLong()
                val result = contentResolver.delete(uriWithId, null, null).toLong()
                if (result > 0) {
                    Toast.makeText(
                        this@MovieDetailActivity,
                        getString(R.string.success_remove),
                        Toast.LENGTH_SHORT
                    ).show()
                    intent.putExtra(EXTRA_POSITION_MOVIE, position)
                    setResult(RESULT_DELETE_MOVIE, intent)
                    finish()
                } else {
                    Toast.makeText(
                        this@MovieDetailActivity,
                        getString(R.string.failed_remove),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                //val result = favoriteHelper.insertMovie(values)
                 contentResolver.insert(CONTENT_URI,  values)
                //if (result > 0) {
                    Toast.makeText(
                        this@MovieDetailActivity,
                        getString(R.string.success_add),
                        Toast.LENGTH_SHORT
                    ).show()
                    setResult(RESULT_ADD_MOVIE, intent)
                    finish()
                /*} else {
                    Toast.makeText(
                        this@MovieDetailActivity,
                        getString(R.string.failed_add),
                        Toast.LENGTH_SHORT
                    ).show()
                }*/

            }

        }

    }
}
