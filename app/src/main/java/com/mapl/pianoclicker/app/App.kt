package com.mapl.pianoclicker.app

import android.app.Application
import dagger.Lazy
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {

    @Inject
    protected lateinit var loggingTree: Lazy<Timber.Tree>

    override fun onCreate() {
        super.onCreate()

        setupLogging()
    }

    private fun setupLogging() {
        Timber.plant(loggingTree.get())
    }
}
