package com.mapl.records.domain.interactor.navigation

import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.mapl.navigation.domain.interactor.OpenRecordsScreen
import com.mapl.records.presentation.RecordsFragment

internal class OpenRecordsScreenImpl(
    private val router: Router
) : OpenRecordsScreen {

    private val screen by lazy { FragmentScreen { RecordsFragment.newInstance() } }

    override fun exec() {
        router.newRootScreen(screen)
    }
}
