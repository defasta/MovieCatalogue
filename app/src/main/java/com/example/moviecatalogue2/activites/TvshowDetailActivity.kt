package com.example.moviecatalogue2.activites

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.moviecatalogue2.R
import com.example.moviecatalogue2.db.DatabaseContract
import com.example.moviecatalogue2.db.FavoriteHelper
import com.example.moviecatalogue2.entities.TvShow
import kotlinx.android.synthetic.main.activity_tvshow_detail.*
import kotlinx.android.synthetic.main.activity_tvshow_detail.img_photo
import kotlinx.android.synthetic.main.activity_tvshow_detail.txt_description
import kotlinx.android.synthetic.main.activity_tvshow_detail.txt_name
import kotlinx.android.synthetic.main.activity_tvshow_detail.txt_score
import kotlinx.android.synthetic.main.activity_tvshow_detail.txt_year

class TvshowDetailActivity : AppCompatActivity(), View.OnClickListener {

    private var position: Int = 0
    private lateinit var favoriteHelper: FavoriteHelper

    companion object {
        const val EXTRA_TVSHOW = "extra_tvshow"
        const val EXTRA_POSITION_TVSHOW = "extra_position_tvshow"
        const val RESULT_ADD_TVSHOW = 101
        const val RESULT_DELETE_TVSHOW = 301
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tvshow_detail)
        val tvshow = intent.getParcelableExtra(EXTRA_TVSHOW) as TvShow

        favoriteHelper = FavoriteHelper.getInstance(applicationContext)
        favoriteHelper.open()

        val url = "https://image.tmdb.org/t/p/w342/${tvshow.photo}"
        Glide.with(this)
            .load(url)
            .into(img_photo)
        txt_name.text = tvshow.name
        txt_description.text = tvshow.description
        txt_year.text = tvshow.year
        txt_score.text = tvshow.score

        if (favoriteHelper.checkTvshow(tvshow.id)){
            btn_tvshow.text = getString(R.string.delete)
        }else{
            btn_tvshow.text = getString(R.string.add)
        }

        btn_tvshow.setOnClickListener(this)

    }
    override fun onClick(v: View) {
        if (v.id == R.id.btn_tvshow) {
            val tvshow = intent.getParcelableExtra(TvshowDetailActivity.EXTRA_TVSHOW) as TvShow
            val id = tvshow.id
            val photo = tvshow.photo
            val name = tvshow.name
            val description = tvshow.description
            val score = tvshow.score
            val year = tvshow.year

            val intent = Intent()
            intent.putExtra(EXTRA_TVSHOW, tvshow)
            intent.putExtra(EXTRA_POSITION_TVSHOW, position)

            val values = ContentValues()
            values.put(DatabaseContract.MovieColumns._ID, id)
            values.put(DatabaseContract.MovieColumns.PHOTO, photo)
            values.put(DatabaseContract.MovieColumns.NAME, name)
            values.put(DatabaseContract.MovieColumns.DESCRIPTION, description)
            values.put(DatabaseContract.MovieColumns.SCORE, score)
            values.put(DatabaseContract.MovieColumns.YEAR, year)

            if (favoriteHelper.checkTvshow(tvshow.id)) {
                val result = favoriteHelper.deleteTvshowById(tvshow.id.toString()).toLong()
                if (result > 0) {
                    Toast.makeText(
                        this@TvshowDetailActivity,
                        getString(R.string.success_remove),
                        Toast.LENGTH_SHORT
                    ).show()
                    intent.putExtra(EXTRA_POSITION_TVSHOW, position)
                    setResult(RESULT_DELETE_TVSHOW, intent)
                    finish()
                } else {
                    Toast.makeText(
                        this@TvshowDetailActivity,
                        getString(R.string.failed_remove),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                val result = favoriteHelper.insertTvshow(values)
                if (result > 0) {
                    Toast.makeText(
                        this@TvshowDetailActivity,
                        getString(R.string.success_add),
                        Toast.LENGTH_SHORT
                    ).show()
                    setResult(RESULT_ADD_TVSHOW, intent)
                    finish()
                } else {
                    Toast.makeText(
                        this@TvshowDetailActivity,
                        getString(R.string.failed_add),
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }
        }

    }
}
