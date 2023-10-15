package com.cascer.dicodingstoryapp.data.repository

import com.cascer.dicodingstoryapp.data.api.ApiService
import com.cascer.dicodingstoryapp.data.model.LoginRequest
import com.cascer.dicodingstoryapp.data.model.RegisterRequest
import com.cascer.dicodingstoryapp.data.model.StoryDetailModel
import com.cascer.dicodingstoryapp.data.model.StoryListModel
import com.cascer.dicodingstoryapp.data.model.UserModel
import com.cascer.dicodingstoryapp.data.model.mapper.Mapper.emptyStoryDetailModel
import com.cascer.dicodingstoryapp.data.model.mapper.Mapper.emptyStoryListModel
import com.cascer.dicodingstoryapp.data.model.mapper.Mapper.emptyUserModel
import com.cascer.dicodingstoryapp.data.model.mapper.Mapper.toModel
import com.cascer.dicodingstoryapp.utils.AppPreferences
import com.cascer.dicodingstoryapp.utils.ExceptionUtil.toException
import com.cascer.dicodingstoryapp.utils.network.Result
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val appPreferences: AppPreferences,
    private val apiService: ApiService
) : Repository {
    override suspend fun login(param: LoginRequest): Result<UserModel> {
        return try {
            val request = apiService.login(param)
            if (request.isSuccessful) {
                Result.Success(request.body()?.toModel() ?: emptyUserModel())
            } else {
                Result.Error(request.errorBody().toException())
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun register(param: RegisterRequest): Result<UserModel> {
        return try {
            val request = apiService.register(param)
            if (request.isSuccessful) {
                Result.Success(request.body()?.toModel() ?: emptyUserModel())
            } else {
                Result.Error(request.errorBody().toException())
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun stories(): Result<StoryListModel> {
        return try {
            val request = apiService.stories(
                token = appPreferences.token
            )
            if (request.isSuccessful) {
                Result.Success(request.body()?.toModel() ?: emptyStoryListModel())
            } else {
                Result.Error(request.errorBody().toException())
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun story(id: String): Result<StoryDetailModel> {
        return try {
            val request = apiService.story(
                token = appPreferences.token,
                id = id
            )
            if (request.isSuccessful) {
                Result.Success(request.body()?.toModel() ?: emptyStoryDetailModel())
            } else {
                Result.Error(request.errorBody().toException())
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}