package com.example.animelist.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.animelist.data.api.MediaApi
import com.example.animelist.domain.model.Media
import com.example.animelist.domain.repository.MediaType
import com.example.animelist.domain.repository.SortType

class MediaPagingSource(
    val mediaApi: MediaApi,
    val type: MediaType,
    val sortType: SortType
) : PagingSource<Int, Media>() {
    override fun getRefreshKey(state: PagingState<Int, Media>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Media> {
        return try {
            // Start refresh at page 1 if undefined.
            val nextPageNumber = params.key ?: 1
            val response = mediaApi.getTopMedia(page = nextPageNumber, type = type, sortType = sortType)
            LoadResult.Page(
                data = response,
                prevKey = if (nextPageNumber == 1) null else nextPageNumber - 1,
                nextKey = if (response.isEmpty()) null else nextPageNumber + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
