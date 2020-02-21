package com.afterwork.mytrendsearch

import android.app.Application
import com.afterwork.mytrendsearch.common.myDiModule
import com.facebook.drawee.backends.pipeline.Fresco
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin

class MyApplication : Application(){

    override fun onCreate() {
        super.onCreate()

        Fresco.initialize(applicationContext)
        startKoin {
            androidContext(applicationContext)
            modules(myDiModule)
        }
    }

    override fun onTerminate() {
        super.onTerminate()

        stopKoin()
        Fresco.shutDown()
    }
}