package com.cascer.dicodingstoryapp.data.api

import com.cascer.dicodingstoryapp.data.model.LoginRequest
import com.cascer.dicodingstoryapp.data.model.RegisterRequest
import com.cascer.dicodingstoryapp.data.model.StoryDetailResponse
import com.cascer.dicodingstoryapp.data.model.StoryListResponse
import com.cascer.dicodingstoryapp.data.model.UserResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiService {

    @POST("register")
    suspend fun register(
        @Body param: RegisterRequest
    ): Response<UserResponse>

    @POST("login")
    suspend fun login(
        @Body param: LoginRequest
    ): Response<UserResponse>

    @Multipart
    @POST("stories")
    suspend fun addStory(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody
    ): Response<StoryDetailResponse>

    @GET("stories")
    suspend fun stories(
        @Header("Authorization") token: String
    ): Response<StoryListResponse>

    @GET("stories/{id}")
    suspend fun story(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): Response<StoryDetailResponse>
}