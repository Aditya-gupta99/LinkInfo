package com.sparklead.linkinfo.data.serviceImp

import com.sparklead.linkinfo.data.dto.DashboardDto
import com.sparklead.linkinfo.data.remote.HttpRoutes
import com.sparklead.linkinfo.data.service.DashboardService
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType

class DashboardServiceImp(private val client: HttpClient) : DashboardService {

    override suspend fun getDashboardData(): DashboardDto {
        return try {
            client.get {
                url(HttpRoutes.GET_DASHBOARD_DATA)
                header(
                    "Authorization",
                    "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MjU5MjcsImlhdCI6MTY3NDU1MDQ1MH0.dCkW0ox8tbjJA2GgUx2UEwNlbTZ7Rr38PVFJevYcXFI"
                )
                contentType(ContentType.Application.Json)
            }
        } catch (exception: Exception) {
            throw exception
        }
    }

}