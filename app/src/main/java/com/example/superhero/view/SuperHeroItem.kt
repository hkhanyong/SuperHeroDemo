package com.example.superhero.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.superhero.R
import com.example.superhero.model.SuperHeroModel

@Composable
fun SuperHeroItem(data: SuperHeroModel?, onItemClick: () -> Unit) {
    if (data == null) {
        EmptyHeroItem()
        return
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 1.dp, vertical = 4.dp)
            .clickable { onItemClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            HeroAvatar(imageUrl = data.image.url)
            Spacer(modifier = Modifier.width(12.dp))

            HeroInfoSection(data)
        }
    }
}

/**
 * 英雄头像组件（处理空URL/加载失败）
 */
@Composable
fun HeroAvatar(imageUrl: String?) {
    val placeholderPainter: Painter = painterResource(R.drawable.ic_avatar_place)
    val errorPainter: Painter = painterResource(R.drawable.ic_avatar_error)
    Box(
        modifier = Modifier
            .size(80.dp)
            .clip(CircleShape),
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = "英雄头像",
            modifier = Modifier.fillMaxSize(),
            placeholder = placeholderPainter,
            error = errorPainter,
            contentScale = ContentScale.Crop
        )
    }
}

/**
 * 英雄信息区域（三行文本）
 */
@Composable
private fun HeroInfoSection(
    data: SuperHeroModel
) {
    val name = data.name
    val occupation = data.work.occupation
    val height = data.appearance.height
    val power = data.powerstats.power
    Column() {
        Text(
            text = name,
            maxLines = 1
        )

        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "职业：$occupation",
            maxLines = 1,
        )

        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "身高：$height ",
            maxLines = 1,
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "能力值：$power ",
            maxLines = 1,
        )
    }
}

/**
 * 空数据占位卡片
 */
@Composable
private fun EmptyHeroItem() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "暂无英雄数据",
                color = Color.Gray
            )
        }
    }
}