package com.example.adsensedashboard.ui.fragments.pagerViewFragments


import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.adsensedashboard.databinding.CardSitesBinding
import com.example.adsensedashboard.ui.recyclerView.adapter.Sites
import com.example.adsensedashboard.ui.recyclerView.adapter.SitesListViewAdapter
import com.example.adsensedashboard.viewModels.PageViewModel

/**
 * A placeholder fragment containing a simple view.
 */
class PlaceholderFragment : Fragment() {
    private val TAG = "PlaceHolderFragment"
    private lateinit var pageViewModel: PageViewModel
    private var _binding: CardSitesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = CardSitesBinding.inflate(inflater, container, false)
        val root = binding.root

//        val textView: TextView = binding.sectionLabel
        pageViewModel = ViewModelProvider(requireActivity()).get(PageViewModel::class.java).apply {
            setIndex(arguments?.getInt(ARG_SECTION_NUMBER) ?: 1)
//            GlobalScope.launch { callAPIusingClientLibrary() }
        }
//        pageViewModel.response.observe(viewLifecycleOwner, Observer {
//        })
//        pageViewModel.text.observe(viewLifecycleOwner, {
//            textView.text = it
//            Log.d(TAG, "observed onCreateView: $it")
//        })
        setupListView()
        return root
    }

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val ARG_SECTION_NUMBER = "section_number"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(sectionNumber: Int): PlaceholderFragment {
            Log.d("PlaceHolderFragment", "newInstance: CREATED starting Fragment.....")
            return PlaceholderFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }

        /** Global instance of the JSON factory.  */
//        private val JSON_FACTORY: JsonFactory = GsonFactory.getDefaultInstance()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @SuppressLint("ClickableViewAccessibility")
    //region Description
    private fun setupListView() {
        // use arrayadapter and define an array
        val arrayAdapter: ArrayAdapter<*>
        val users = listOf(
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
        var mListView = binding.userlist
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
    //endregion
}