package com.homedev.android.nowcapture

import android.app.Application
import android.content.Context
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * Created by jaehyunpark on 2018. 10. 29..
 */
@Singleton
@Component(modules = [(AppModule::class), (AndroidSupportInjectionModule::class)])
interface AppComponent : AndroidInjector<NowApplication> {

    fun getAppContext(): Context

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(app: Application): Builder

        fun build(): AppComponent
    }
}