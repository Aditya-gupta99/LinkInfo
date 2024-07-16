package com.sparklead.linkinfo.domain.usecase

import com.sparklead.linkinfo.common.Resource
import com.sparklead.linkinfo.data.dto.DashboardDto
import com.sparklead.linkinfo.domain.repository.DashboardRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetDashboardDataUseCase @Inject constructor(private val repository: DashboardRepository) {

    suspend operator fun invoke(): Flow<Resource<DashboardDto>> = flow {
        try {
            emit(Resource.Loading())
            val data = repository.getDashboardData()
            emit(Resource.Success(data))
        } catch (exception: Exception) {
            emit(Resource.Error(exception.message.toString()))
        }
    }

}