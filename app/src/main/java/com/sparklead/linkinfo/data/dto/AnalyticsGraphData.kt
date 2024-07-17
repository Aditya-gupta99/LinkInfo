package com.sparklead.linkinfo.data.dto

data class AnalyticsGraphData(
    val xData : List<Float>,
    val xValue : List<String>,
    val yData : List<Float>,
    val span : Pair<String,String>
)
