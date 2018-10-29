package com.homedev.android.nowcapture

import android.annotation.SuppressLint
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.graphics.PixelFormat
import android.view.*
import android.widget.ImageView
import android.view.MotionEvent
import com.homedev.android.nowcapture.ui.MainActivity


class NowService: Service() {
    val floatingButtonHandler = FloatingButtonHandler(this)

    override fun onBind(intent: Intent?): IBinder? = null

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate() {
        super.onCreate()

        floatingButtonHandler.setUp()
    }

    override fun onDestroy() {
        super.onDestroy()

        floatingButtonHandler.close()
    }
}