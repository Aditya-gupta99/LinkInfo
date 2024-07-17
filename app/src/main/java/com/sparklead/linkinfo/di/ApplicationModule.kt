package com.sparklead.linkinfo.di

import com.sparklead.linkinfo.data.datastore.PrefManager
import com.sparklead.linkinfo.data.repository.DashboardRepositoryImp
import com.sparklead.linkinfo.data.service.DashboardService
import com.sparklead.linkinfo.data.serviceImp.DashboardServiceImp
import com.sparklead.linkinfo.domain.repository.DashboardRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.features.HttpTimeout
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logging
import io.ktor.client.features.websocket.WebSockets
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient {
        return HttpClient(Android) {
            install(WebSockets)
            install(JsonFeature) {
                serializer = KotlinxSerializer(Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                })
            }
            install(HttpTimeout) {
                requestTimeoutMillis = 15000
            }
            install(Logging) {
                level = LogLevel.ALL
            }
        }
    }

    @Provides
    @Singleton
    fun providesDashboardService(client: HttpClient, prefManager: PrefManager): DashboardService =
        DashboardServiceImp(client, prefManager)

    @Provides
    fun providesDashboardRepository(dashboardService: DashboardService): DashboardRepository =
        DashboardRepositoryImp(dashboardService)
}