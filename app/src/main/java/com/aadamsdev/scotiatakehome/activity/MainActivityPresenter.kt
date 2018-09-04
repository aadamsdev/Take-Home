package com.aadamsdev.scotiatakehome.activity

import com.aadamsdev.scotiatakehome.activity.base.BasePresenter
import com.aadamsdev.scotiatakehome.repository.entity.GithubRepositoryEntity
import com.aadamsdev.scotiatakehome.repository.entity.UserEntity
import com.aadamsdev.scotiatakehome.repository.GithubRepository
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers

class MainActivityPresenter(private var view: MainActivityContract.View?,
                            private val githubRepository: GithubRepository) : BasePresenter(view) {

    private val compositeDisposable = CompositeDisposable()

    fun getUserData(userId: String) {
        Single.zip(githubRepository.getUserById(userId),
                githubRepository.getUserRepositories(userId),
                BiFunction<UserEntity, List<GithubRepositoryEntity>, Pair<UserEntity, List<GithubRepositoryEntity>>> { user: UserEntity, repositoryEntities: List<GithubRepositoryEntity> ->
                    Pair(user, repositoryEntities)
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SingleObserver<Pair<UserEntity, List<GithubRepositoryEntity>>> {
                    override fun onSuccess(pair: Pair<UserEntity, List<GithubRepositoryEntity>>) {
                        val (user, repositories) = pair

                        view?.setUserProfile(user)
                        view?.setGithubRepositories(repositories)
                    }

                    override fun onSubscribe(d: Disposable) {
                        compositeDisposable.add(d)
                    }

                    override fun onError(e: Throwable) {
                        view?.showErrorToast()
                        e.printStackTrace()
                    }
                })
    }

    fun clearDisposable() {
        compositeDisposable.clear()
    }
}