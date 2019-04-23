package com.truongpx.ckcportfolio.platform

import com.truongpx.ckcportfolio.AndroidApplication
import com.truongpx.corelibrary.di.ApplicationComponent
import com.truongpx.corelibrary.platform.AbstractFragment

open class BaseFragment : AbstractFragment() {
    val appComponent: ApplicationComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        (activity?.application as AndroidApplication).appComponent
    }
}