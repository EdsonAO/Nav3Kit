package com.earratea.nav3kit.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

interface RouterEffectEmitter {
    val routerState: StateFlow<RouterAction>
    fun navigateTo(route: NavKey)
    fun goBack()
    fun onNavigationHandled()
}

class RouterEffectEmitterDelegate : RouterEffectEmitter {
    private val _routerState = MutableStateFlow<RouterAction>(RouterAction.None)
    override val routerState: StateFlow<RouterAction>
        get() = _routerState.asStateFlow()

    override fun navigateTo(route: NavKey) {
        executeRouterAction(RouterAction.NavigateTo(route))
    }

    override fun goBack() {
        executeRouterAction(RouterAction.GoBack)
    }

    override fun onNavigationHandled() {
        executeRouterAction(RouterAction.None)
    }

    private fun executeRouterAction(action: RouterAction) {
        _routerState.update { action }
    }
}
