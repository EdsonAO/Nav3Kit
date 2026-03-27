package com.earratea.nav3kit.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.earratea.nav3kit.navigation.Router
import com.earratea.nav3kit.screens.detail.DetailRoute
import com.earratea.nav3kit.screens.profile.ProfileResult
import com.earratea.nav3kit.screens.profile.ProfileRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val router: Router
) : ViewModel() {
    val uiState: StateFlow<HomeUiState>
        field = MutableStateFlow(HomeUiState())

    init {
        router.setResultListener<Int>(ProfileResult.RANDOM_NUMBER_KEY) { number ->
            uiState.update { it.copy(profileResult = number) }
        }
    }

    fun handleEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.GoToDetailClicked -> handleGoToDetailClicked()
            is HomeEvent.GoToProfileClicked -> handleGoToProfileClicked()
        }
    }

    private fun handleGoToDetailClicked() = viewModelScope.launch {
        if (uiState.value.isLoading) return@launch

        uiState.update {
            it.copy(isLoading = true)
        }

        delay(2000) // Simulate async work

        uiState.update {
            it.copy(isLoading = false)
        }

        val randomId = (0..100).random().toString()
        router.navigateTo(DetailRoute(randomId))
    }

    private fun handleGoToProfileClicked() = viewModelScope.launch {
        if (uiState.value.isLoading) return@launch

        uiState.update {
            it.copy(isLoading = true)
        }

        delay(2000) // Simulate async work

        uiState.update {
            it.copy(isLoading = false)
        }

        router.navigateTo(ProfileRoute)
    }
}
