package com.example.adsensedashboard.ui.fragments


import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.adsensedashboard.R
import com.example.adsensedashboard.databinding.FragmentEarningsBinding
import com.example.adsensedashboard.ui.recyclerView.adapter.EarningsRecyclerViewAdapter
import com.example.adsensedashboard.ui.recyclerView.adapter.SectionsPagerAdapter
import com.example.adsensedashboard.ui.recyclerView.adapter.Sites
import com.example.adsensedashboard.ui.recyclerView.adapter.SitesListViewAdapter
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
//        setupTabView()
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
        setupTabView()
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

        setupListView()
    }

    private fun setupTabView() {
        val sectionsPagerAdapter = SectionsPagerAdapter(requireContext(), parentFragmentManager)
        val viewPager: ViewPager = binding.sitesLayout.viewPager

        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.sitesLayout.tabs
        tabs.setupWithViewPager(viewPager)
        Log.d(TAG, "setupTabView: ${tabs.isActivated}")
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupListView() {
        // use arrayadapter and define an array
        val arrayAdapter: ArrayAdapter<*>
        val users = listOf<Sites>(
            Sites("www.google.com", "$ 3.86", pageView = "6", clicksCount = "20"),
            Sites("www.stackoverflow.com", "$ 4.57", pageView = "7", clicksCount = "16"),
            Sites("www.cisce.org", "$ 3.25", pageView = "13", clicksCount = "9"),
            Sites("www.wikipedia.org", "$ 13.34", pageView = "17", clicksCount = "14"),
            Sites("www.geeksforgeeks.org", "$ 4.56", pageView = "26", clicksCount = "34"),
            Sites("www.cloudskillsboost.google", "$ 6.44", pageView = "89", clicksCount = "56"),
            Sites("www.cisce.org", "$ 3.25", pageView = "13", clicksCount = "9"),
            Sites("www.wikipedia.org", "$ 13.34", pageView = "17", clicksCount = "14"),
            Sites("www.geeksforgeeks.org", "$ 4.56", pageView = "26", clicksCount = "34"),
            Sites("www.cloudskillsboost.google", "$ 6.44", pageView = "89", clicksCount = "56"),
        )

        // access the listView from xml file
        var mListView = ListView(requireContext())
        arrayAdapter = SitesListViewAdapter(
            requireActivity(), users
        )
        mListView.adapter = arrayAdapter
        mListView.setOnTouchListener { v, event ->
            val action = event.action
            when (action) {
                MotionEvent.ACTION_DOWN ->                 // Disallow ScrollView to intercept touch events.
                    v.parent.requestDisallowInterceptTouchEvent(true)

                MotionEvent.ACTION_UP ->                 // Allow ScrollView to intercept touch events.
                    v.parent.requestDisallowInterceptTouchEvent(false)
            }

            // Handle ListView touch events.
            v.onTouchEvent(event)
            true
        }
    }

    fun onEarningsLoadSuccess(templates: List<String>) {
        val adapter = EarningsRecyclerViewAdapter(requireContext(), templates)
        recyclerView.adapter = adapter
        val largePadding = resources.getDimensionPixelSize(R.dimen.updown_product_grid_spacing)
        val smallPadding = resources.getDimensionPixelSize(R.dimen.side_product_grid_spacing_small)
        recyclerView.addItemDecoration(ProductGridItemDecoration(largePadding, smallPadding))
    }
}