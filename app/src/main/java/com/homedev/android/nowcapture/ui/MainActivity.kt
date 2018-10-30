package com.homedev.android.nowcapture.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.homedev.android.nowcapture.R
import com.homedev.android.nowcapture.capture.CaptureHelper
import kotlinx.android.synthetic.main.activity_main.*
import com.homedev.android.nowcapture.support.PermissionHelper
import android.content.Intent
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
            captureHelper.scanImageFiles(imageFile) {
                captureHelper.openScreenshot(imageFile)
            }
        }

        startfloatingButton.setOnClickListener {
            startService(Intent(this@MainActivity, NowService::class.java))
            finish()
        }

        getActionSendAppList.setOnClickListener {
            val imageFile = captureHelper.takeScreenshot(window)
            captureHelper.scanImageFiles(imageFile) {
                captureHelper.requestAppActionSendImage(imageFile, packageManager)
            }
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
