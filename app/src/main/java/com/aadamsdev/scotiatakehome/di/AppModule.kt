package com.aadamsdev.scotiatakehome.di

import android.app.Application
import android.content.Context
import com.aadamsdev.scotiatakehome.BuildConfig.GITHUB_URL
import com.aadamsdev.scotiatakehome.TakeHomeApplication
import com.aadamsdev.scotiatakehome.repository.GithubRepository
import com.aadamsdev.scotiatakehome.repository.api.GithubService
import dagger.Binds
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
abstract class AppModule {

    @Binds
    abstract fun bindApplication(application: TakeHomeApplication): Application

    @Binds
    abstract fun bindContext(application: TakeHomeApplication): Context

    @Module
    companion object {
        const val TIMEOUT_MINUTES: Long = 1

        @Provides
        @JvmStatic
        @Singleton
        fun providesOkHttpClient(): OkHttpClient {
            return OkHttpClient.Builder()
                    .connectTimeout(TIMEOUT_MINUTES, TimeUnit.MINUTES)
                    .readTimeout(TIMEOUT_MINUTES, TimeUnit.MINUTES)
                    .writeTimeout(TIMEOUT_MINUTES, TimeUnit.MINUTES)
                    .build()
        }

        @Provides
        @JvmStatic
        @Singleton
        fun providesUserRepostiory(okHttpClient: OkHttpClient): GithubRepository{
            val retrofit = Retrofit.Builder()
                    .baseUrl(GITHUB_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build()

            return GithubRepository(retrofit.create(GithubService::class.java))
        }


    }
}