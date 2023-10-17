package com.cascer.dicodingstoryapp.data.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.cascer.dicodingstoryapp.data.api.ApiService
import com.cascer.dicodingstoryapp.data.model.StoryDataModel
import com.cascer.dicodingstoryapp.data.model.mapper.Mapper.toModel

class StoryPagingSource(private val apiService: ApiService, private val token: String) :
    PagingSource<Int, StoryDataModel>() {

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    override fun getRefreshKey(state: PagingState<Int, StoryDataModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, StoryDataModel> {
        return try {
            val position = params.key ?: INITIAL_PAGE_INDEX
            val responseData = apiService.stories(
                token = token, page = position, size = params.loadSize
            )
            LoadResult.Page(data = responseData.body()?.listStory?.map { it.toModel() } ?: listOf(),
                prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
                nextKey = if (responseData.body()?.listStory.isNullOrEmpty()) null else position + 1)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}