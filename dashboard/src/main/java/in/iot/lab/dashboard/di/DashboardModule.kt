package `in`.iot.lab.dashboard.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import `in`.iot.lab.dashboard.data.remote.TeamApiService
import `in`.iot.lab.dashboard.data.repository.DashboardRepository
import `in`.iot.lab.dashboard.data.repository.DashboardRepositoryImpl
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object DashboardModule {

    @Provides
    fun provideTeamApiService(retrofit: Retrofit): TeamApiService {
        return retrofit.create(TeamApiService::class.java)
    }

    @Provides
    fun provideDashboardRepository(impl: DashboardRepositoryImpl): DashboardRepository {
        return impl
    }
}