package com.homedev.android.nowcapture

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    val captureHelper = CaptureHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        screenShotButton.setOnClickListener {
            captureHelper.takeScreenshot(window)
        }
    }


}
