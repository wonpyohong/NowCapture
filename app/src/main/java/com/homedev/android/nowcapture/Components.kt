package com.homedev.android.nowcapture

import android.content.Context

/**
 * Created by jaehyunpark on 2018. 10. 29..
 */
object Components {
    private lateinit var component: AppComponent

    fun set(appComponent: AppComponent) {
        this.component = appComponent
    }

    fun getAppContext(): Context {
        return component.getAppContext()
    }
}