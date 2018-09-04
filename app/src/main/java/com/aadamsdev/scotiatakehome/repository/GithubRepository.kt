package com.aadamsdev.scotiatakehome.repository

import com.aadamsdev.scotiatakehome.repository.entity.GithubRepositoryEntity
import com.aadamsdev.scotiatakehome.repository.entity.UserEntity
import com.aadamsdev.scotiatakehome.repository.api.GithubService
import io.reactivex.Single
import javax.inject.Singleton

@Singleton
class GithubRepository(private val githubService: GithubService) {
    fun getUserById(userId: String): Single<UserEntity> {
        return githubService.getUser(userId)
    }

    fun getUserRepositories(userId: String): Single<List<GithubRepositoryEntity>> {
        return githubService.getUserRepositories(userId)
    }
}