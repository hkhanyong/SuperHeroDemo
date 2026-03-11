package com.example.superhero.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.superhero.api.ISuperHeroService
import com.example.superhero.model.SuperHeroModel
import com.example.superhero.repository.SuperHeroConfig.PAGE_OFFSET
import com.example.superhero.repository.SuperHeroConfig.RESPONSE_SUCCESS
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

class SuperHeroPagingSource(private val api: ISuperHeroService) :
    PagingSource<Int, SuperHeroModel>() {
    override fun getRefreshKey(state: PagingState<Int, SuperHeroModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SuperHeroModel> {
        val currentKey = params.key ?: 0
        return try {
            val response = fetchSuperHeroWithPage(currentKey)
            val hasNext = response.size == PAGE_OFFSET
            LoadResult.Page(
                data = response,
                prevKey = if (currentKey == 0) null else currentKey - 1,
                nextKey = if (!hasNext) null else currentKey + 1
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    /**
     * 由于不能获取英雄角色总数量，只能每次都去获取PAGE_OFFSET个角色，
     * 根据返回的response字段判断是否获取成功
     */
    private suspend fun fetchSuperHeroWithPage(page: Int): List<SuperHeroModel> = coroutineScope{
            val start = page * PAGE_OFFSET + 1
            val end = (page + 1 ) * PAGE_OFFSET
            val deferredList = (start..end).map { id->
                async {
                    val data = api.getSuperHeroById(id).body()
                    if(data?.response?.equals(RESPONSE_SUCCESS) == true){
                        data
                    }else{
                        null
                    }

                }
            }
           deferredList.awaitAll().filterNotNull()
    }
}