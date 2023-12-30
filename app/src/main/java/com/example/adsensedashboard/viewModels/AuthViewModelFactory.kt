package com.example.adsensedashboard.viewModels

import android.app.Application
import android.view.View
import androidx.activity.result.ActivityResultRegistry
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AuthViewModelFactory(
    private val app: Application,
    private val registry: ActivityResultRegistry
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AuthViewModel(app, registry) as T
    }
}