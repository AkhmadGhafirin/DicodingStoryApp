package com.cascer.dicodingstoryapp.data.model

import com.google.gson.annotations.SerializedName

data class UserModel(
    val error: Boolean,
    val message: String,
    val loginResult: UserDataModel,
)

data class UserDataModel(
    val userId: String,
    val name: String,
    val token: String,
)

data class UserResponse(
    @SerializedName("error") val error: Boolean?,
    @SerializedName("message") val message: String?,
    @SerializedName("loginResult") val loginResult: UserDataResponse?,
)

data class UserDataResponse(
    @SerializedName("userId") val userId: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("token") val token: String?,
)