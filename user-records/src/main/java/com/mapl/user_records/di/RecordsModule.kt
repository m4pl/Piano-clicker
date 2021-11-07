package com.mapl.user_records.di

import com.github.terrakok.cicerone.Router
import com.mapl.navigation.domain.interactor.OpenRecordsScreen
import com.mapl.user_records.domain.interactor.navigation.OpenRecordsScreenImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RecordsModule {

    @Provides
    @Singleton
    fun provideOpenRecordsScreen(
        router: Router
    ): OpenRecordsScreen = OpenRecordsScreenImpl(router)
}
