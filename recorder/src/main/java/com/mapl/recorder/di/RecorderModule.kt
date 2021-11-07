package com.mapl.recorder.di

import android.content.Context
import com.mapl.recorder.domain.interactor.navigation.StartRecorder
import com.mapl.recorder.domain.interactor.navigation.StartRecorderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RecorderModule {

    @Provides
    @Singleton
    fun provideStartRecorder(
        @ApplicationContext
        context: Context
    ): StartRecorder = StartRecorderImpl(context)
}
