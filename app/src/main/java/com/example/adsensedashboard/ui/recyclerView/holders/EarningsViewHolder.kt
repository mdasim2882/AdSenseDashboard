package com.example.adsensedashboard.ui.recyclerView.holders

import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.adsensedashboard.databinding.EarningCardsBinding

class EarningsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val binding: EarningCardsBinding = EarningCardsBinding.bind(itemView)
    fun bindData(price: String, pos: Int) {
//        TODO: Handle card click and data setup here
        when (pos) {
            0 -> {
                binding.priceTitle.text = getTitle("Today's so far")
                binding.price.text = price
            }

            1 -> {
                binding.priceTitle.text = getTitle("Yesterday")
                binding.price.text = price
            }

            2 -> {
                binding.priceTitle.text = getTitle("Last 7 days")
                binding.price.text = price
            }

            3 -> {
                binding.priceTitle.text = getTitle("This Month")
                binding.price.text = price
            }

        }

        binding.weeklyCard.setOnClickListener { v ->
            Toast.makeText(
                v.context,
                "Working on pos: ${pos} having price = ${price}",
                Toast.LENGTH_SHORT
            ).show();
        }
    }

    private fun getTitle(earningType: String): String = "${earningType}( $ )"


}
