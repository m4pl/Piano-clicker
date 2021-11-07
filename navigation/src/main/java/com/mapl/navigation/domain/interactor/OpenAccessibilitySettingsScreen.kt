package com.mapl.navigation.domain.interactor

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.provider.Settings
import timber.log.Timber

interface OpenAccessibilitySettingsScreen {
    fun exec()
}

internal class OpenAccessibilitySettingsScreenImpl(
    private val context: Context
) : OpenAccessibilitySettingsScreen {

    override fun exec() {
        try {
            Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                .let(context::startActivity)
        } catch (e: ActivityNotFoundException) {
            Timber.e(e)
        }
    }
}
