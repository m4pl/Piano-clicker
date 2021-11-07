package com.mapl.pianoclicker.app.presentation.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.github.terrakok.cicerone.NavigatorHolder
import com.mapl.core_ui.util.viewbinding.viewBinding
import com.mapl.navigation.domain.CustomNavigator
import com.mapl.pianoclicker.R
import com.mapl.pianoclicker.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    protected lateinit var navigator: CustomNavigator

    @Inject
    protected lateinit var navigatorHolder: NavigatorHolder

    private val binding by viewBinding(ActivityMainBinding::inflate)
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupNavigator()
        setupViewModel()
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    private fun setupNavigator() {
        navigator.activity = this
        navigator.containerId = R.id.fragmentContainer
    }

    private fun setupViewModel() {
        viewModel.openUserRecordsScreen()
    }
}
