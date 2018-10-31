package com.homedev.android.nowcapture.ui

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import com.homedev.android.nowcapture.NowService
import com.homedev.android.nowcapture.R
import com.homedev.android.nowcapture.capture.CaptureHelper
import com.homedev.android.nowcapture.common.viewmodel.DaggerViewModelFactory
import com.homedev.android.nowcapture.common.viewmodel.ViewModelKey
import com.homedev.android.nowcapture.support.OverdrawPermssionHelper
import com.homedev.android.nowcapture.support.PermissionHelper
import dagger.Binds
import dagger.android.support.DaggerAppCompatActivity
import dagger.multibindings.IntoMap
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainActivity : DaggerAppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: DaggerViewModelFactory

    private lateinit var viewModel: MainViewModel
    
    val captureHelper = CaptureHelper()
    val permissionHelper = PermissionHelper(this)
    val overdrawPermissionHelper = OverdrawPermssionHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bindViewModel()

        permissionHelper.requestPermssionIfNeed()
        overdrawPermissionHelper.startCheck()

        screenShotButton.setOnClickListener {
            val imageFile = captureHelper.capture(window.decorView.rootView)
            captureHelper.scanImageFiles(imageFile) {
                captureHelper.openScreenshot(imageFile)
            }
        }

        startfloatingButton.setOnClickListener {
            startService(Intent(this@MainActivity, NowService::class.java))
            finish()
        }

        getActionSendAppList.setOnClickListener {
            val imageFile = captureHelper.capture(window.decorView.rootView)
            captureHelper.scanImageFiles(imageFile) {
                captureHelper.requestAppActionSendImage(imageFile, packageManager)
            }
        }
    }

    private fun bindViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory)[MainViewModel::class.java]
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == overdrawPermissionHelper.CODE_DRAW_OVER_OTHER_APP_PERMISSION) {
            overdrawPermissionHelper.onActivityResult(resultCode)
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    @dagger.Module
    abstract inner class Module {
        @Binds
        @IntoMap
        @ViewModelKey(MainViewModel::class)
        abstract fun provideViewModel(viewModel: MainViewModel): ViewModel
    }
}