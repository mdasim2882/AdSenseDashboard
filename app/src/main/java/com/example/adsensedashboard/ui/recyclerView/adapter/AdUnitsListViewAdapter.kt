package com.example.adsensedashboard.ui.recyclerView.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.adsensedashboard.R
import com.example.adsensedashboard.databinding.AdUnitDataItemBinding
import java.util.Random


data class AdUnitsData(
    val adUnitName: String,
    val adUnitCost: String,
)

class AdUnitsListViewAdapter(context: Context, private val listItems: List<AdUnitsData>) :
    ArrayAdapter<AdUnitsData>(context, 0, listItems) {

    private lateinit var binding: AdUnitDataItemBinding

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // convertView which is recyclable view
        var currentItemView = convertView

        // of the recyclable view is null then inflate the custom layout for the same
        if (currentItemView == null) {
            currentItemView =
                LayoutInflater.from(context).inflate(R.layout.ad_unit_data_item, parent, false)
        }
        binding = AdUnitDataItemBinding.bind(currentItemView!!)
        // get the position of the view from the ArrayAdapter
        val adUnitsData: AdUnitsData? = listItems[position]

        assert(adUnitsData != null)

        adUnitsData?.apply {
            binding.adUnitName.text = adUnitName
            binding.adUnitCost.text = adUnitCost
            val rnd = Random()
            val color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
            binding.adUnitColor.setColorFilter(color)
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
