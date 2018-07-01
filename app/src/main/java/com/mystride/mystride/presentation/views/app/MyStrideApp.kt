package com.mystride.mystride.presentation.views.app

import android.app.Application

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