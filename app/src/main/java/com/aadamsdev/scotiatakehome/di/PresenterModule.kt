package com.aadamsdev.scotiatakehome.di

import com.aadamsdev.scotiatakehome.activity.MainActivityPresenter
import com.aadamsdev.scotiatakehome.repository.GithubRepository
import com.aadamsdev.scotiatakehome.activity.MainActivity
import com.aadamsdev.scotiatakehome.activity.MainActivityContract
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class PresenterModule {

    @Binds
    abstract fun mainActivityView(mainActivity: MainActivity): MainActivityContract.View

    @Module
    companion object {

        @JvmStatic
        @Provides
        fun provideHomePresenter(view: MainActivityContract.View,
                                 githubRepository: GithubRepository): MainActivityPresenter {

            return MainActivityPresenter(view, githubRepository)
        }
    }
}