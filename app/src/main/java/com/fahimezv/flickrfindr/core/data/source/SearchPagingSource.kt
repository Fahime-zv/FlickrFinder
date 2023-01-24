package com.fahimezv.flickrfindr.core.data.source

import androidx.paging.PagingSource

import androidx.paging.PagingState
import com.fahimezv.flickrfindr.core.network.NetworkDataSource
import com.fahimezv.flickrfindr.core.network.model.PhotoDao
import okio.IOException
import retrofit2.HttpException

private const val PAGE_START_INDEX = 1
const val PAGE_SIZE = 25

class SearchPagingSource(
    private val networkDataSource: NetworkDataSource,
    private val term: String
) : PagingSource<Int, PhotoDao>() {

    override fun getRefreshKey(state: PagingState<Int, PhotoDao>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PhotoDao> {
        val pageIndex = params.key ?: PAGE_START_INDEX
        return try {
            val sourceResult =
                networkDataSource.search(
                    term = term,
                    page = pageIndex,
                    perPage = PAGE_SIZE
                )
            val nextKey =
                if (pageIndex * PAGE_SIZE < sourceResult.photos.total) {
                    pageIndex + 1
                } else {
                    null
                }

            LoadResult.Page(
                data = sourceResult.photos.list,
                prevKey = if (pageIndex == PAGE_START_INDEX) null else pageIndex,
                nextKey = nextKey,
            )
        } catch (exception: Throwable) {
            LoadResult.Error(exception)
        }
    }

}