package com.earratea.nav3kit.navigation

import androidx.navigation3.runtime.NavKey

sealed interface RouterAction {
    data class NavigateTo(val route: NavKey) : RouterAction
    data object GoBack : RouterAction
    data class ReplaceWith(val route: NavKey) : RouterAction
    data class PopToRoute(val route: NavKey, val inclusive: Boolean = false) : RouterAction
    data class ClearStackAndNavigate(val route: NavKey) : RouterAction
}
