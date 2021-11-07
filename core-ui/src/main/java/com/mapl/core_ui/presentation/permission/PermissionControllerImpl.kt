package com.mapl.core_ui.presentation.permission

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

interface PermissionController {
    fun isGranted(permission: String): Boolean

    fun isAllGranted(permissions: Array<String>): Boolean
}

internal class PermissionControllerImpl(
    private val context: Context
) : PermissionController {

    override fun isGranted(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
    }

    override fun isAllGranted(permissions: Array<String>): Boolean {
        permissions.forEach { permission ->
            if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) return false
        }
        return true
    }
}
