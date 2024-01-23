package com.example.adsensedashboard.ui.fragments

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.adsensedashboard.databinding.FragmentEarningsBinding
import com.example.adsensedashboard.ui.recyclerView.adapter.EarningsRecyclerViewAdapter


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EarningsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EarningsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentEarningsBinding
    private val recyclerView = binding.earningItemsRecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEarningsBinding.inflate(inflater, container, false)
        val view = binding.root
        setRecyclerView()
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment EarningsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EarningsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


    private fun setRecyclerView() {

        // Set up the RecyclerView

        // Set up the RecyclerView
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager =
            GridLayoutManager(activity, 1, GridLayoutManager.VERTICAL, false)
        /*
         * Pass parameter as list of type ProductEntry
         * Must be retrieved from database to here only
         * ProductEntry contains three fields:
         * ImageView productImage
         * TextView productName, productCost;
         * */
    }

    fun onCartItemsLoadSuccess(templates: List<String>) {
        val adapter = EarningsRecyclerViewAdapter(requireContext(), templates)
        recyclerView.adapter = adapter
        val largePadding = resources.getDimensionPixelSize(R.dimen.)
        val smallPadding = resources.getDimensionPixelSize(R.dimen.side_product_grid_spacing_small)
        recyclerView.addItemDecoration(ProductGridItemDecoration(largePadding, smallPadding))
    }
}