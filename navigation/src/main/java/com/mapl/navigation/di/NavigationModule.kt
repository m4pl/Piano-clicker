package com.mapl.navigation.di

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.mapl.navigation.domain.CustomNavigator
import com.mapl.navigation.domain.CustomNavigatorImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NavigationModule {

    @Provides
    @Singleton
    fun provideCicerone(): Cicerone<Router> {
        return Cicerone.create()
    }

    @Provides
    @Singleton
    fun provideRouter(
        cicerone: Cicerone<Router>
    ): Router = cicerone.router

    @Provides
    @Singleton
    fun provideNavigationHolder(
        cicerone: Cicerone<Router>
    ): NavigatorHolder = cicerone.getNavigatorHolder()

    @Provides
    @Singleton
    fun provideNavigator(): CustomNavigator = CustomNavigatorImpl()
}
