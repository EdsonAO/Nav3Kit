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

    private val resultListeners = ConcurrentHashMap<String, (ResultValue) -> Unit>()

    fun navigateTo(route: NavKey) {
        _commands.trySend(RouterAction.NavigateTo(route))
    }

    fun goBack(results: ResultBuilder.() -> Unit = {}) {
        ResultBuilder(resultListeners).apply(results)
        _commands.trySend(RouterAction.GoBack)
    }

    fun replaceWith(route: NavKey, results: ResultBuilder.() -> Unit = {}) {
        ResultBuilder(resultListeners).apply(results)
        _commands.trySend(RouterAction.ReplaceWith(route))
    }

    fun popToRoute(route: NavKey, inclusive: Boolean = false, results: ResultBuilder.() -> Unit = {}) {
        ResultBuilder(resultListeners).apply(results)
        _commands.trySend(RouterAction.PopToRoute(route, inclusive))
    }

    fun clearStackAndNavigate(route: NavKey) {
        _commands.trySend(RouterAction.ClearStackAndNavigate(route))
    }

    fun <T : ResultValue> expectResult(key: ResultKey<T>, listener: (T) -> Unit) {
        @Suppress("UNCHECKED_CAST")
        resultListeners[key.key] = listener as (ResultValue) -> Unit
    }

    fun dismissResult(key: ResultKey<*>) {
        resultListeners.remove(key.key)
    }
}

class ResultBuilder(private val listeners: ConcurrentHashMap<String, (ResultValue) -> Unit>) {
    fun <T : ResultValue> result(key: ResultKey<T>, data: T) {
        listeners[key.key]?.invoke(data)
    }
}
