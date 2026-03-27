package com.earratea.nav3kit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.earratea.nav3kit.navigation.AppHostNav
import com.earratea.nav3kit.navigation.EntryProviderInstaller
import com.earratea.nav3kit.screens.detail.DetailScreen
import com.earratea.nav3kit.screens.home.HomeScreen
import com.earratea.nav3kit.screens.profile.ProfileScreen
import com.earratea.nav3kit.ui.theme.Nav3KitTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var entryProviderScopes: Set<@JvmSuppressWildcards EntryProviderInstaller>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Nav3KitTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppHostNav(
                        entryScopes = entryProviderScopes,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    )
                }
            }
        }
    }
}
