package com.example.superhero.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.superhero.model.SuperHeroModel
import com.example.superhero.repository.SuperHeroConfig.PAGE_OFFSET
import com.example.superhero.repository.SuperHeroRepository
import kotlinx.coroutines.flow.Flow

class SuperHeroViewModel(
    private val repository: SuperHeroRepository = SuperHeroRepository()
) : ViewModel() {

    val superHeroList: Flow<PagingData<SuperHeroModel>> =
        Pager(
            config = PagingConfig(PAGE_OFFSET),
            pagingSourceFactory = {
                repository.superHeroPagingSource()
            }
        )
            .flow
            .cachedIn(viewModelScope)
}