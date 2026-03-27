package com.earratea.nav3kit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.earratea.nav3kit.navigation.AppHostNav
import com.earratea.nav3kit.navigation.EntryProviderInstaller
import com.earratea.nav3kit.navigation.Router
import com.earratea.nav3kit.ui.theme.Nav3KitTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var entryProviderScopes: Set<@JvmSuppressWildcards EntryProviderInstaller>

    @Inject
    lateinit var router: Router

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Nav3KitTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppHostNav(
                        router = router,
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
