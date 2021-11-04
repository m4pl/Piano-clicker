package com.mapl.navigation.domain

import androidx.fragment.app.FragmentActivity
import com.github.terrakok.cicerone.Navigator

interface CustomNavigator : Navigator {
    var containerId: Int?
    var activity: FragmentActivity?
}
