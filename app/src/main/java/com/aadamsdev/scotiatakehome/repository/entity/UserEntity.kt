package com.aadamsdev.scotiatakehome.repository.entity

import com.google.gson.annotations.SerializedName

data class UserEntity(val name: String,
                      @SerializedName("avatar_url") val avatarUrl: String)