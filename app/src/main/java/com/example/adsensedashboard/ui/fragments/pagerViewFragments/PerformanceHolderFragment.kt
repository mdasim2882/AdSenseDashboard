package com.example.adsensedashboard.ui.fragments.pagerViewFragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.adsensedashboard.databinding.FragmentPerformanceHolderBinding
import com.example.adsensedashboard.models.api.PerformanceTest
import com.example.adsensedashboard.utils.JSON_FORMAT
import com.example.adsensedashboard.utils.toast
import com.example.adsensedashboard.viewModels.PerformanceHolderViewModel
import com.example.adsensedashboard.viewModels.PerformanceHolderViewModelFactory
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.gson.Gson
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PerformanceHolderFragment : Fragment() {
    private val TAG = javaClass.name
    private lateinit var binding: FragmentPerformanceHolderBinding
    private lateinit var viewModel: PerformanceHolderViewModel

    companion object {
        fun newInstance() = PerformanceHolderFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPerformanceHolderBinding.inflate(layoutInflater, container, false)
        setupArchitectureComponents()
        makeAPICall()
        return binding.root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PerformanceHolderViewModel::class.java)
    }


    //region API throttling
    private fun setupArchitectureComponents() {
        viewModel = ViewModelProvider(
            this, PerformanceHolderViewModelFactory(requireActivity().application)
        ).get(PerformanceHolderViewModel::class.java)

        lifecycle.addObserver(viewModel)

        setupObservers()

    }

    //region ViewModel Observers
    private fun setupObservers() {
        viewModel.response.observe(viewLifecycleOwner) {
            Log.d(TAG, "onCreate: Fetching Response...from Observer")
            if (it == null) {
                Log.d(TAG, "onCreate: NULL Response")
                return@observe
            }
            // TODO: Try to use the accountId from here and then call getSites()
            updateUI(it, "accounts")
        }
        viewModel.responseSitesAPI.observe(viewLifecycleOwner) {
            Log.d(TAG, "onCreate: Fetching responseSitesAPI Response...from Observer")
            if (it == null) {
                Log.d(TAG, "onCreate: NULL Response")
                return@observe
            }
            updateUI(it, "sites")
        }
    }
//endregion

    //region Triggering API calls
    @OptIn(DelicateCoroutinesApi::class)
    private fun makeAPICall() {
        GlobalScope.launch {
            val token = generateAccessToken()
            Log.d(TAG, "makeAPICall: ACCESS TOKEN : $token")
            Log.d(
                TAG,
                "makeAPICall: SERVER AUTH CODE : ${
                    GoogleSignIn.getLastSignedInAccount(
                        requireContext()
                    )?.serverAuthCode
                }"
            )
            token?.let {
                val account = viewModel.getAccount("Bearer $token", JSON_FORMAT)
                account?.let {
                    val dimensions = listOf<String>()
                    val metrics = listOf(
                        "PAGE_VIEWS",
                        "PAGE_VIEWS_RPM",
                        "IMPRESSIONS",
                        "CLICKS",
                        "COST_PER_CLICK",
                        "PAGE_VIEWS_CTR"
                    )
                    val sites = viewModel.getSites(
                        account.accounts[0].name.split('/')[1],
//                        "pub-7890024740593023",
                        "Bearer $token",
                        JSON_FORMAT, "YESTERDAY", metrics, dimensions
                    )

                }
            }

        }
    }
    //endregion

    private fun generateAccessToken(): String? {
        var token: String? = null
        try {
            // Check for existing Google Sign In account, if the user is already signed in
            // the GoogleSignInAccount will be non-null
            val currentAccount = GoogleSignIn.getLastSignedInAccount(requireContext())
            currentAccount?.let { signin ->
                val credential = GoogleAccountCredential.usingOAuth2(
                    context, listOf("https://www.googleapis.com/auth/adsense.readonly")
                ).setSelectedAccount(signin.account)

                // TODO : Setup Backoff if needed

                token = credential.token
            }
        } catch (e: Exception) {
            // TODO("Handle the AdSenseAPI Retrofit response exception")
            context?.toast("Network issue")
        }
        return token
    }

    private fun <T> updateUI(responseAPI: T, type: String) {
        Log.d(TAG, "updateUI: Called with ${responseAPI.toString()}")
        val jsonData = Gson().toJson(responseAPI)

        when (type) {
            "sites" -> {
                val resp = responseAPI as PerformanceTest

                binding.pageView.text = String.format("${resp.rows[0].cells[0].value}K")
                binding.pageRpm.text = String.format("${resp.rows[0].cells[1].value}")
                binding.pageImpressions.text = String.format("${resp.rows[0].cells[2].value}K")
                binding.pageClicks.text = String.format("${resp.rows[0].cells[3].value}")
                binding.pageCpc.text = String.format("${resp.rows[0].cells[4].value}")
                binding.pageCtr.text = String.format("${resp.rows[0].cells[5].value}")
            
            }

            "accounts" -> {
                // TODO:  Don't require much info, we can reuse account
                // in a common variable
            }
        }

        Log.d(TAG, "updateUI: RESPONSE ON DASHBOARD UI= $jsonData")
    }

//endregion


}