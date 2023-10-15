package com.cascer.dicodingstoryapp.data.model

import com.google.gson.annotations.SerializedName

data class StoryListModel(
    val error: Boolean,
    val message: String,
    val listStory: List<StoryDataModel>,
)

data class StoryDetailModel(
    val error: Boolean,
    val message: String,
    val story: StoryDataModel,
)

data class StoryDataModel(
    val id: String,
    val name: String,
    val description: String,
    val photoUrl: String,
    val createdAt: String,
    val lat: Float,
    val lon: Float,
)

data class StoryListResponse(
    @SerializedName("error") val error: Boolean?,
    @SerializedName("message") val message: String?,
    @SerializedName("listStory") val listStory: List<StoryDataResponse>?,
)

data class StoryDetailResponse(
    @SerializedName("error") val error: Boolean?,
    @SerializedName("message") val message: String?,
    @SerializedName("story") val story: StoryDataResponse?,
)

data class StoryDataResponse(
    @SerializedName("id") val id: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("photoUrl") val photoUrl: String?,
    @SerializedName("createdAt") val createdAt: String?,
    @SerializedName("lat") val lat: Float?,
    @SerializedName("lon") val lon: Float?,
)