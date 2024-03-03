package com.example.adsensedashboard.ui.fragments


import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.adsensedashboard.R
import com.example.adsensedashboard.databinding.FragmentEarningsBinding
import com.example.adsensedashboard.ui.recyclerView.adapter.EarningsRecyclerViewAdapter
import com.example.adsensedashboard.ui.recyclerView.adapter.SectionsPagerAdapter
import com.example.adsensedashboard.ui.recyclerView.animations.ProductGridItemDecoration
import com.google.android.material.tabs.TabLayout


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
    private val TAG = "EarningsFragment"

    // TODO: Earnings - Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentEarningsBinding
    private lateinit var recyclerView: RecyclerView
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
        recyclerView = binding.earningItemsRecyclerView
        setRecyclerView()
        setupTabView()
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        setupTabView()
        // TODO : Need to check which is the best place to initialize the Tab Layout
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setRecyclerView() {

        // Set up the RecyclerView

        // Set up the RecyclerView
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager =
            GridLayoutManager(activity, 2, GridLayoutManager.VERTICAL, false)
        /*
         * Pass parameter as list of type ProductEntry
         * Must be retrieved from database to here only
         * ProductEntry contains three fields:
         * ImageView productImage
         * TextView productName, productCost;
         * */
        val earningsData = listOf("13.56 $", "256.07 $", "2308.46 $", "9008.83 $")
        onEarningsLoadSuccess(earningsData)

    }

    private fun setupTabView() {
        val sectionsPagerAdapter = SectionsPagerAdapter(requireContext(), parentFragmentManager)
        val viewPager: ViewPager = binding.sitesLayout.viewPager

        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.sitesLayout.tabs
        tabs.setupWithViewPager(viewPager)
        Log.d(TAG, "setupTabView: ${tabs.isActivated}")
    }


    fun onEarningsLoadSuccess(templates: List<String>) {
        val adapter = EarningsRecyclerViewAdapter(requireContext(), templates)
        recyclerView.adapter = adapter
        val largePadding = resources.getDimensionPixelSize(R.dimen.updown_product_grid_spacing)
        val smallPadding = resources.getDimensionPixelSize(R.dimen.side_product_grid_spacing_small)
        recyclerView.addItemDecoration(ProductGridItemDecoration(largePadding, smallPadding))
    }
}