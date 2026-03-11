package com.example.superhero.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.superhero.model.SuperHeroModel

/**
 * 英雄角色详情弹窗组件
 */
@Composable
fun HeroDetailDialog(
    hero: SuperHeroModel,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        )
    ) {
        Card(
            modifier = Modifier
                .width(300.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween
                ) {
                    HeroAvatar(hero.image.url)
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(Color.LightGray.copy(alpha = 0.3f))
                            .clickable { onDismiss() },
                        contentAlignment = Alignment.Center
                    ) {
                        Text("×")
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = hero.name,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(12.dp))

                val genderText = hero.appearance.gender
                Text(
                    text = "性别：$genderText",
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(8.dp))

                val occupationText = hero.work.occupation
                Text(
                    text = "职业：$occupationText",
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(8.dp))

                val heightText = hero.appearance.height
                val weightText = hero.appearance.weight
                Text(
                    text = "身高：$heightText | 体重：$weightText",
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(20.dp))

                val powerText = hero.powerstats.power
                val speedText = hero.powerstats.speed
                val intelligenceText = hero.powerstats.intelligence
                val strengthText = hero.powerstats.strength
                Text(
                    text = "能力：$powerText | 速度：$speedText \n" +
                            "智力：$intelligenceText | 力量：$strengthText ",
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(20.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .clip(RoundedCornerShape(24.dp))
                        .clickable { onDismiss() },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "确 定",
                        color = Color.DarkGray,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}