package com.cascer.dicodingstoryapp.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.cascer.dicodingstoryapp.data.api.ApiService
import com.cascer.dicodingstoryapp.data.model.LoginRequest
import com.cascer.dicodingstoryapp.data.model.RegisterRequest
import com.cascer.dicodingstoryapp.data.model.StoryDataModel
import com.cascer.dicodingstoryapp.data.model.StoryDetailModel
import com.cascer.dicodingstoryapp.data.model.StoryListModel
import com.cascer.dicodingstoryapp.data.model.UserModel
import com.cascer.dicodingstoryapp.data.model.mapper.Mapper.emptyStoryDetailModel
import com.cascer.dicodingstoryapp.data.model.mapper.Mapper.emptyStoryListModel
import com.cascer.dicodingstoryapp.data.model.mapper.Mapper.emptyUserModel
import com.cascer.dicodingstoryapp.data.model.mapper.Mapper.toModel
import com.cascer.dicodingstoryapp.data.source.StoryPagingSource
import com.cascer.dicodingstoryapp.utils.AppPreferences
import com.cascer.dicodingstoryapp.utils.ExceptionUtil.toException
import com.cascer.dicodingstoryapp.utils.network.Result
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
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

    override fun stories(): LiveData<PagingData<StoryDataModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10
            ),
            pagingSourceFactory = {
                StoryPagingSource(apiService, token = appPreferences.token)
            }
        ).liveData
    }

    override suspend fun storiesWithLocation(): Result<StoryListModel> {
        return try {
            val request = apiService.storiesWithLocation(
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

    override suspend fun uploadStory(photo: File, desc: String): Result<StoryDetailModel> {
        return try {
            val requestBody = desc.toRequestBody("text/plain".toMediaType())
            val requestImageFile = photo.asRequestBody("image/jpeg".toMediaType())
            val multipartBody = MultipartBody.Part.createFormData(
                "photo", photo.name, requestImageFile
            )
            val request = apiService.addStory(
                token = appPreferences.token,
                file = multipartBody,
                description = requestBody
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