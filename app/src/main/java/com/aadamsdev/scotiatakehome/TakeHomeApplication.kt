package com.aadamsdev.scotiatakehome

import com.aadamsdev.scotiatakehome.di.AppComponent
import com.aadamsdev.scotiatakehome.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class TakeHomeApplication : DaggerApplication() {
    lateinit var appComponent: AppComponent

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        appComponent = DaggerAppComponent.builder().application(this).build()
        return appComponent
    }
}