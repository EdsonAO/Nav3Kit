package com.earratea.nav3kit.screens.detail

import androidx.lifecycle.ViewModel
import com.earratea.nav3kit.navigation.Router
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@HiltViewModel(assistedFactory = DetailViewModel.Factory::class)
class DetailViewModel @AssistedInject constructor(
    @Assisted navKey: DetailRoute,
    private val router: Router
) : ViewModel() {

    val uiState: StateFlow<DetailUiState>
        field = MutableStateFlow(DetailUiState(id = navKey.id))

    fun handleEvent(event: DetailEvent) {
        when (event) {
            is DetailEvent.GoBackClicked -> router.goBack()
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(navKey: DetailRoute): DetailViewModel
    }
}
