package com.sparklead.linkinfo.data.repository

import com.sparklead.linkinfo.data.dto.DashboardDto
import com.sparklead.linkinfo.data.service.DashboardService
import com.sparklead.linkinfo.domain.repository.DashboardRepository
import javax.inject.Inject

class DashboardRepositoryImp @Inject constructor(private val dashboardService: DashboardService) :
    DashboardRepository {

    override suspend fun getDashboardData(): DashboardDto {
        return dashboardService.getDashboardData()
    }

}