package com.homedev.android.nowcapture.common.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider

/**
 * Created by jaehyunpark on 2018. 10. 31..
 */
class DaggerViewModelFactory() : ViewModelProvider.Factory {
    private lateinit var creators: Map<Class<out ViewModel>, Provider<ViewModel>>

    @Inject
    constructor(creators: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>) : this() {
        this.creators = creators
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        var creator: Provider<out ViewModel>? = creators[modelClass]

        creator!!.apply {
            for ((key, value) in creators) {
                if (modelClass.isAssignableFrom(key)) {
                    creator = value
                    break
                }
            }
        }

        return creator!!.get() as T
    }
}