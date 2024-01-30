package com.example.adsensedashboard.ui.recyclerView.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.adsensedashboard.R
import com.example.adsensedashboard.databinding.SitesCardBinding


data class Sites(
    val websiteName: String,
    val estimationCost: String,
    val pageView: String = "",
    val clicksCount: String = ""
)

class SitesListViewAdapter(context: Context, private val listItems: List<Sites>) :
    ArrayAdapter<Sites>(context, 0, listItems) {

    private lateinit var binding: SitesCardBinding

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // convertView which is recyclable view
        var currentItemView = convertView

        // of the recyclable view is null then inflate the custom layout for the same
        if (currentItemView == null) {
            currentItemView =
                LayoutInflater.from(context).inflate(R.layout.sites_card, parent, false)
        }
        binding = SitesCardBinding.bind(currentItemView!!)
        // get the position of the view from the ArrayAdapter
        val site: Sites? = listItems[position]

        assert(site != null)

        site?.apply {
            binding.websiteName.text = websiteName
            binding.estimationCost.text = estimationCost
            binding.pageViews.text = pageView
            binding.clickCounts.text = clicksCount
        }

        return binding.root
    }
    
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return listItems.size
    }
}
