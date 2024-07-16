package com.sparklead.linkinfo.domain.repository

import com.sparklead.linkinfo.data.dto.DashboardDto

interface DashboardRepository {

    suspend fun getDashboardData(): DashboardDto

}