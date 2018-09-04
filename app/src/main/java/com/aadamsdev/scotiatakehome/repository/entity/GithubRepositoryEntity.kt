package com.aadamsdev.scotiatakehome.repository.entity

import com.google.gson.annotations.SerializedName
import java.util.*

data class GithubRepositoryEntity(val name: String,
                                  val description: String,
                                  @SerializedName("updated_at") val updatedAt: Date,
                                  @SerializedName("stargazers_count") val starGazersCount: Int,
                                  val forks: Int) : BaseListItem()