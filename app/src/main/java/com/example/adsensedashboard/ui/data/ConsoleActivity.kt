package com.example.adsensedashboard.ui.data

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.adsensedashboard.R
import com.example.adsensedashboard.databinding.ActivityConsoleBinding
import com.example.adsensedashboard.ui.data.consoleUI.main.SectionsPagerAdapter
import com.example.adsensedashboard.utils.toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.Scope
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.firebase.Firebase
import com.google.firebase.auth.auth


class ConsoleActivity : AppCompatActivity() {
    private val TAG = "ConsoleActivity"
    private lateinit var binding: ActivityConsoleBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityConsoleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = binding.viewPager

        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        tabs.setupWithViewPager(viewPager)
        val fab: FloatingActionButton = binding.fab

        fab.setOnClickListener { view -> performSignOut() }

    }


    private fun performSignOut() {
        Firebase.auth.signOut()
        val account = GoogleSignIn.getLastSignedInAccount(this)
        if (account == null) {
            toast("SignOut Error...")
            return
        }
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestScopes(Scope("https://www.googleapis.com/auth/adsense.readonly"))
            .requestEmail()
            .build()

        val googleSignInClient = GoogleSignIn.getClient(this, gso)
        googleSignInClient.signOut().addOnCompleteListener {
            toast("Sign out completed")
            finish()
        }
    }
}