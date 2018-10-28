package com.homedev.android.nowcapture

import android.app.Application
import android.content.Context
import com.facebook.stetho.Stetho

class NowApplication: Application() {
    init {
        instance = this
    }

    companion object {
        private var instance: NowApplication? = null

        val applicationContext: Context
            get() = instance!!.applicationContext
    }

    override fun onCreate() {
        super.onCreate()

        Stetho.initializeWithDefaults(this)
    }
}