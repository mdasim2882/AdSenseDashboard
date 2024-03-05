package com.example.adsensedashboard.ui.recyclerView.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.adsensedashboard.R
import com.example.adsensedashboard.ui.recyclerView.holders.EarningsViewHolder


class EarningsRecyclerViewAdapter(context: Context, private val items: List<String>) :
    RecyclerView.Adapter<EarningsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EarningsViewHolder {
        val layoutView: View =
            LayoutInflater.from(parent.context).inflate(R.layout.earning_cards, parent, false)

        return EarningsViewHolder(layoutView)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: EarningsViewHolder, position: Int) {
        // TODO: Handle card click and setup actions
        holder.bindData(items[position], position)
    }
}