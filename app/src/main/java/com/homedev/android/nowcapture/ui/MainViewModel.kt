package com.homedev.android.nowcapture.ui

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.LiveDataReactiveStreams
import android.content.pm.PackageManager
import com.homedev.android.nowcapture.capture.openScreenshot
import com.homedev.android.nowcapture.capture.requestAppActionSendImage
import com.homedev.android.nowcapture.capture.scanImageFiles
import com.homedev.android.nowcapture.common.viewmodel.BaseViewModel
import io.reactivex.processors.PublishProcessor
import java.io.File
import javax.inject.Inject

/**
 * Created by jaehyunpark on 2018. 10. 31..
 */


class MainViewModel @Inject constructor() : BaseViewModel() {
    private val actionSignal: PublishProcessor<ACTION> = PublishProcessor.create()

    fun getActionSignal(): LiveData<ACTION> {
        return LiveDataReactiveStreams.fromPublisher(actionSignal)
    }

    fun requestImageCapture(imageFile: File) {
        scanImageFiles(imageFile) {
            openScreenshot(imageFile)
        }

        actionSignal.onNext(ACTION.IMAGE_CAPTURE)
    }

    fun requestImageShare(imageFile: File, packageManager: PackageManager) {
        scanImageFiles(imageFile) {
            requestAppActionSendImage(imageFile, packageManager)
        }

        actionSignal.onNext(ACTION.IMAGE_SHARE)
    }

    enum class ACTION {
        IMAGE_CAPTURE,
        IMAGE_SHARE
    }
}