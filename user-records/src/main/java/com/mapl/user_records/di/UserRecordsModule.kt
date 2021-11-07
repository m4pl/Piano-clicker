package com.mapl.user_records.di

import com.github.terrakok.cicerone.Router
import com.mapl.navigation.domain.interactor.OpenUserRecordsScreen
import com.mapl.user_records.domain.interactor.navigation.OpenUserRecordsScreenImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UserRecordsModule {

    @Provides
    @Singleton
    fun provideOpenUserRecordsScreen(
        router: Router
    ): OpenUserRecordsScreen = OpenUserRecordsScreenImpl(router)
}
