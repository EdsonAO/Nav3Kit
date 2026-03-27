package com.earratea.nav3kit.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.earratea.nav3kit.screens.home.HomeRoute

@Composable
fun AppHostNav(
    router: Router,
    entryScopes: Set<EntryProviderInstaller>,
    modifier: Modifier = Modifier
) {
    val backStack = rememberNavBackStack(HomeRoute)

    RouterEffect(router = router, backStack = backStack)

    NavDisplay(
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = entryProvider {
            entryScopes.forEach { builder ->
                this.builder()
            }
        },
        modifier = modifier
    )
}
