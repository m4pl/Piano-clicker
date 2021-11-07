package com.mapl.pianoclicker.app.presentation.main

import androidx.lifecycle.ViewModel
import com.mapl.navigation.domain.interactor.OpenRecordsScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val openRecordsScreen: OpenRecordsScreen
) : ViewModel() {

    fun openRecordsScreen() {
        openRecordsScreen.exec()
    }
}
