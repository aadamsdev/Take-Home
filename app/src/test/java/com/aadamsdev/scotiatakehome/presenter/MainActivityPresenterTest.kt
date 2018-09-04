package com.aadamsdev.scotiatakehome.presenter

import com.aadamsdev.scotiatakehome.util.MockNetworkResponseUtil
import com.aadamsdev.scotiatakehome.activity.MainActivityContract
import com.aadamsdev.scotiatakehome.activity.MainActivityPresenter
import com.aadamsdev.scotiatakehome.repository.GithubRepository
import com.aadamsdev.scotiatakehome.repository.entity.GithubRepositoryEntity
import com.aadamsdev.scotiatakehome.repository.entity.UserEntity
import com.google.gson.Gson
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.junit.internal.Classes
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class MainActivityPresenterTest {

    @Mock
    lateinit var mainActivityView: MainActivityContract.View

    @Mock
    lateinit var githubRepository: GithubRepository

    lateinit var mainActivityPresenter: MainActivityPresenter

    lateinit var gson: Gson

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }

        gson = Gson()
        mainActivityPresenter = MainActivityPresenter(mainActivityView, githubRepository)
    }

    @Test
    fun loadUserInfoTest() {
        val userId = "octocat"
        val user = getUserResponse()
        val userRepositories = getUserRepositories()

        Mockito.`when`(githubRepository.getUserById(userId)).thenReturn(Single.just(user))
        Mockito.`when`(githubRepository.getUserRepositories(userId)).thenReturn(Single.just(userRepositories))

        mainActivityPresenter.getUserData(userId)

        Mockito.verify(mainActivityView, Mockito.times(1)).setUserProfile(user)
        Mockito.verify(mainActivityView, Mockito.times(1)).setGithubRepositories(userRepositories)
        Mockito.verify(mainActivityView, Mockito.never()).showErrorToast()
    }

    @Test
    fun errorUserInfoTest() {
        val userId = "octocat"
        val user = getUserResponse()
        val userRepositories = getUserRepositories()

        Mockito.`when`(githubRepository.getUserById(userId)).thenReturn(Single.error(Throwable("Test Exception")))
        Mockito.`when`(githubRepository.getUserRepositories(userId)).thenReturn(Single.just(userRepositories))

        mainActivityPresenter.getUserData(userId)

        Mockito.verify(mainActivityView, Mockito.times(1)).showErrorToast()
        Mockito.verify(mainActivityView, Mockito.never()).setUserProfile(user)
        Mockito.verify(mainActivityView, Mockito.never()).setGithubRepositories(userRepositories)
    }

    private fun getUserResponse(): UserEntity {
        return gson.fromJson(
                MockNetworkResponseUtil.convertStreamToString("User.json",
                        Classes.getClass(UserEntity::class.java.name).classLoader),
                UserEntity::class.java
        )
    }

    private fun getUserRepositories(): List<GithubRepositoryEntity> {
        return gson.fromJson(
                MockNetworkResponseUtil.convertStreamToString("GithubRepositories.json",
                        Classes.getClass(Array<GithubRepositoryEntity>::class.java.name).classLoader),
                Array<GithubRepositoryEntity>::class.java).toMutableList()
    }
}
