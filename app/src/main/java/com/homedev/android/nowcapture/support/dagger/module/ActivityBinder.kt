package com.homedev.android.nowcapture.support.dagger.module

import com.homedev.android.nowcapture.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by jaehyunpark on 2018. 10. 31..
 */
@Module
abstract class ActivityBinder {
    @ContributesAndroidInjector(modules = [MainActivity.Module::class])
    abstract fun contributesMainActivity(): MainActivity
}