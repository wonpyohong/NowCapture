package com.homedev.android.nowcapture.support

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.homedev.android.nowcapture.NowService
import kotlinx.android.synthetic.main.activity_main.*

class OverdrawPermssionHelper(val activity: Activity) {
    val CODE_DRAW_OVER_OTHER_APP_PERMISSION = 2084

    fun startCheck() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(activity)) {
            val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:${activity.packageName}"))
            activity.startActivityForResult(intent, CODE_DRAW_OVER_OTHER_APP_PERMISSION)
        }
    }

    fun onActivityResult(resultCode: Int) {
        if (resultCode != AppCompatActivity.RESULT_OK) {
            Toast.makeText(activity,
                    "Draw over other app permission not available. Closing the application",
                    Toast.LENGTH_SHORT).show()

            activity.finish()
        }
    }
}