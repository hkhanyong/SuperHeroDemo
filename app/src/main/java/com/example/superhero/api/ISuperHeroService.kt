package com.example.superhero.api

import com.example.superhero.model.SuperHeroModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * SuperHero API 封装类
 */
interface ISuperHeroService {

    // 按id查询英雄角色信息
    @GET("{id}")
    suspend fun getSuperHeroById(@Path("id") id: Number) : Response<SuperHeroModel>
}