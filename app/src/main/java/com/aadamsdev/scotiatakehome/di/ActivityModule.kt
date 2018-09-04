package com.aadamsdev.scotiatakehome.di

import com.aadamsdev.scotiatakehome.activity.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector(modules = [(PresenterModule::class)])
    abstract fun mainActivityInjector(): MainActivity
}
