package com.example.animelist.ui.feature.detail

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.animelist.R
import com.example.animelist.domain.mockModel.mock
import com.example.animelist.domain.model.MediaDetails
import com.example.animelist.ui.component.FullScreenLoading
import com.example.animelist.ui.feature.detail.component.BasicInfo
import com.example.animelist.ui.feature.detail.component.FadedImage
import com.example.animelist.ui.feature.detail.component.StreamingEpisodes
import com.example.animelist.ui.theme.AnimeListTheme
import com.example.animelist.ui.theme.AppTheme

@Composable
fun MediaDetailsScreen(
    viewModel: MediaDetailsViewModel = hiltViewModel()
) {
    val viewState by viewModel.viewState.collectAsStateWithLifecycle()

    when (viewState) {
        MediaDetailsViewModel.ViewState.Loading -> FullScreenLoading()
        is MediaDetailsViewModel.ViewState.Success -> MediaDetailsSuccessScreen(
            viewState = (viewState as MediaDetailsViewModel.ViewState.Success),
            onBookmarkClick = { viewModel.onBookmarkClick() }
        )
        is MediaDetailsViewModel.ViewState.Error -> MediaDetailsErrorScreen((viewState as MediaDetailsViewModel.ViewState.Error).message)
    }
}

@Composable
private fun MediaDetailsSuccessScreen(
    viewState: MediaDetailsViewModel.ViewState.Success,
    modifier: Modifier = Modifier,
    onBookmarkClick: () -> Unit
) {
    val scrollState = rememberScrollState()
    Box {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(color = Color.Black)
                .verticalScroll(scrollState)
        ) {
            FadedImage(imageUrl = viewState.mediaDetails.bannerImage)

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = AppTheme.dimension.spaceL, horizontal = AppTheme.dimension.spaceS),
                verticalArrangement = Arrangement.spacedBy(AppTheme.dimension.spaceM),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                viewState.mediaDetails.title?.let {
                    Text(
                        text = it,
                        fontSize = 24.sp,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }

                BasicInfo(mediaDetails = viewState.mediaDetails)

                viewState.mediaDetails.description?.let {
                    ExpandableTextView(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = AppTheme.dimension.spaceM),
                        text = it,
                        textLengthToCutOff = 400
                    )
                }
                StreamingEpisodes(streamingEpisode = viewState.mediaDetails.streamingEpisodes)
            }
        }
        BookMarkButton(
            modifier = Modifier.align(Alignment.TopEnd),
            isBookmarked = viewState.isBookmarked,
            onClick = onBookmarkClick
        )
    }
}

@Composable
private fun BookMarkButton(
    modifier: Modifier = Modifier,
    isBookmarked: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier.padding(AppTheme.dimension.spaceL)
    ) {
        Icon(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .clickable { onClick.invoke() },
            painter = painterResource(R.drawable.ic_star),
            tint = if (isBookmarked) AppTheme.colors.bookmark else AppTheme.colors.bookmarkOff,
            contentDescription = null
        )
    }
}

@Composable
private fun ExpandableTextView(
    modifier: Modifier = Modifier,
    text: String,
    textLengthToCutOff: Int
) {
    var isExpanded by remember {
        mutableStateOf(false)
    }
    if (text.length < textLengthToCutOff) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = AppTheme.dimension.spaceM),
            textAlign = TextAlign.Center,
            text = text,
            color = MaterialTheme.colorScheme.onPrimary
        )
    } else {
        Column(
            modifier = modifier
                .animateContentSize(animationSpec = tween(500)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = if (isExpanded) text else text.take(textLengthToCutOff),
                color = MaterialTheme.colorScheme.onPrimary
            )
            Spacer(modifier = Modifier.height(AppTheme.dimension.spaceL))
            Text(
                text = if (isExpanded) stringResource(R.string.show_less) else stringResource(R.string.show_more),
                modifier = Modifier
                    .width(300.dp)
                    .border(width = 2.dp, color = MaterialTheme.colorScheme.onPrimary, shape = RoundedCornerShape(AppTheme.dimension.spaceL))
                    .clickable { isExpanded = !isExpanded }
                    .padding(AppTheme.dimension.spaceM),
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimary,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun MediaDetailsErrorScreen(
    errorMessage: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = errorMessage, color = Color.Red)
    }
}

@Preview
@Composable
private fun MediaDetailsSuccessScreen_Preview() {
    AnimeListTheme {
        MediaDetailsSuccessScreen(
            viewState = MediaDetailsViewModel.ViewState.Success(MediaDetails.mock(), isBookmarked = true),
            onBookmarkClick = {}
        )
    }
}

@Preview
@Composable
private fun MediaDetailsErrorScreen_Preview() {
    AnimeListTheme {
        MediaDetailsErrorScreen(errorMessage = "Error loading the anime details")
    }
}
