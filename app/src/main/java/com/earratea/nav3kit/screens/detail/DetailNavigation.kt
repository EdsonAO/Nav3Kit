package com.earratea.nav3kit.screens.detail

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation3.runtime.NavKey
import com.earratea.nav3kit.navigation.EntryProviderInstaller
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.multibindings.IntoSet
import kotlinx.serialization.Serializable

@Serializable
data class DetailRoute(
    val id: String
) : NavKey

@Module
@InstallIn(ActivityRetainedComponent::class)
object DetailModule {

    @IntoSet
    @Provides
    fun provideEntryProviderInstaller(): EntryProviderInstaller = {
        entry<DetailRoute> { key ->
            val viewModel = hiltViewModel<DetailViewModel, DetailViewModel.Factory>(
                creationCallback = { factory ->
                    factory.create(key)
                }
            )
            DetailScreen(viewModel = viewModel)
        }
    }
}
