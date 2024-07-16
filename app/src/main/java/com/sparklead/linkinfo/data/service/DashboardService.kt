package com.sparklead.linkinfo.data.service

import com.sparklead.linkinfo.data.dto.DashboardDto

interface DashboardService {

    suspend fun getDashboardData(): DashboardDto

}