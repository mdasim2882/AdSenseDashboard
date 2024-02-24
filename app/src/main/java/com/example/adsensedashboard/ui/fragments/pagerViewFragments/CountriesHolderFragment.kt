package com.example.adsensedashboard.ui.fragments.pagerViewFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.adsensedashboard.R
import com.example.adsensedashboard.viewModels.CountriesHolderViewModel

class CountriesHolderFragment : Fragment() {

    companion object {
        fun newInstance() = CountriesHolderFragment()
    }

    private lateinit var viewModel: CountriesHolderViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_countries_holder, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CountriesHolderViewModel::class.java)
        // TODO: Use the ViewModel
    }

}