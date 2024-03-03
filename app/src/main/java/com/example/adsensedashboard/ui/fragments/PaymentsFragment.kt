package com.example.adsensedashboard.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.adsensedashboard.R
import com.example.adsensedashboard.viewModels.PaymentsViewModel

class PaymentsFragment : Fragment() {

    companion object {
        fun newInstance() = PaymentsFragment()
    }

    private lateinit var viewModel: PaymentsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_payments, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PaymentsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}