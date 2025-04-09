package com.example.animelist.ui.feature.detail.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.animelist.R
import com.example.animelist.domain.mockModel.mock
import com.example.animelist.domain.model.AnimeDetails
import com.example.animelist.ui.theme.AnimeListTheme

@Composable
fun BasicInfo(
    animeDetails: AnimeDetails,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier.fillMaxWidth()) {
        animeDetails.averageScore?.let {
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                text = stringResource(R.string.score, it),
                color = MaterialTheme.colorScheme.onPrimary
            )
            VerticalDivider(
                thickness = 2.dp,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.height(20.dp)
            )
        }

        animeDetails.episodes?.let {
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                text = stringResource(R.string.episodes, it),
                color = MaterialTheme.colorScheme.onPrimary
            )

            VerticalDivider(
                thickness = 2.dp,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.height(20.dp)
            )
        }
        animeDetails.status?.let {
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                text = it,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

@Preview
@Composable
private fun BasicInfo_Preview() {
    AnimeListTheme {
        BasicInfo(animeDetails = AnimeDetails.mock())
    }
}
