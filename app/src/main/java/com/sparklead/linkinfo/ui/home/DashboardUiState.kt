package com.sparklead.linkinfo.ui.home

import com.sparklead.linkinfo.data.dto.DashboardDto

sealed class DashboardUiState {

    data object Loading : DashboardUiState()

    data class Error(val message: Int) : DashboardUiState()

    data class DashboardDetails(val details: DashboardDto) : DashboardUiState()
}