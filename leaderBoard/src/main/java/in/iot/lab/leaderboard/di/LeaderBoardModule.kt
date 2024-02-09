package `in`.iot.lab.leaderboard.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import `in`.iot.lab.leaderboard.data.remote.LeaderBoardApiService
import `in`.iot.lab.leaderboard.data.repo.LeaderBoardRepo
import `in`.iot.lab.leaderboard.data.repo.LeaderBoardRepoImpl
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object LeaderBoardModule {


    @Provides
    @Singleton
    fun providesLeaderBoardApi(retrofit: Retrofit): LeaderBoardApiService {
        return retrofit.create(LeaderBoardApiService::class.java)
    }


    @Provides
    @Singleton
    fun providesLeaderBoardRepo(api: LeaderBoardApiService): LeaderBoardRepo {
        return LeaderBoardRepoImpl(api)
    }
}