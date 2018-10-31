package com.homedev.android.nowcapture.ui

import com.homedev.android.nowcapture.capture.CaptureHelper
import com.homedev.android.nowcapture.common.viewmodel.BaseViewModel
import javax.inject.Inject

/**
 * Created by jaehyunpark on 2018. 10. 31..
 */
class MainViewModel() : BaseViewModel() {
    private lateinit var captureHelper: CaptureHelper

    @Inject
    constructor(captureHelper: CaptureHelper) : this() {
        this.captureHelper = captureHelper
    }
}