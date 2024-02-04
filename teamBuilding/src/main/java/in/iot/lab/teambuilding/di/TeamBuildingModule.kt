package `in`.iot.lab.teambuilding.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import `in`.iot.lab.qrcode.installer.ModuleInstaller
import `in`.iot.lab.qrcode.scanner.QrCodeScanner


@Module
@InstallIn(SingletonComponent::class)
object TeamBuildingModule {


    @Provides
    fun provideQrCodeScanner(@ApplicationContext context: Context): QrCodeScanner {
        return QrCodeScanner(context)
    }

    @Provides
    fun providesModuleInstaller(@ApplicationContext context: Context): ModuleInstaller {
        return ModuleInstaller(context)
    }
}