package com.sparklead.linkinfo.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun HomeScreen(
    viewModel : HomeViewModel = hiltViewModel()
) {
    val state by viewModel.homeUiState.collectAsStateWithLifecycle()

    HomeScreen(
        state = state
    )

}

@Composable
fun HomeScreen(
    state: HomeUiState
) {

}

@Preview
@Composable
private fun HomeScreenPreview() {

}