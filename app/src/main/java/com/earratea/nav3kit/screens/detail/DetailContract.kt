package com.earratea.nav3kit.screens.detail

data class DetailUiState(
    val id: String = "",
)

sealed interface DetailEvent {
    data object GoBackClicked : DetailEvent
}
