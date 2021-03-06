
package com.truongpx.ckcportfolio

import android.app.Application
import com.truongpx.corelibrary.di.ApplicationComponent
import com.truongpx.corelibrary.di.ApplicationModule
import com.truongpx.corelibrary.di.DaggerApplicationComponent
import com.truongpx.corelibrary.di.RoomModule
import com.truongpx.ckcportfolio.features.basecrypto.datalayer.workmanager.MyWorkerManager


class AndroidApplication : Application() {

    val appComponent: ApplicationComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        DaggerApplicationComponent
            .builder().roomModule(RoomModule(this)).applicationModule(
                ApplicationModule(
                    this
                )
            )
            .build()
    }



    override fun onCreate() {
        super.onCreate()
        this.injectMembers()
        MyWorkerManager(appComponent).launchWorker()

        // this.initializeLeakDetection()
    }



    private fun injectMembers() = appComponent.inject(this)

//    private fun initializeLeakDetection() {
//        if (BuildConfig.DEBUG) LeakCanary.install(this)
//    }
}
