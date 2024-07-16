package com.sparklead.linkinfo.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sparklead.linkinfo.R
import com.sparklead.linkinfo.common.Resource
import com.sparklead.linkinfo.domain.usecase.GetDashboardDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getDashboardDataUseCase: GetDashboardDataUseCase
) : ViewModel() {

    private val _dashboardUiState = MutableStateFlow<DashboardUiState>(DashboardUiState.Loading)
    val dashboardUiState = _dashboardUiState.asStateFlow()

    fun getDashboardDetails() = viewModelScope.launch(Dispatchers.IO) {
        getDashboardDataUseCase().collect { result ->
            when (result) {
                is Resource.Error -> {
                    _dashboardUiState.value =
                        DashboardUiState.Error(R.string.failed_to_load_detail)
                    Log.e("DashboardViewModel", "Error: ${result.message}")
                }

                is Resource.Loading -> _dashboardUiState.value = DashboardUiState.Loading

                is Resource.Success -> result.data?.let {
                    _dashboardUiState.value = DashboardUiState.DashboardDetails(it)
                }
            }
        }
    }
}