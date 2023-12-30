package com.example.adsensedashboard.ui.data

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.adsensedashboard.R
import com.example.adsensedashboard.utils.EMAIL
import com.example.adsensedashboard.utils.SOURCE
import com.example.adsensedashboard.utils.USER

class DashboardActivity : AppCompatActivity() {
    private val TAG = "DashboardActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val source = intent.getStringExtra(SOURCE)
        val user = intent.getStringExtra(USER)
        val email = intent.getStringExtra(EMAIL)

        Log.d(TAG, "updateUI: ${source}")
        Log.d(TAG, "updateUI: ${user}")
        Log.d(TAG, "updateUI: ${email}")
    }
}