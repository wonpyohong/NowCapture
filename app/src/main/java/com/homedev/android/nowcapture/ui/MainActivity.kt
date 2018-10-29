package com.homedev.android.nowcapture.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.homedev.android.nowcapture.R
import com.homedev.android.nowcapture.capture.CaptureHelper
import kotlinx.android.synthetic.main.activity_main.*
import com.homedev.android.nowcapture.support.PermissionHelper


class MainActivity : AppCompatActivity() {
    val captureHelper = CaptureHelper()
    val permissionHelper = PermissionHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        permissionHelper.requestPermssionIfNeed()

        screenShotButton.setOnClickListener {
            val imageFile = captureHelper.takeScreenshot(window)
            captureHelper.scanImageFiles(imageFile) { captureHelper.openScreenshot(imageFile)}
        }
    }
}
