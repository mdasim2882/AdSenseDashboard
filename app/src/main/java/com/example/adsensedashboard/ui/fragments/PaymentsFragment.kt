package com.example.adsensedashboard.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.adsensedashboard.databinding.FragmentPaymentsBinding
import com.example.adsensedashboard.ui.recyclerView.adapter.SitesManagerListViewAdapter
import com.example.adsensedashboard.ui.recyclerView.adapter.SitesManger
import com.example.adsensedashboard.viewModels.PaymentsViewModel

class PaymentsFragment : Fragment() {
    private val TAG = javaClass.name
    private var _binding: FragmentPaymentsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = PaymentsFragment()
    }

    private lateinit var viewModel: PaymentsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPaymentsBinding.inflate(inflater, container, false)
        setupListView()
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PaymentsViewModel::class.java)
        // TODO: Use the Payments ViewModel here
    }


    //region Description
    private fun setupListView() {
        // use arrayadapter and define an array
        val arrayAdapter: ArrayAdapter<*>
        val users = listOf(
            SitesManger("www.google.com", "Ready", "Authorized"),
            SitesManger("www.stackoverflow.com", "Ready", "Unauthorized"),
            SitesManger("www.cisce.org", "Ready", "Unauthorized"),
            SitesManger("www.wikipedia.org", "Ready", "Unauthorized"),
            SitesManger("www.geeksforgeeks.org", "Ready", "Authorized"),
            SitesManger("www.cloudskillsboost.google", "Ready", "Authorized"),
            SitesManger("www.cisce.org", "Ready", "Authorized"),
            SitesManger("www.wikipedia.org", "Ready", "Authorized"),
            SitesManger("www.geeksforgeeks.org", "Ready", "Unauthorized"),
            SitesManger("www.cloudskillsboost.google", "Ready", "Authorized"),
            SitesManger("www.cisce.org", "Ready", "Authorized"),
            SitesManger("www.wikipedia.org", "Ready", "Authorized"),
            SitesManger("www.geeksforgeeks.org", "Ready", "Unauthorized"),
            SitesManger("www.cloudskillsboost.google", "Ready", "Authorized"),
        )

        // access the listView from xml file
        var mListView = binding.sitesManagerList
        arrayAdapter = SitesManagerListViewAdapter(
            requireActivity(), users
        )
        mListView.adapter = arrayAdapter

    }
    //endregion

}