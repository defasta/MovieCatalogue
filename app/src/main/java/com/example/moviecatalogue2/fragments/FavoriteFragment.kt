package com.example.moviecatalogue2.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager

import com.example.moviecatalogue2.R
import com.example.moviecatalogue2.adapters.SectionsPagerAdapter
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_favorite.*

/**
 * A simple [Fragment] subclass.
 */
class FavoriteFragment : Fragment() {

    private lateinit var viewPager: ViewPager
    private lateinit var tabs: TabLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_favorite, container, false)
        viewPager = view.findViewById(R.id.view_pager)
        tabs = view.findViewById(R.id.tab_layout)

        val fragmentManager = SectionsPagerAdapter(context, childFragmentManager)
        viewPager.adapter = fragmentManager
        tabs.setupWithViewPager(viewPager)

        return view
    }


}