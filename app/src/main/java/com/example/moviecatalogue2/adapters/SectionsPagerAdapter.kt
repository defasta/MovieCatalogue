package com.example.moviecatalogue2.adapters

import android.content.Context
import androidx.annotation.Nullable
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.moviecatalogue2.R
import com.example.moviecatalogue2.fragments.MovieFavoriteFragment
import com.example.moviecatalogue2.fragments.TvshowFavoriteFragment

class SectionsPagerAdapter(private val mContext: Context?, fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){


    @StringRes
    private val TAB_TITLES = intArrayOf(R.string.tab_text_1, R.string.tab_text_2)

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when(position){
            0 -> fragment = MovieFavoriteFragment()
            1 -> fragment = TvshowFavoriteFragment()
        }
        return fragment as Fragment
    }

    @Nullable
    override fun getPageTitle(position: Int): String? {
        return mContext?.resources?.getString(TAB_TITLES[position])
    }


    override fun getCount(): Int {
        return 2
    }

}