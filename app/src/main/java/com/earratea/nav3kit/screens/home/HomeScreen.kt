package com.earratea.nav3kit.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.compose.dropUnlessResumed
import androidx.lifecycle.compose.dropUnlessStarted
import com.earratea.nav3kit.ui.theme.Nav3KitTheme

@Composable
fun HomeScreen(viewModel: HomeViewModel) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    HomeContent(
        uiState = uiState,
        onEvent = viewModel::handleEvent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeContent(
    uiState: HomeUiState,
    onEvent: (HomeEvent) -> Unit,
) {
    PullToRefreshBox(
        isRefreshing = uiState.isLoading,
        onRefresh = { },
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
            uiState.profileResult?.let { result ->
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Profile result: $result",
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = dropUnlessStarted {
                    onEvent(HomeEvent.GoToDetailClicked)
                },
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(text = "Go to Detail")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = dropUnlessResumed {
                    onEvent(HomeEvent.GoToProfileClicked)
                },
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(text = "Go to Profile")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    Nav3KitTheme {
        HomeContent(uiState = HomeUiState(), onEvent = {})
    }
}
