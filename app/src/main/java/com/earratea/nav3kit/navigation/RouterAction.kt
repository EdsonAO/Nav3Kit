package com.earratea.nav3kit.navigation

import androidx.navigation3.runtime.NavKey

sealed interface RouterAction {
    data object None : RouterAction
    data class NavigateTo(val route: NavKey) : RouterAction
    data object GoBack : RouterAction
}