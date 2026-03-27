package com.earratea.nav3kit.screens.home

data class HomeUiState(
    val isLoading: Boolean = false,
    val profileResult: Int? = null,
)

sealed interface HomeEvent {
    data object GoToDetailClicked : HomeEvent
    data object GoToProfileClicked : HomeEvent
}

