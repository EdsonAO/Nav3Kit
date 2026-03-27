package com.earratea.nav3kit.screens.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import com.earratea.nav3kit.navigation.Router
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel(assistedFactory = DetailViewModel.Factory::class)
class DetailViewModel @AssistedInject constructor(
    @Assisted val navKey: DetailRoute,
    val router: Router
) : ViewModel() {
    init {
        Log.e("DetailViewModel", "DetailViewModel created with navKey: ${navKey}")
    }

    @AssistedFactory
    interface Factory {
        fun create(navKey: DetailRoute): DetailViewModel
    }
}
