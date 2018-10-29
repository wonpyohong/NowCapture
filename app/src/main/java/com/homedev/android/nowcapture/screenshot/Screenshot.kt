package com.homedev.android.nowcapture.screenshot

import android.graphics.Bitmap
import android.view.View


object Screenshot {

    fun takescreenshot(v: View): Bitmap {
        v.isDrawingCacheEnabled = true
        v.buildDrawingCache(true)
        val b = Bitmap.createBitmap(v.getDrawingCache())
        v.isDrawingCacheEnabled = false
        return b
    }

    fun takeScreenshotOfRootView(v: View): Bitmap {
        return takescreenshot(v.rootView)
    }

}