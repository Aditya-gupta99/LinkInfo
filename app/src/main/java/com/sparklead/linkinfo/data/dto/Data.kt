package com.sparklead.linkinfo.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class Data(
    val favourite_links: List<String?>,

    val overall_url_chart: Map<String,Int>,

    val recent_links: List<RecentLink>,

    val top_links: List<TopLink>
)
