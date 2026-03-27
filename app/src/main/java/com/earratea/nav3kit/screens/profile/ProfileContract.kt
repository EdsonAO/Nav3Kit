package com.earratea.nav3kit.screens.profile

import com.earratea.nav3kit.navigation.IntResult
import com.earratea.nav3kit.navigation.ResultKey

object ProfileResult {
    val RandomNumber = ResultKey<IntResult>("profile_random_number")
}

data class ProfileUiState(
    val randomNumber: Int = 0,
)

sealed interface ProfileEvent {
    data object SendResultAndGoBack : ProfileEvent
}
