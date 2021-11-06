package com.mapl.core.service

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent
import timber.log.Timber


class PianoClickerAccessibilityService: AccessibilityService() {

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        Timber.d("--> AccessibilityService onAccessibilityEvent")
    }

    override fun onInterrupt() {
        Timber.d("--> AccessibilityService onInterrupt")
    }
}
