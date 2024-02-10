package `in`.iot.lab.playgame.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import `in`.iot.lab.playgame.data.remote.PlayApiService
import `in`.iot.lab.playgame.data.repo.PlayRepo
import `in`.iot.lab.playgame.data.repo.PlayRepoImpl
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object PlayModule {


    /**
     * This provides the [Retrofit] api service instance which is used to make REST api calls.
     */
    @Provides
    @Singleton
    fun providesPlayApiService(retrofit: Retrofit): PlayApiService {
        return retrofit.create(PlayApiService::class.java)
    }


    /**
     * This provides the repository layer object which is responsible for handling and fetching
     * data.
     */
    @Provides
    @Singleton
    fun providePlayRepo(apiService: PlayApiService): PlayRepo {
        return PlayRepoImpl(apiService)
    }
}