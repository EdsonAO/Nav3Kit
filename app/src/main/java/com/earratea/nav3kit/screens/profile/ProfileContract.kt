package com.earratea.nav3kit.screens.profile

object ProfileResult {
    const val RANDOM_NUMBER_KEY = "profile_random_number"
}

data class ProfileUiState(
    val randomNumber: Int = 0,
)

sealed interface ProfileEvent {
    data object SendResultAndGoBack : ProfileEvent
}
