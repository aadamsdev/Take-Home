package com.aadamsdev.scotiatakehome.di

import com.aadamsdev.scotiatakehome.TakeHomeApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    (AndroidSupportInjectionModule::class),
    (AppModule::class),
    (ActivityModule::class)
])
interface AppComponent : AndroidInjector<TakeHomeApplication> {

    override fun inject(instance: TakeHomeApplication)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: TakeHomeApplication): Builder

        fun build(): AppComponent
    }
}