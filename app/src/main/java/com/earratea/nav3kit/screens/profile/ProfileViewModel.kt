package com.earratea.nav3kit.screens.profile

import androidx.lifecycle.ViewModel
import com.earratea.nav3kit.navigation.IntResult.Companion.asResult
import com.earratea.nav3kit.navigation.Router
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val router: Router
) : ViewModel() {

    val uiState: StateFlow<ProfileUiState>
        field = MutableStateFlow(ProfileUiState(randomNumber = (0..1000).random()))

    fun handleEvent(event: ProfileEvent) {
        when (event) {
            is ProfileEvent.SendResultAndGoBack -> router.goBack {
                val number = uiState.value.randomNumber
                result(ProfileResult.RandomNumber, number.asResult())
            }
        }
    }
}
