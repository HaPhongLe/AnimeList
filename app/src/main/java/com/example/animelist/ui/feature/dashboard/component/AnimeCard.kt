package com.example.animelist.ui.feature.dashboard.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.animelist.domain.mockModel.mock
import com.example.animelist.domain.model.Anime
import com.example.animelist.ui.util.hexToComposeColor

@Composable
fun AnimeCard(
    anime: Anime,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .width(200.dp)
            .aspectRatio(0.75f)
    ) {
        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = anime.coverImage?.large,
            contentDescription = null,
            contentScale = ContentScale.FillBounds
        )
        Column(
            modifier = Modifier
                .background(color = Color.Gray.copy(alpha = 0.9f))
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        ) {
            val color = remember { anime.coverImage?.color?.let { hexToComposeColor(it) } ?: Color.White }
            anime.title?.english?.let { Text(text = it, color = Color.White, fontWeight = FontWeight.Bold) }
            anime.studios.first().let {
                Text(text = it, color = color)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview_AnimeCard() {
    AnimeCard(
        anime = Anime.mock()
    )
}
