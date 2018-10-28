package com.homedev.android.nowcapture

import android.app.Application
import android.content.Context

class NowApplication: Application() {
    init {
        instance = this
    }

    companion object {
        private var instance: NowApplication? = null

        val applicationContext: Context
            get() = instance!!.applicationContext
    }
}