package com.earratea.nav3kit.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Router @Inject constructor() {

    private val _commands = Channel<RouterAction>(Channel.UNLIMITED)
    val commands: ReceiveChannel<RouterAction> = _commands

    private val resultListeners = ConcurrentHashMap<String, (Any) -> Unit>()

    fun navigateTo(route: NavKey) {
        _commands.trySend(RouterAction.NavigateTo(route))
    }

    fun goBack() {
        _commands.trySend(RouterAction.GoBack)
    }

    fun replaceWith(route: NavKey) {
        _commands.trySend(RouterAction.ReplaceWith(route))
    }

    fun popToRoute(route: NavKey, inclusive: Boolean = false) {
        _commands.trySend(RouterAction.PopToRoute(route, inclusive))
    }

    fun clearStackAndNavigate(route: NavKey) {
        _commands.trySend(RouterAction.ClearStackAndNavigate(route))
    }

    fun <T : Any> sendResult(key: String, data: T) {
        (resultListeners.remove(key) as? (T) -> Unit)?.invoke(data)
    }

    fun <T : Any> setResultListener(key: String, listener: (T) -> Unit) {
        @Suppress("UNCHECKED_CAST")
        resultListeners[key] = listener as (Any) -> Unit
    }

    fun removeResultListener(key: String) {
        resultListeners.remove(key)
    }
}
