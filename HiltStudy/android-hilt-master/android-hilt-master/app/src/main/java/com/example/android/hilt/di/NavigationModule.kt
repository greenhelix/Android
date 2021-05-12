package com.example.android.hilt.di

import com.example.android.hilt.navigator.AppNavigator
import com.example.android.hilt.navigator.AppNavigatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@InstallIn(ActivityComponent::class)
@Module
abstract class NavigationModule {

    // Binds주석은 추상 함수에 달아야한다. Provides가 아니라 Binds로 제공
    @Binds
    abstract fun bindNavigator(impl: AppNavigatorImpl) : AppNavigator
}