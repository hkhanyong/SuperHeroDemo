package com.example.superhero.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.superhero.viewmodel.SuperHeroViewModel

/**
 * 英雄角色列表界面
 * 支持分页加载
 */
@Composable
fun HeroListView(
) {
    val heroViewModel: SuperHeroViewModel = viewModel()
    val superHeros = heroViewModel.superHeroList.collectAsLazyPagingItems()
    var selectedIndex by remember { mutableStateOf(-1) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(4.dp), content = {
                items(superHeros.itemCount) {
                    val hero = superHeros[it]
                    SuperHeroItem(hero, { selectedIndex = it })
                }

                superHeros.apply {
                    // 第一次加载或下拉刷新，展示顶部加载动画
                    when (loadState.refresh) {
                        is LoadState.Error -> {
                            item {
                                ErrorItem(onRetry = { refresh() })
                            }
                        }

                        is LoadState.Loading -> item { LoadingItem() }
                        is LoadState.NotLoading -> {

                        }
                    }
                    // 展示bottom 加载下一页相关逻辑展示
                    when (val appendLoadState = loadState.append) {
                        is LoadState.Error -> {
                            item {
                                ErrorItem(onRetry = { retry() })
                            }
                        }

                        is LoadState.Loading -> item { LoadingItem() }
                        is LoadState.NotLoading -> {
                            if (appendLoadState.endOfPaginationReached) {
                                item { EndOfListFooter() }
                            }
                        }
                    }
                }

            })
        if (selectedIndex > -1) {
            val hero = superHeros[selectedIndex]
            HeroDetailDialog(hero!!, { selectedIndex = -1 })
        }
    }
}