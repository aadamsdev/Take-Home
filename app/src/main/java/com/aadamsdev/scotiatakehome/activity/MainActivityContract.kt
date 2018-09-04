package com.aadamsdev.scotiatakehome.activity

import com.aadamsdev.scotiatakehome.activity.base.BaseViewInterface
import com.aadamsdev.scotiatakehome.repository.entity.GithubRepositoryEntity
import com.aadamsdev.scotiatakehome.repository.entity.UserEntity

interface MainActivityContract {

    interface View : BaseViewInterface {
        fun setUserProfile(user: UserEntity)
        fun setGithubRepositories(repositoryEntities: List<GithubRepositoryEntity>)
        fun showErrorToast()
    }
}