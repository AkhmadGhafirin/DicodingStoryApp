package com.cascer.dicodingstoryapp.data.model.mapper

import com.cascer.dicodingstoryapp.data.model.StoryDataModel
import com.cascer.dicodingstoryapp.data.model.StoryDataResponse
import com.cascer.dicodingstoryapp.data.model.StoryDetailModel
import com.cascer.dicodingstoryapp.data.model.StoryDetailResponse
import com.cascer.dicodingstoryapp.data.model.StoryListModel
import com.cascer.dicodingstoryapp.data.model.StoryListResponse
import com.cascer.dicodingstoryapp.data.model.UserDataModel
import com.cascer.dicodingstoryapp.data.model.UserDataResponse
import com.cascer.dicodingstoryapp.data.model.UserModel
import com.cascer.dicodingstoryapp.data.model.UserResponse

object Mapper {

    fun UserResponse.toModel() = UserModel(
        error = error ?: false,
        message = message.orEmpty(),
        loginResult = loginResult?.toModel() ?: emptyUserDataModel()
    )

    fun UserDataResponse.toModel() = UserDataModel(
        userId = userId.orEmpty(),
        name = name.orEmpty(),
        token = token.orEmpty()
    )

    fun emptyUserModel() = UserModel(
        error = false,
        message = "",
        loginResult = emptyUserDataModel()
    )

    fun emptyUserDataModel() = UserDataModel(
        userId = "",
        name = "",
        token = ""
    )

    fun StoryListResponse.toModel() = StoryListModel(
        error = error ?: false,
        message = message.orEmpty(),
        listStory = listStory?.map { it.toModel() } ?: listOf()
    )

    fun StoryDetailResponse.toModel() = StoryDetailModel(
        error = error ?: false,
        message = message.orEmpty(),
        story = story?.toModel() ?: emptyStoryModel()
    )

    fun StoryDataResponse.toModel() = StoryDataModel(
        id = id.orEmpty(),
        name = name.orEmpty(),
        description = description.orEmpty(),
        photoUrl = photoUrl.orEmpty(),
        createdAt = createdAt.orEmpty(),
        lat = lat ?: 0.0f,
        lon = lon ?: 0.0f
    )

    fun emptyStoryListModel() = StoryListModel(
        error = false,
        message = "",
        listStory = listOf()
    )

    fun emptyStoryDetailModel() = StoryDetailModel(
        error = false,
        message = "",
        story = emptyStoryModel()
    )

    fun emptyStoryModel() = StoryDataModel(
        id = "",
        name = "",
        description = "",
        photoUrl = "",
        createdAt = "",
        lat = 0.0f,
        lon = 0.0f
    )
}