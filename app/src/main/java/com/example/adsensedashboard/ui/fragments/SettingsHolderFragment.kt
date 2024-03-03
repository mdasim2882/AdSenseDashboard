package com.example.adsensedashboard.ui.fragments

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.example.adsensedashboard.R


class SettingsHolderFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }
}