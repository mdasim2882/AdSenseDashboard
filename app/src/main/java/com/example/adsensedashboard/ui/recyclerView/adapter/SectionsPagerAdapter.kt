package com.example.adsensedashboard.ui.recyclerView.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.adsensedashboard.R
import com.example.adsensedashboard.ui.fragments.pagerViewFragments.CountriesHolderFragment
import com.example.adsensedashboard.ui.fragments.pagerViewFragments.PerformanceHolderFragment
import com.example.adsensedashboard.ui.fragments.pagerViewFragments.PlaceholderFragment


private val TAB_TITLES = arrayOf(
    R.string.tab_text_1,
    R.string.tab_text_2,
    R.string.tab_text_3
)

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment.
        var fragment: Fragment? = null
        when (position + 1) {
            1 -> fragment = PlaceholderFragment.newInstance(position + 1)
            2 -> fragment = PerformanceHolderFragment.newInstance()
            3 -> fragment = CountriesHolderFragment.newInstance()
        }

        return fragment!!
    }

    override fun getPageTitle(position: Int): CharSequence {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int = 3
}