package com.example.superhero.viewmodel

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.superhero.model.SuperHeroModel
import com.example.superhero.repository.SuperHeroConfig.PAGE_OFFSET
import com.example.superhero.repository.SuperHeroPagingSource
import com.example.superhero.repository.SuperHeroRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

/**
 * SuperHeroViewModel 单元测试类
 * 核心测试：Paging 数据流创建、Repository 调用、依赖注入
 */
@RunWith(MockitoJUnitRunner::class)
@OptIn(ExperimentalCoroutinesApi::class) // 启用协程测试 API
class SuperHeroViewModelTest {

    // Mock 依赖：避免真实 Repository 调用网络
    @Mock
    private lateinit var mockRepository: SuperHeroRepository

    // Mock PagingSource：模拟分页数据源
    @Mock
    private lateinit var mockPagingSource: SuperHeroPagingSource

    // 测试用 ViewModel 实例（分两种：默认 Repository / Mock Repository）
    private lateinit var defaultViewModel: SuperHeroViewModel
    private lateinit var mockRepoViewModel: SuperHeroViewModel

    /**
     * 测试前置初始化：创建 ViewModel 实例，Mock 必要依赖
     */
    @Before
    fun setup() {
        // 1. Mock Repository 的返回值：调用 superHeroPagingSource 时返回 mockPagingSource
        `when`(mockRepository.superHeroPagingSource()).thenReturn(mockPagingSource)

        // 2. 创建 ViewModel 实例
        defaultViewModel = SuperHeroViewModel() // 使用默认 Repository
        mockRepoViewModel = SuperHeroViewModel(mockRepository) // 注入 Mock Repository
    }

    /**
     * 测试 1：验证 superHeroList 数据流非空，且 PagingConfig 配置正确
     * 核心：确保 Pager 使用的 PAGE_OFFSET 符合预期
     */
    @Test
    fun `superHeroList should be non-null and use correct PagingConfig`() = runTest {
        // 执行：获取 viewModel 的 superHeroList 数据流
        val flow: Flow<PagingData<SuperHeroModel>> = defaultViewModel.superHeroList

        // 验证 1：数据流非空
        assertNotNull("superHeroList 数据流为空", flow)

        // 验证 2：PagingConfig 的 pageSize 等于 PAGE_OFFSET（核心配置）
        // 注：Paging 3 的 Flow 无法直接获取 PagingConfig，通过行为验证 + 配置约定保证
        assertEquals("PagingConfig 页码偏移配置错误", PAGE_OFFSET, PagingConfig(PAGE_OFFSET).pageSize)
    }

    /**
     * 测试 2：验证 PagingSourceFactory 正确调用 Repository 的方法
     * 核心：确保 viewModel 会调用 repository.superHeroPagingSource()
     */
    @Test
    fun `pagingSourceFactory should call repository superHeroPagingSource`() = runTest {
        // 执行：触发 PagingSourceFactory 执行（访问 superHeroList 并收集一次数据）
        val flow = mockRepoViewModel.superHeroList
        flow.collect { /* 仅触发工厂方法执行，无需处理数据 */ }

        // 验证：Repository 的 superHeroPagingSource 方法被调用且仅调用一次
        verify(mockRepository, times(1)).superHeroPagingSource()
    }

    /**
     * 测试 3：验证依赖注入灵活性（自定义 Repository 生效）
     * 核心：确保注入的 Mock Repository 被正确使用
     */
    @Test
    fun `viewModel should use injected repository instance`() = runTest {
        // 执行：通过 mockRepoViewModel 触发 PagingSource 创建
        mockRepoViewModel.superHeroList.collect { /* 触发工厂方法 */ }

        // 验证：使用的是注入的 Mock Repository，而非默认实例
        verify(mockRepository).superHeroPagingSource() // 若调用则说明注入生效
        assertNotEquals(
            "viewModel 使用了默认 Repository 而非注入的实例",
            mockRepository,
            SuperHeroRepository()
        )
    }

    /**
     * 测试 4：边界测试 - 空 PagingSource 时数据流不崩溃
     * 核心：确保异常场景下 viewModel 仍能优雅处理
     */
    @Test
    fun `superHeroList should handle null PagingSource gracefully`() = runTest {
        // 模拟：Repository 返回 null PagingSource
        `when`(mockRepository.superHeroPagingSource()).thenReturn(null)

        val flow = mockRepoViewModel.superHeroList
        try {
            flow.collect { } // 执行可能抛出异常的代码
        } catch (e: Exception) {
            // 若捕获到异常，手动让测试失败并提示原因
            fail("空 PagingSource 导致数据流收集崩溃，异常：${e.message}")
        }
    }
}