package com.example.animelist.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.animelist.data.api.AnimeApi
import com.example.animelist.domain.model.Manga
import com.example.animelist.domain.repository.SortType

class MangaPagingSource(
    val animeApi: AnimeApi,
    val sortType: SortType
) : PagingSource<Int, Manga>() {
    override fun getRefreshKey(state: PagingState<Int, Manga>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Manga> {
        return try {
            // Start refresh at page 1 if undefined.
            val nextPageNumber = params.key ?: 1
            val response = animeApi.getTopManga(page = nextPageNumber,sortType = sortType)
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