package com.mystride.app

import android.app.Application
import com.mystride.dagger.AppComponent
import com.mystride.dagger.AppModule
import com.mystride.dagger.DaggerAppComponent

class MyStrideApp : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        initializeInjector()
    }

    private fun initializeInjector() {
        this.appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
    }
}