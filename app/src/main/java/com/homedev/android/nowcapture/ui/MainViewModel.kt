package com.homedev.android.nowcapture.ui

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.LiveDataReactiveStreams
import android.content.pm.PackageManager
import android.view.View
import com.homedev.android.nowcapture.capture.captureImage
import com.homedev.android.nowcapture.capture.shareCaptureImage
import com.homedev.android.nowcapture.common.viewmodel.BaseViewModel
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.processors.PublishProcessor
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by jaehyunpark on 2018. 10. 31..
 */

class MainViewModel @Inject constructor() : BaseViewModel() {
    private val actionSignal: PublishProcessor<ACTION> = PublishProcessor.create()

    fun getActionSignal(): LiveData<ACTION> {
        return LiveDataReactiveStreams.fromPublisher(actionSignal)
    }

    fun requestImageCapture(captureView: View) {
        addDisposable(Completable.fromAction { captureImage(captureView) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { actionSignal.onNext(ACTION.IMAGE_CAPTURE) })
    }


    fun requestImageShare(captureView: View, packageManager: PackageManager) {
        addDisposable(Completable.fromAction { shareCaptureImage(captureView, packageManager) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { actionSignal.onNext(ACTION.IMAGE_SHARE) })
    }

    enum class ACTION {
        IMAGE_CAPTURE,
        IMAGE_SHARE
    }
}