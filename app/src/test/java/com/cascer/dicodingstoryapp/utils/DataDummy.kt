package com.cascer.dicodingstoryapp.utils

import com.cascer.dicodingstoryapp.data.model.StoryDataModel

object DataDummy {

    fun generateDummyStoryData(): List<StoryDataModel> {
        val items: MutableList<StoryDataModel> = arrayListOf()
        for (i in 0..100) {
            val quote = StoryDataModel(
                id = "$i",
                name = "name $i",
                description = "description $i",
                photoUrl = "photoUrl $i",
                createdAt = "createdAt $i",
                lat = 0.0f,
                lon = 0.0f
            )
            items.add(quote)
        }
        return items
    }
}