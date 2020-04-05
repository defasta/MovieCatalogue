package com.example.moviecatalogue2.activites

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.moviecatalogue2.R
import com.example.moviecatalogue2.db.DatabaseContract.MovieColumns.Companion.CONTENT_URI
import com.example.moviecatalogue2.db.FavoriteHelper
import com.example.moviecatalogue2.entities.Movie
import com.example.moviecatalogue2.helper.MappingHelper
import kotlinx.android.synthetic.main.activity_movie_favorite_detail.btn_submit
import kotlinx.android.synthetic.main.activity_movie_favorite_detail.img_photo
import kotlinx.android.synthetic.main.activity_movie_favorite_detail.txt_description
import kotlinx.android.synthetic.main.activity_movie_favorite_detail.txt_name
import kotlinx.android.synthetic.main.activity_movie_favorite_detail.txt_score
import kotlinx.android.synthetic.main.activity_movie_favorite_detail.txt_year

class MovieFavoriteDetailActivity : AppCompatActivity(), View.OnClickListener {

    private var position: Int = 0
    private lateinit var movie: Movie
    private lateinit var favoriteHelper: FavoriteHelper
    private lateinit var uriId :Uri

    companion object {
        const val EXTRA_MOVIE = "extra_movie"
        const val EXTRA_POSITION_MOVIE = "extra_position_movie"
        const val REQUEST_DELETE_MOVIE = 302
        const val RESULT_DELETE_MOVIE = 304
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_favorite_detail)

        //favoriteHelper = FavoriteHelper.getInstance(applicationContext)
        //favoriteHelper.open()

        uriId = Uri.parse(CONTENT_URI.toString()+"/"+movie.id)

        val cursor = contentResolver.query(uriId, null, null, null, null)
        if(cursor!=null){
            movie = MappingHelper.mapMovieCursorToObject(cursor)
            cursor.close()
        }

        //var movie = intent.getParcelableExtra(EXTRA_MOVIE) as Movie

        val url = "https://image.tmdb.org/t/p/w342/${movie.photo}"
        Glide.with(this)
            .load(url)
            .into(img_photo)
        txt_name.text = movie.name
        txt_description.text = movie.description
        txt_score.text = movie.score
        txt_year.text = movie.year
        btn_submit.text = getString(R.string.delete)
        btn_submit.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        if(v.id == R.id.btn_submit){
            //var movie = intent.getParcelableExtra(EXTRA_MOVIE) as Movie

            //favoriteHelper.deleteMovieById(movie.id.toString()).toLong()

            contentResolver.delete(uriId, null,null)

            intent.putExtra(EXTRA_POSITION_MOVIE, position)
            setResult(RESULT_DELETE_MOVIE, intent)

            Toast.makeText(this@MovieFavoriteDetailActivity, R.string.success_remove, Toast.LENGTH_SHORT).show()
            finish()
        }
    }

}
