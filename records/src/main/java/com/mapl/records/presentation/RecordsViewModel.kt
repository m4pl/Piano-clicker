package com.mapl.records.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mapl.core_ui.presentation.permission.PermissionController
import com.mapl.core_ui.presentation.permission.Permissions.READ_STORAGE
import com.mapl.core_ui.presentation.permission.Permissions.WRITE_STORAGE
import com.mapl.core_ui.presentation.setting.Settings.ACCESSIBILITY_ENABLED
import com.mapl.core_ui.presentation.setting.SettingsController
import com.mapl.navigation.domain.interactor.OpenAccessibilitySettingsScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RecordsViewModel @Inject constructor(
    private val permissionController: PermissionController,
    private val settingsController: SettingsController,
    private val openAccessibilitySettingsScreen: OpenAccessibilitySettingsScreen
) : ViewModel() {

    val requestPermissions = MutableLiveData<Array<String>>()

    fun checkPermissions() {
        when {
            !isStorageGranted() -> requestStoragePermissions()
            !isAccessibilitySettingsGranted() -> openAccessibilitySettingsScreen.exec()
            else -> {
                // TODO: Start record service
            }
        }
    }

    private fun isStorageGranted(): Boolean {
        return permissionController.isAllGranted(STORAGE_PERMISSIONS)
    }

    private fun isAccessibilitySettingsGranted(): Boolean {
        return settingsController.isSettingEnabled(ACCESSIBILITY_ENABLED)
    }

    private fun requestStoragePermissions() {
        requestPermissions.value = STORAGE_PERMISSIONS
    }

    private companion object {
        // TODO: Set up write storage
        val STORAGE_PERMISSIONS = arrayOf(READ_STORAGE, WRITE_STORAGE)
    }
}
