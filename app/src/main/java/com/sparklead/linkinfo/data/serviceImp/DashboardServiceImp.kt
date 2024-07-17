package com.sparklead.linkinfo.data.serviceImp

import com.sparklead.linkinfo.common.utils.Constants
import com.sparklead.linkinfo.data.datastore.PrefManager
import com.sparklead.linkinfo.data.dto.DashboardDto
import com.sparklead.linkinfo.data.remote.HttpRoutes
import com.sparklead.linkinfo.data.service.DashboardService
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.flow.first

class DashboardServiceImp(private val client: HttpClient, private val prefManager: PrefManager) :
    DashboardService {

    override suspend fun getDashboardData(): DashboardDto {
        return try {
            client.get {
                url(HttpRoutes.GET_DASHBOARD_DATA)
                header(
                    Constants.AUTH_HEADER,
                    prefManager.readStringValue(Constants.HEADER_TOKEN).first()
                )
                contentType(ContentType.Application.Json)
            }
        } catch (exception: Exception) {
            throw exception
        }
    }

}