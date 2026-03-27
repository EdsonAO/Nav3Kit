package com.earratea.nav3kit.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey

@Composable
fun RouterEffect(
    router: Router,
    backStack: NavBackStack<NavKey>,
) {
    LaunchedEffect(Unit) {
        for (action in router.commands) {
            when (action) {
                is RouterAction.NavigateTo -> backStack.add(action.route)
                is RouterAction.GoBack -> backStack.removeLastOrNull()
                is RouterAction.ReplaceWith -> {
                    backStack.removeLastOrNull()
                    backStack.add(action.route)
                }
                is RouterAction.PopToRoute -> {
                    val targetIndex = backStack.indexOfLast { it == action.route }
                    if (targetIndex >= 0) {
                        val removeUntil = if (action.inclusive) targetIndex else targetIndex + 1
                        while (backStack.size > removeUntil) {
                            backStack.removeLastOrNull()
                        }
                    }
                }
                is RouterAction.ClearStackAndNavigate -> {
                    backStack.clear()
                    backStack.add(action.route)
                }
            }
        }
    }
}
