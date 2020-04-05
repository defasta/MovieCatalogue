package com.example.moviecatalogue2.fragments


import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviecatalogue2.R
import com.example.moviecatalogue2.activites.MovieSearchActivity
import com.example.moviecatalogue2.activites.TvshowDetailActivity
import com.example.moviecatalogue2.activites.TvshowSearchActivity
import com.example.moviecatalogue2.adapters.ListTvShowAdapter
import com.example.moviecatalogue2.entities.TvShow
import com.example.moviecatalogue2.models.TvshowViewModel
import kotlinx.android.synthetic.main.fragment_movie.*
import kotlinx.android.synthetic.main.fragment_tvshow.*
import kotlinx.android.synthetic.main.fragment_tvshow.fab_search

/**
 * A simple [Fragment] subclass.
 */
class TvshowFragment : Fragment() {
    private val list = ArrayList<TvShow>()
    private lateinit var tvshowViewModel: TvshowViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tvshow, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        rv_tvshows.setHasFixedSize(true)
        showRecyclerList()

        fab_search.setOnClickListener{
            val intent = Intent(context, TvshowSearchActivity::class.java)
            startActivity(intent)
        }
    }


    private fun showRecyclerList() {
        rv_tvshows.layoutManager = LinearLayoutManager(context)
        val listTvShowAdapter = ListTvShowAdapter(list)
        rv_tvshows.adapter = listTvShowAdapter

        showLoading(true)

        listTvShowAdapter.setOnItemClickCallback(object : ListTvShowAdapter.OnItemClickCallback {
            override fun onItemClicked(data: TvShow) {
                showSelectedTvShow(data)
            }
        })

        tvshowViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(TvshowViewModel::class.java)

        tvshowViewModel.setTvshow()

        tvshowViewModel.getTvshow().observe(this, Observer { tvshow ->
            if (tvshow != null) {
                listTvShowAdapter.setData(tvshow)
                showLoading(false)
            }
        })
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progress_bar.visibility = View.VISIBLE
        } else {
            progress_bar.visibility = View.GONE
        }
    }

    private fun showSelectedTvShow(tvShow: TvShow) {
        val tvShowDetailIntent = Intent(context, TvshowDetailActivity::class.java)
        tvShowDetailIntent.putExtra(TvshowDetailActivity.EXTRA_TVSHOW, tvShow)
        startActivity(tvShowDetailIntent)
    }

}
