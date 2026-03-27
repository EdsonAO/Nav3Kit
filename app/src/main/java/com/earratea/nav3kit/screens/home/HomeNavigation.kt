package com.earratea.nav3kit.screens.home

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation3.runtime.NavKey
import com.earratea.nav3kit.navigation.EntryProviderInstaller
import com.earratea.nav3kit.navigation.RouterEffect
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.multibindings.IntoSet
import kotlinx.serialization.Serializable

@Serializable
data object HomeRoute : NavKey

@Module
@InstallIn(ActivityRetainedComponent::class)
object HomeModule {

    @IntoSet
    @Provides
    fun provideEntryProviderInstaller(): EntryProviderInstaller = { backStack ->
        entry<HomeRoute> {
            val viewModel: HomeViewModel = hiltViewModel()
            RouterEffect(emitter = viewModel, backStack = backStack)
            HomeScreen(viewModel = viewModel)
        }
    }
}