package com.example.adsensedashboard.ui.recyclerView.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.adsensedashboard.R
import com.example.adsensedashboard.databinding.SitesManagerDataItemBinding


data class SitesManger(
    val siteURL: String,
    val approvalStatus: String,
    val adsTxtStatus: String = "",
)

class SitesManagerListViewAdapter(context: Context, private val listItems: List<SitesManger>) :
    ArrayAdapter<SitesManger>(context, 0, listItems) {

    private lateinit var binding: SitesManagerDataItemBinding

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // convertView which is recyclable view
        var currentItemView = convertView

        // of the recyclable view is null then inflate the custom layout for the same
        if (currentItemView == null) {
            currentItemView =
                LayoutInflater.from(context)
                    .inflate(R.layout.sites_manager_data_item, parent, false)
        }
        binding = SitesManagerDataItemBinding.bind(currentItemView!!)
        // get the position of the view from the ArrayAdapter
        val site: SitesManger? = listItems[position]

        assert(site != null)

        site?.apply {
            binding.siteUrl.text = siteURL
            binding.approvalStatus.text = approvalStatus
            binding.adsTxtStatus.text = adsTxtStatus

            val color = Color.argb(255, 139, 195, 74)
            binding.approvalStatus.background.setTint(color)
            if (!adsTxtStatus.equals("Authorized"))
                binding.adsTxtStatus.background.setTint(Color.RED)
            else
                binding.adsTxtStatus.background.setTint(color)

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
