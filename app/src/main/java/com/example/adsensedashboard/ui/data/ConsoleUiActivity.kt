package com.example.adsensedashboard.ui.data


import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.adsensedashboard.R
import com.example.adsensedashboard.databinding.ActivityConsoleUiBinding
import com.example.adsensedashboard.ui.fragments.EarningsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class ConsoleUiActivity : AppCompatActivity() {

    var FRAGMENT_STATUS = 0
    var COMING_FROM = null
    private lateinit var binding: ActivityConsoleUiBinding
    val bottomNavigationView: BottomNavigationView by lazy {
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
        bottomNavigationView.visibility = View.VISIBLE
        binding.navigation.setOnItemSelectedListener {
            when (it.itemId) {

                R.id.action_payments -> loadFromFragment(EarningsFragment())
                else -> loadFromFragment(null)
            }
        }
        binding.navigation.setOnItemReselectedListener { true }

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


}