package com.homedev.android.nowcapture

import com.facebook.stetho.Stetho
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class NowApplication : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        val appComponent: AppComponent = DaggerAppComponent.builder().application(this).build()
        Components.set(appComponent)
        return appComponent
    }

    override fun onCreate() {
        super.onCreate()

        initLibraries()
    }

    private fun initLibraries() {
        initSteto()
    }

    private fun initSteto() {
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this)
        }
    }
}