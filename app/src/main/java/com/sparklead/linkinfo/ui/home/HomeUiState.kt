package com.sparklead.linkinfo.ui.home

sealed class HomeUiState {

    data object Loading : HomeUiState()

    data class Error(val message: Int) : HomeUiState()

    data class Success(val list: List<String>) : HomeUiState()
}