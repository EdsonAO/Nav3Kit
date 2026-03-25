package com.earratea.nav3kit.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.earratea.nav3kit.ui.theme.Nav3KitTheme

@Composable
fun HomeScreen(
    onEvent: (HomeContract.Event) -> Unit,
) {
    HomeContent(onEvent = onEvent)
}

@Composable
private fun HomeContent(
    onEvent: (HomeContract.Event) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "Home",
            style = MaterialTheme.typography.headlineLarge,
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = { onEvent(HomeContract.Event.OnNavigateToDetail) },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(text = "Go to Detail")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { onEvent(HomeContract.Event.OnNavigateToProfile) },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(text = "Go to Profile")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    Nav3KitTheme {
        HomeContent(onEvent = {})
    }
}
