package com.earratea.nav3kit.screens.home

data class HomeUiState(
    val isLoading: Boolean = false,
)

sealed interface HomeUiEvent {
    data object ShowToast : HomeUiEvent
}

sealed interface HomeEvent {
    data object GoToDetailClicked : HomeEvent
    data object GoToProfileClicked : HomeEvent
}

