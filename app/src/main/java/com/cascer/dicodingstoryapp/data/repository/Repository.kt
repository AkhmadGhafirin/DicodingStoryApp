package com.cascer.dicodingstoryapp.data.repository

import com.cascer.dicodingstoryapp.data.model.LoginRequest
import com.cascer.dicodingstoryapp.data.model.RegisterRequest
import com.cascer.dicodingstoryapp.data.model.StoryDetailModel
import com.cascer.dicodingstoryapp.data.model.StoryListModel
import com.cascer.dicodingstoryapp.data.model.UserModel
import com.cascer.dicodingstoryapp.utils.network.Result
import java.io.File

interface Repository {
    suspend fun login(param: LoginRequest): Result<UserModel>
    suspend fun register(param: RegisterRequest): Result<UserModel>
    suspend fun stories(): Result<StoryListModel>
    suspend fun story(id: String): Result<StoryDetailModel>
    suspend fun uploadStory(photo: File, desc: String): Result<StoryDetailModel>
}