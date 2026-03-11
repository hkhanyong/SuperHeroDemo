package com.example.superhero.repository

import com.example.superhero.api.ISuperHeroService
import com.example.superhero.repository.SuperHeroConfig.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SuperHeroRepository {
    companion object {
        /**
         * 为了单元测试用例，将private 改为 internal
         */
        internal val apiService: ISuperHeroService by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ISuperHeroService::class.java)
        }
    }

    fun superHeroPagingSource() = SuperHeroPagingSource(apiService)
}