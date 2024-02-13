package `in`.iot.lab.teambuilding.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import `in`.iot.lab.teambuilding.data.remote.TeamBuildingApiService
import `in`.iot.lab.teambuilding.data.repo.TeamBuildingRepo
import `in`.iot.lab.teambuilding.data.repo.TeamBuildingRepoImpl
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object TeamBuildingModule {


    /**
     * Provides [TeamBuildingApiService], retrofit instance which is used for team building feature's
     * REST api calls
     */
    @Provides
    @Singleton
    fun providesTeamBuildingApiService(retrofit: Retrofit): TeamBuildingApiService {
        return retrofit.create(TeamBuildingApiService::class.java)
    }


    /**
     * This is the repository layer implementation which helps to fetch data and it stays as the
     * single source of truth.
     */
    @Provides
    @Singleton
    fun providesTeamBuildingRepo(teamBuildingRepoImpl: TeamBuildingRepoImpl): TeamBuildingRepo {
        return teamBuildingRepoImpl
    }
}