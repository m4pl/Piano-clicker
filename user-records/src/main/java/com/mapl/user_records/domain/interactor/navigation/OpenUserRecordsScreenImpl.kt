package com.mapl.user_records.domain.interactor.navigation

import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.mapl.navigation.domain.interactor.OpenUserRecordsScreen
import com.mapl.user_records.presentation.UserRecordsFragment

internal class OpenUserRecordsScreenImpl(
    private val router: Router
) : OpenUserRecordsScreen {

    private val screen by lazy { FragmentScreen { UserRecordsFragment.newInstance() } }

    override fun exec() {
        router.newRootScreen(screen)
    }
}
