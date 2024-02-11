package `in`.iot.lab.credits.di


import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import `in`.iot.lab.credits.data.remote.AboutUsApiService
import `in`.iot.lab.credits.data.repo.AboutUsRepo
import `in`.iot.lab.credits.data.repo.AboutUsRepoImpl
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AboutUsModule {


    @Provides
    @Singleton
    fun providesAboutUsApiService(retrofit: Retrofit): AboutUsApiService {
        return retrofit.create(AboutUsApiService::class.java)
    }

    @Provides
    @Singleton
    fun providesAboutUsRepo(aboutUsApiService: AboutUsApiService): AboutUsRepo {
        return AboutUsRepoImpl(aboutUsApiService)
    }
}