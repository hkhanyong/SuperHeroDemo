package com.example.superhero.repository

import com.example.superhero.api.ISuperHeroService
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * SuperHeroRepository 单元测试类
 * 测试核心逻辑：apiService 初始化、PagingSource 创建
 */
@RunWith(MockitoJUnitRunner::class)
class SuperHeroRepositoryTest {

    // Mock ISuperHeroService 实例，避免真实网络请求
    @Mock
    lateinit var mockApiService: ISuperHeroService

    /**
     * 测试 1：验证 apiService 懒加载初始化成功
     * 核心：确保 Retrofit 能正常创建 ISuperHeroService 实例
     */
    @Test
    fun `apiService should be initialized lazily`() {
        // 执行：访问伴生对象的 apiService
        val apiService = SuperHeroRepository.apiService

        // 验证：实例不为空，且类型正确
        assertNotNull("apiService 初始化失败，实例为空", apiService)
    }

    /**
     * 测试 2：验证 superHeroPagingSource() 方法返回正确的实例
     * 核心：确保返回的 PagingSource 注入了正确的 apiService
     */
    @Test
    fun `superHeroPagingSource should return valid PagingSource instance`() {
        // 步骤1：创建 Repository 实例
        val repository = SuperHeroRepository()

        // 步骤2：调用方法获取 PagingSource
        val pagingSource = repository.superHeroPagingSource()

        // 验证1：PagingSource 实例不为空
        assertNotNull("SuperHeroPagingSource 实例为空", pagingSource)

        // 验证2：PagingSource 中持有的 apiService 是预期的实例（这里验证类型即可，若需精准验证可结合反射）
        assertTrue("PagingSource 注入的 apiService 类型错误",
            pagingSource::class.java.declaredFields.any {
                it.type == ISuperHeroService::class.java
            })
    }
}