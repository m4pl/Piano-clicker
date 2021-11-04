package com.mapl.pianoclicker.app.presentation.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mapl.navigation.domain.interactor.OpenRecordsScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    openRecordsScreen: OpenRecordsScreen
) : ViewModel() {

    val permission = MutableLiveData<String>()

    init {
        openRecordsScreen.exec()
    }
}
