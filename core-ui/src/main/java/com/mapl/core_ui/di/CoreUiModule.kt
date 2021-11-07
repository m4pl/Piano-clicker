package com.mapl.core_ui.di

import android.content.Context
import com.mapl.core_ui.presentation.permission.PermissionController
import com.mapl.core_ui.presentation.permission.PermissionControllerImpl
import com.mapl.core_ui.presentation.setting.SettingsController
import com.mapl.core_ui.presentation.setting.SettingsControllerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CoreUiModule {

    @Provides
    @Singleton
    fun providePermissionController(
        @ApplicationContext
        context: Context
    ): PermissionController = PermissionControllerImpl(context)

    @Provides
    @Singleton
    fun provideSettingsController(
        @ApplicationContext
        context: Context
    ): SettingsController = SettingsControllerImpl(context)
}
