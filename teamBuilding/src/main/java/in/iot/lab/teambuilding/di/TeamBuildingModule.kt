package `in`.iot.lab.teambuilding.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import `in`.iot.lab.qrcode.installer.ModuleInstaller
import `in`.iot.lab.qrcode.scanner.QrCodeScanner
import `in`.iot.lab.teambuilding.data.remote.TeamBuildingApiService
import `in`.iot.lab.teambuilding.data.repo.TeamBuildingRepo
import `in`.iot.lab.teambuilding.data.repo.TeamBuildingRepoDummy
import `in`.iot.lab.teambuilding.data.repo.TeamBuildingRepoImpl
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object TeamBuildingModule {


    /**
     * Provides the [QrCodeScanner] object used to scan QR Codes
     */
    @Provides
    fun provideQrCodeScanner(@ApplicationContext context: Context): QrCodeScanner {
        return QrCodeScanner(context)
    }


    /**
     * Provides the [ModuleInstaller] object used to download extra modules from the play store. In
     * this case we are downloading the [QrCodeScanner] module
     */
    @Provides
    fun providesModuleInstaller(@ApplicationContext context: Context): ModuleInstaller {
        return ModuleInstaller(context)
    }


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
    @Named("production")
    fun providesTeamBuildingRepo(teamBuildingRepoImpl: TeamBuildingRepoImpl): TeamBuildingRepo {
        return teamBuildingRepoImpl
    }


    /**
     * This provides the dummy repository implementation [TeamBuildingRepoDummy] which helps
     * in testing.
     */
    @Provides
    @Singleton
    @Named("testing")
    fun providesTeamBuildingRepoDummy(): TeamBuildingRepo {
        return TeamBuildingRepoDummy()
    }
}