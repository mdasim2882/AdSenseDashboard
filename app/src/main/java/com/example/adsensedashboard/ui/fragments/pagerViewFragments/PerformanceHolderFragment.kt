package com.example.adsensedashboard.ui.fragments.pagerViewFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.adsensedashboard.R
import com.example.adsensedashboard.viewModels.PerformanceHolderViewModel

class PerformanceHolderFragment : Fragment() {

    companion object {
        fun newInstance() = PerformanceHolderFragment()
    }

    private lateinit var viewModel: PerformanceHolderViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_performance_holder, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PerformanceHolderViewModel::class.java)
        // TODO: Use the ViewModel
    }

}