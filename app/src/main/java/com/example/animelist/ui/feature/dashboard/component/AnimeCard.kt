package com.example.animelist.ui.feature.dashboard.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.animelist.domain.mockModel.mock
import com.example.animelist.domain.model.Anime
import com.example.animelist.ui.util.hexToComposeColor

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AnimeCard(
    anime: Anime,
    ranking: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(250.dp)
            .clip(RoundedCornerShape(5.dp))
    ) {
        AnimeAvatar(
            ranking = ranking,
            imageUrl = anime.coverImage?.large
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .background(color = Color.Gray.copy(alpha = 0.9f))
                .padding(horizontal = 8.dp)
        ) {
            val color = remember { anime.coverImage?.color?.let { hexToComposeColor(it) } ?: Color.White }
            anime.title?.english?.let { Text(text = it, color = Color.White, fontWeight = FontWeight.Bold) }
            anime.studios.firstOrNull()?.let {
                Text(text = it, color = color)
            }
            FlowRow(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                anime.genres.forEach { genre ->
                    AssistChip(
                        onClick = {},
                        label = { Text(text = genre, color = Color.White) },
                        shape = RoundedCornerShape(20.dp),
                        colors = AssistChipDefaults.assistChipColors(containerColor = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)),
                        border = AssistChipDefaults.assistChipBorder(false)
                    )
                }
            }
        }
    }
}

@Composable
private fun AnimeAvatar(
    modifier: Modifier = Modifier,
    ranking: Int,
    imageUrl: String?
) {
    Box(
        modifier = modifier
            .fillMaxHeight()
            .aspectRatio(0.75f)
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxSize(),
            model = imageUrl,
            contentDescription = null,
            contentScale = ContentScale.FillBounds
        )

        Box(
            modifier = Modifier
                .height(60.dp)
                .aspectRatio(1f)
                .drawBehind {
                    val path = Path().apply {
                        moveTo(0f, 0f)
                        moveTo(size.width, 0f)
                        lineTo(0f, size.height)
                        lineTo(0f, 0f)
                        close()
                    }

                    drawPath(path, color = Color.Blue.copy(alpha = 0.2f))
                }
                .padding(4.dp)
        ) {
            Text(
                text = ranking.toString(),
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview_AnimeCard() {
    AnimeCard(
        anime = Anime.mock(),
        ranking = 1
    )
}
