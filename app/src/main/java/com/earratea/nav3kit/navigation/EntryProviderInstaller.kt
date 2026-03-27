package com.earratea.nav3kit.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey

typealias EntryProviderInstaller = EntryProviderScope<NavKey>.(backStack: NavBackStack<NavKey>) -> Unit