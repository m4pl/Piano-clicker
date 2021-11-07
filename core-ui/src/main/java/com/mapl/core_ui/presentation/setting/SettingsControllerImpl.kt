package com.mapl.core_ui.presentation.setting

import android.content.Context
import android.provider.Settings

interface SettingsController {
    fun isSettingEnabled(settingSecure: String): Boolean
}

internal class SettingsControllerImpl(
    private val context: Context
) : SettingsController {

    override fun isSettingEnabled(name: String): Boolean {
        return Settings.Secure.getInt(context.contentResolver, name) == 1
    }
}
