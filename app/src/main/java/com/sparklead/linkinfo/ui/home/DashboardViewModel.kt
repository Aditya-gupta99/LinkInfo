package com.sparklead.linkinfo.ui.home

import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sparklead.linkinfo.R
import com.sparklead.linkinfo.common.Resource
import com.sparklead.linkinfo.data.dto.AnalyticsData
import com.sparklead.linkinfo.data.dto.AnalyticsGraphData
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

    private val _analyticsDataList = MutableStateFlow<List<AnalyticsData>>(emptyList())
    val analyticsDataList = _analyticsDataList.asStateFlow()

    private val _analyticsGraphData = MutableStateFlow<AnalyticsGraphData>(
        AnalyticsGraphData(
            emptyList(), emptyList(), ""
        )
    )
    val analyticsGraphData = _analyticsGraphData.asStateFlow()

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
                    mapAnalyticsGraphData(it.data.overall_url_chart)
                    mapAnalyticsList(it.today_clicks.toString(), it.top_location, it.top_source)
                }
            }
        }
    }

    private fun mapAnalyticsList(todayClicks: String, topLocation: String, topSource: String) {
        val tempList = mutableListOf<AnalyticsData>()

        tempList.add(
            AnalyticsData(
                "Today's clicks",
                todayClicks,
                R.drawable.ic_total_click,
                Color(0xffded9eb)
            )
        )

        tempList.add(
            AnalyticsData(
                "Top Location",
                topLocation,
                R.drawable.ic_pin,
                Color(0xffe2edff)
            )
        )

        tempList.add(
            AnalyticsData(
                "Top Source",
                topSource,
                R.drawable.ic_globe,
                Color(0xffffe9ec)
            )
        )
        _analyticsDataList.value = tempList
    }

    private fun mapAnalyticsGraphData(overallUrlChart: Map<String,Int>) {

    }
}