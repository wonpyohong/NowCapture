package com.homedev.android.nowcapture.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.gun0912.tedpermission.PermissionListener
import com.homedev.android.nowcapture.NowService
import com.homedev.android.nowcapture.R
import com.homedev.android.nowcapture.common.viewmodel.DaggerViewModelFactory
import com.homedev.android.nowcapture.common.viewmodel.ViewModelKey
import com.homedev.android.nowcapture.support.OverlayPermissionHelper
import com.homedev.android.nowcapture.support.PermissionHelper
import dagger.Binds
import dagger.android.support.DaggerAppCompatActivity
import dagger.multibindings.IntoMap
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import javax.inject.Inject


class MainActivity : DaggerAppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: DaggerViewModelFactory

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bindViewModel()

        observeEvents()

        initClickListeners()

        showPermissions()
    }

    private fun bindViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory)[MainViewModel::class.java]
    }

    private fun observeEvents() {
        viewModel.getActionSignal().observe(this, Observer {
            when (it is MainViewModel.ACTION) {
                it == MainViewModel.ACTION.IMAGE_CAPTURE -> Toast.makeText(this, "캡쳐 완료", Toast.LENGTH_LONG).show()
                it == MainViewModel.ACTION.IMAGE_SHARE -> Toast.makeText(this, "공유 완료", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun initClickListeners() {
        screenShotButton.setOnClickListener {
            viewModel.requestImageCapture(window.decorView.rootView)
        }

        startfloatingButton.setOnClickListener {
            startService(Intent(this, NowService::class.java))
            finish()
        }

        getActionSendAppList.setOnClickListener {
            viewModel.requestImageShare(window.decorView.rootView, packageManager)
        }
    }

    private fun showPermissions() {
        showStoragePermission()
        showOverlayPermission()
    }

    private fun showStoragePermission() {
        PermissionHelper.requestPermissionIfNeed(this, permissionListener)
    }

    private fun showOverlayPermission() {
        OverlayPermissionHelper.requestOverlayPermission(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == OverlayPermissionHelper.CODE_DRAW_OVER_OTHER_APP_PERMISSION) {
            OverlayPermissionHelper.onActivityResult(this, resultCode)
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private val permissionListener = object : PermissionListener {
        override fun onPermissionGranted() {
//            Toast.makeText(this, "권한 허가", Toast.LENGTH_SHORT).show()
        }

        override fun onPermissionDenied(deniedPermissions: ArrayList<String>?) {
//            Toast.makeText(this, "권한 거부\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show()
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