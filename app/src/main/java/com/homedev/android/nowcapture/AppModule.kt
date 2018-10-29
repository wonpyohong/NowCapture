package com.homedev.android.nowcapture

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by jaehyunpark on 2018. 10. 29..
 */
@Module
class AppModule {
    @Provides
    @Singleton
    fun provideAppContext(application: Application): Context = application.applicationContext
}