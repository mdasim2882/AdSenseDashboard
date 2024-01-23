package com.example.adsensedashboard.ui.data


import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.adsensedashboard.R
import com.example.adsensedashboard.databinding.ActivityConsoleUiBinding
import com.example.adsensedashboard.ui.fragments.EarningsFragment
import com.example.adsensedashboard.ui.fragments.PaymentsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

private const val TAG = "ConsoleUiActivity"

class ConsoleUiActivity : AppCompatActivity() {

    var FRAGMENT_STATUS = 0
    var COMING_FROM = null
    private lateinit var binding: ActivityConsoleUiBinding
    private val bottomNavigationView: BottomNavigationView by lazy {
        findViewById(R.id.navigation)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConsoleUiBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setBottomNavigationMenu();
    }

    private fun setBottomNavigationMenu() {
        binding.navigation.visibility = View.VISIBLE
        binding.navigation.selectedItemId = 0
        binding.navigation.setOnItemSelectedListener {
            Log.d(TAG, "setBottomNavigationMenu: Setting Fragment")
            when (it.itemId) {

                R.id.action_earnings -> loadFromFragment(EarningsFragment())
                R.id.action_payments -> loadFromFragment(PaymentsFragment())
                R.id.action_settings -> loadFromFragment(EarningsFragment())
                else -> loadFromFragment(null)
            }
        }
        binding.navigation.setOnItemReselectedListener { true }

        Log.d(TAG, "setBottomNavigationMenu: ${binding.navigation.selectedItemId}")
        // TODO: SelectedItemId not working on startup
        binding.navigation.selectedItemId = R.id.action_earnings
    }

    override fun onResume() {
        super.onResume()

    }

    private fun loadFromFragment(fragment: Fragment?): Boolean {
        if (fragment != null) {
            supportFragmentManager.beginTransaction().replace(R.id.my_container, fragment)
                .commit()
            if (COMING_FROM == null) {
                FRAGMENT_STATUS = 1
            } else {
                FRAGMENT_STATUS = 0
            }
            return true
        }
        return false
    }

    override fun onBackPressed() {
        if (FRAGMENT_STATUS > 0) {
            val fragment: Fragment = EarningsFragment()
            loadFromFragment(fragment)
            bottomNavigationView.selectedItemId = R.id.action_earnings
            FRAGMENT_STATUS = 0
            bottomNavigationView.visibility = View.VISIBLE
        } else {
            super.onBackPressed()
        }
    }


}