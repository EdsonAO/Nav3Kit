package com.earratea.nav3kit.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey

@Composable
fun RouterEffect(
    emitter: RouterEffectEmitter,
    backStack: NavBackStack<NavKey>,
) {
    val navigationEffect by emitter.routerState.collectAsStateWithLifecycle()

    LaunchedEffect(navigationEffect) {
        val success: Boolean = when (val effect = navigationEffect) {
            is RouterAction.None -> true
            is RouterAction.NavigateTo -> backStack.add(effect.route)
            is RouterAction.GoBack -> backStack.removeLastOrNull() != null
        }

        if (success) {
            emitter.onNavigationHandled()
        }
    }
}
