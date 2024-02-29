package com.example.adsensedashboard.ui.fragments.pagerViewFragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.adsensedashboard.databinding.FragmentAdUnitsHolderBinding
import com.example.adsensedashboard.ui.recyclerView.adapter.AdUnitsData
import com.example.adsensedashboard.ui.recyclerView.adapter.AdUnitsListViewAdapter
import com.example.adsensedashboard.viewModels.CountriesHolderViewModel

class AdUnitsHolderFragment : Fragment() {

    private lateinit var adUnitViewModel: CountriesHolderViewModel
    private var _binding: FragmentAdUnitsHolderBinding? = null
    private val binding get() = _binding!!


    companion object {
        fun newInstance() = AdUnitsHolderFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAdUnitsHolderBinding.inflate(inflater, container, false)
        val root = binding.root
        adUnitViewModel = ViewModelProvider(this).get(CountriesHolderViewModel::class.java)
        setupListView()
        return root
    }


    @SuppressLint("ClickableViewAccessibility")
    //region Description
    private fun setupListView() {
        // use arrayadapter and define an array
        val arrayAdapter: ArrayAdapter<*>
        val users = listOf(
            AdUnitsData("www.google.com", "$ 3.86"),
            AdUnitsData("www.stackoverflow.com", "$ 4.57"),
            AdUnitsData("www.cisce.org", "$ 3.25"),
            AdUnitsData("www.wikipedia.org", "$ 13.34"),
            AdUnitsData("www.geeksforgeeks.org", "$ 4.56"),
            AdUnitsData("www.cloudskillsboost.google", "$ 6.44"),
        )

        // access the listView from xml file
        var mListView = binding.adUnitsList
        arrayAdapter = AdUnitsListViewAdapter(
            requireActivity(), users
        )
        mListView.adapter = arrayAdapter
        mListView.setOnTouchListener { v, event ->
            val action = event.action
            when (action) {
                MotionEvent.ACTION_DOWN ->                 // Disallow ScrollView to intercept touch events.
                    v.parent.requestDisallowInterceptTouchEvent(true)

                MotionEvent.ACTION_UP ->                 // Allow ScrollView to intercept touch events.
                    v.parent.requestDisallowInterceptTouchEvent(false)
            }

            // Handle ListView touch events.
            v.onTouchEvent(event)
            true
        }
    }
    //endregion

}