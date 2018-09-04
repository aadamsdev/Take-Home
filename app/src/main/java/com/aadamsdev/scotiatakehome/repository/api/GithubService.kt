package com.aadamsdev.scotiatakehome.repository.api

import com.aadamsdev.scotiatakehome.repository.entity.GithubRepositoryEntity
import com.aadamsdev.scotiatakehome.repository.entity.UserEntity
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubService {

    @GET("users/{userId}")
    fun getUser(
            @Path("userId") userId: String
    ): Single<UserEntity>

    @GET("users/{userId}/repos")
    fun getUserRepositories(
            @Path("userId") userId: String
    ): Single<List<GithubRepositoryEntity>>
}