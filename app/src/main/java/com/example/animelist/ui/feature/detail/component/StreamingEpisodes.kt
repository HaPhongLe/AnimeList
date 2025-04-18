package com.example.animelist.ui.feature.detail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.animelist.domain.mockModel.mock
import com.example.animelist.domain.model.MediaDetails
import com.example.animelist.domain.model.StreamingEpisode
import com.example.animelist.ui.theme.AnimeListTheme
import com.example.animelist.ui.theme.AppTheme

@Composable
fun StreamingEpisodes(
    streamingEpisode: List<StreamingEpisode>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(AppTheme.dimension.spaceM)
    ) {
        streamingEpisode.forEach { streamingEpisode ->
            streamingEpisode.title?.let {
                Episode(imageUrl = streamingEpisode.thumbnail, title = it)
            }
        }
    }
}

@Composable
private fun Episode(
    modifier: Modifier = Modifier,
    imageUrl: String?,
    title: String
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(AppTheme.dimension.spaceXS))
            .background(color = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f))
            .padding(AppTheme.dimension.spaceS),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(AppTheme.dimension.spaceL)
    ) {
        AsyncImage(
            modifier = Modifier
                .height(150.dp)
                .aspectRatio(2f / 3f)
                .clip(shape = RoundedCornerShape(AppTheme.dimension.spaceXS)),
            model = imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Text(text = title, color = MaterialTheme.colorScheme.onPrimary, fontSize = 18.sp)
    }
}

@Preview
@Composable
private fun StreamingEpisodes_Preview() {
    AnimeListTheme {
        StreamingEpisodes(streamingEpisode = MediaDetails.mock().streamingEpisodes)
    }
}
