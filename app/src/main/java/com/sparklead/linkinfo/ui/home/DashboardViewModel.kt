package com.sparklead.linkinfo.ui.home

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sparklead.linkinfo.R
import com.sparklead.linkinfo.common.Resource
import com.sparklead.linkinfo.common.utils.Constants
import com.sparklead.linkinfo.data.datastore.PrefManager
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
    private val getDashboardDataUseCase: GetDashboardDataUseCase,
    private val prefManager: PrefManager
) : ViewModel() {

    private val _dashboardUiState = MutableStateFlow<DashboardUiState>(DashboardUiState.Loading)
    val dashboardUiState = _dashboardUiState.asStateFlow()

    private val _analyticsDataList = MutableStateFlow<List<AnalyticsData>>(emptyList())
    val analyticsDataList = _analyticsDataList.asStateFlow()

    private val _analyticsGraphData = MutableStateFlow<AnalyticsGraphData>(
        AnalyticsGraphData(
            emptyList(), emptyList(), emptyList(), Pair("", "")
        )
    )
    val analyticsGraphData = _analyticsGraphData.asStateFlow()

    // function to save token
    fun saveToken() = viewModelScope.launch(Dispatchers.IO) {
        prefManager.saveStringValue(Constants.HEADER_TOKEN, Constants.HEADER_TOKEN_KEY)
    }

    // function to get dashboard details
    fun getDashboardDetails() = viewModelScope.launch(Dispatchers.IO) {
        getDashboardDataUseCase().collect { result ->
            when (result) {
                is Resource.Error -> _dashboardUiState.value =
                    DashboardUiState.Error(R.string.failed_to_load_detail)


                is Resource.Loading -> _dashboardUiState.value = DashboardUiState.Loading

                is Resource.Success -> result.data?.let {
                    _dashboardUiState.value = DashboardUiState.DashboardDetails(it)
                    mapAnalyticsGraphData(it.data.overall_url_chart)
                    mapAnalyticsList(it.today_clicks.toString(), it.top_location, it.top_source)
                }
            }
        }
    }

    // function to get analytics lists
    private fun mapAnalyticsList(todayClicks: String, topLocation: String, topSource: String) {
        val tempList = mutableListOf<AnalyticsData>()

        tempList.add(
            AnalyticsData(
                R.string.todays_clicks,
                todayClicks,
                R.drawable.ic_total_click,
                Color(0xffded9eb)
            )
        )

        tempList.add(
            AnalyticsData(
                R.string.top_location,
                topLocation,
                R.drawable.ic_pin,
                Color(0xffe2edff)
            )
        )

        tempList.add(
            AnalyticsData(
                R.string.top_source,
                topSource,
                R.drawable.ic_globe,
                Color(0xffffe9ec)
            )
        )
        _analyticsDataList.value = tempList
    }

    // function to get analytics graph data
    private fun mapAnalyticsGraphData(overallUrlChart: Map<String, Int>) {
        val xData = mutableListOf<Float>()
        val xValue = mutableListOf<String>()
        val yData = mutableListOf<Float>()
        var start = "0"
        var end = "0"
        overallUrlChart.keys.forEachIndexed { index, key ->
            if (index == 0) {
                start = key
            }
            if (index == overallUrlChart.size - 1) {
                end = key
            }
            xData.add(index.toFloat())
            xValue.add(key)
            overallUrlChart[key]?.let { yData.add(it.toFloat()) }
        }
        _analyticsGraphData.value =
            AnalyticsGraphData(xData = xData, xValue = xValue, yData = yData, Pair(start, end))
    }
}