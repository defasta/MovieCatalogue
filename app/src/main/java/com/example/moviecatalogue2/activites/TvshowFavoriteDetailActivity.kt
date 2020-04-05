package com.example.moviecatalogue2.activites

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.moviecatalogue2.R
import com.example.moviecatalogue2.db.FavoriteHelper
import com.example.moviecatalogue2.entities.TvShow
import kotlinx.android.synthetic.main.activity_tvshow_favorite_detail.*

class TvshowFavoriteDetailActivity : AppCompatActivity(), View.OnClickListener {

    private var position: Int = 0
    private lateinit var favoriteHelper: FavoriteHelper

    companion object {
        const val EXTRA_TVSHOW = "extra_tvshow"
        const val EXTRA_POSITION_TVSHOW = "extra_position_tvshow"
        const val REQUEST_DELETE_TVSHOW = 300
        const val RESULT_DELETE_TVSHOW = 301
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tvshow_favorite_detail)

        favoriteHelper = FavoriteHelper.getInstance(applicationContext)
        favoriteHelper.open()

        var tvshow = intent.getParcelableExtra(EXTRA_TVSHOW) as TvShow

        val url = "https://image.tmdb.org/t/p/w342/${tvshow.photo}"
        Glide.with(this)
            .load(url)
            .into(img_photo)
        txt_name.text = tvshow.name
        txt_description.text = tvshow.description
        txt_score.text = tvshow.score
        txt_year.text = tvshow.year
        btn_submit.text = getString(R.string.delete)
        btn_submit.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        if(v.id == R.id.btn_submit){
            var tvshow = intent.getParcelableExtra(EXTRA_TVSHOW) as TvShow

            favoriteHelper.deleteTvshowById(tvshow.id.toString()).toLong()

            intent.putExtra(EXTRA_POSITION_TVSHOW, position)
            setResult(RESULT_DELETE_TVSHOW, intent)

            Toast.makeText(this@TvshowFavoriteDetailActivity, getString(R.string.success_remove), Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
