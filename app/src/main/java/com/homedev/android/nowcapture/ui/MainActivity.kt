package com.homedev.android.nowcapture.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.homedev.android.nowcapture.R
import com.homedev.android.nowcapture.capture.CaptureHelper
import kotlinx.android.synthetic.main.activity_main.*
import com.homedev.android.nowcapture.support.PermissionHelper
import android.provider.Settings.ACTION_MANAGE_OVERLAY_PERMISSION
import android.content.Intent
import android.net.Uri
import android.provider.Settings.canDrawOverlays
import android.os.Build
import android.provider.Settings
import android.view.View
import android.widget.Toast
import com.homedev.android.nowcapture.NowService
import com.homedev.android.nowcapture.support.OverdrawPermssionHelper

class MainActivity : AppCompatActivity() {
    val captureHelper = CaptureHelper()
    val permissionHelper = PermissionHelper(this)
    val overdrawPermissionHelper = OverdrawPermssionHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        permissionHelper.requestPermssionIfNeed()
        overdrawPermissionHelper.startCheck()

        screenShotButton.setOnClickListener {
            val imageFile = captureHelper.takeScreenshot(window)
            captureHelper.scanImageFiles(imageFile) { captureHelper.openScreenshot(imageFile)}
        }

        startfloatingButton.setOnClickListener {
            startService(Intent(this@MainActivity, NowService::class.java))
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == overdrawPermissionHelper.CODE_DRAW_OVER_OTHER_APP_PERMISSION) {
            overdrawPermissionHelper.onActivityResult(resultCode)
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}
