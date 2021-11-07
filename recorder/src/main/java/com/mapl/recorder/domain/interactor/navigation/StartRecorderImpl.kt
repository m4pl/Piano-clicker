package com.mapl.recorder.domain.interactor.navigation

import android.content.Context
import android.content.Intent
import com.mapl.recorder.service.RecordService

interface StartRecorder {
    fun exec()
}

internal class StartRecorderImpl(
    private val context: Context
) : StartRecorder {

    override fun exec() {
        Intent(context, RecordService::class.java)
            .let(context::startService)
    }
}
