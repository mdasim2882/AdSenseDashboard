package com.example.adsensedashboard.ui.data.consoleUI.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.adsensedashboard.R
import com.example.adsensedashboard.databinding.FragmentConsoleBinding
import com.example.adsensedashboard.viewModels.PageViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.JsonFactory
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.adsense.v2.Adsense
import kotlinx.coroutines.DelicateCoroutinesApi

/**
 * A placeholder fragment containing a simple view.
 */
class PlaceholderFragment : Fragment() {

    private lateinit var pageViewModel: PageViewModel
    private var _binding: FragmentConsoleBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentConsoleBinding.inflate(inflater, container, false)
        val root = binding.root

        val textView: TextView = binding.sectionLabel
        pageViewModel = ViewModelProvider(this).get(PageViewModel::class.java).apply {
            setIndex(arguments?.getInt(ARG_SECTION_NUMBER) ?: 1)
//            GlobalScope.launch { callAPIusingClientLibrary() }
        }
        pageViewModel.response.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        pageViewModel.text.observe(viewLifecycleOwner, Observer {
        })
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
            return PlaceholderFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }

        /** Global instance of the JSON factory.  */
        private val JSON_FACTORY: JsonFactory = GsonFactory.getDefaultInstance()
        fun initializeAdsense(context: Context): Adsense? {
            val currentAccount = GoogleSignIn.getLastSignedInAccount(context)
            currentAccount?.let { signin ->
                val credential = GoogleAccountCredential.usingOAuth2(
                    context,
                    listOf("https://www.googleapis.com/auth/adsense.readonly")
                ).setSelectedAccount(signin.account)//

                val adsense = Adsense.Builder(
                    NetHttpTransport(), JSON_FACTORY, credential
                ).setApplicationName(context.getString(R.string.app_name))
                    .build()
                return adsense
            }
            return null
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}