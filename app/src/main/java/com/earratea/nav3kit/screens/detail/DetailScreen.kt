package com.earratea.nav3kit.screens.detail

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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.compose.dropUnlessResumed
import com.earratea.nav3kit.ui.theme.Nav3KitTheme

@Composable
fun DetailScreen(viewModel: DetailViewModel) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    DetailContent(uiState = uiState, onEvent = viewModel::handleEvent)
}

@Composable
private fun DetailContent(
    uiState: DetailUiState,
    onEvent: (DetailEvent) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "Detail",
            style = MaterialTheme.typography.headlineLarge,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "ID: ${uiState.id}",
            style = MaterialTheme.typography.bodyLarge,
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = dropUnlessResumed { onEvent(DetailEvent.GoBackClicked) },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(text = "Go Back")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DetailScreenPreview() {
    Nav3KitTheme {
        DetailContent(uiState = DetailUiState(id = "42"), onEvent = {})
    }
}
