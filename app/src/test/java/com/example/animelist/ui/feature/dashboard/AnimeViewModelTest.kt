package com.example.animelist.ui.feature.dashboard

import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.map
import app.cash.turbine.test
import com.example.animelist.MainDispatcherRule
import com.example.animelist.domain.mockModel.mock
import com.example.animelist.domain.model.Media
import com.example.animelist.domain.repository.MediaRepository
import com.example.animelist.domain.repository.MediaType
import com.example.animelist.domain.repository.SortType
import com.example.animelist.ui.util.ResourceProvider
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AnimeViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val mockMediaList = (1..10).map { Media.mock(id = it) }
    private val mockLoadStateSource = LoadStates(
        refresh = LoadState.Loading,
        prepend = LoadState.NotLoading(endOfPaginationReached = false),
        append = LoadState.NotLoading(endOfPaginationReached = false)
    )
    private val mockLoadState = CombinedLoadStates(
        refresh = LoadState.Loading,
        prepend = LoadState.NotLoading(endOfPaginationReached = false),
        append = LoadState.NotLoading(endOfPaginationReached = false),
        source = mockLoadStateSource
    )
    private fun createSut(
        mediaRepository: MediaRepository = mockk {
            every { this@mockk.getTopMedia(type = MediaType.Anime, sortType = any()) } returns flowOf(PagingData.from(mockMediaList))
        },
        resourceProvider: ResourceProvider = mockk {
            every { stringForRes(any()) } returns "Error"
        }
    ) = AnimeViewModel(mediaRepository = mediaRepository, resourceProvider = resourceProvider)

    @Test
    fun `init viewmodel should update animeList and viewState`() = runTest {
        val sut = createSut()
        advanceUntilIdle()
        val returnedMediaList = mutableListOf<Media>()
        sut.mediaState.value.map { anime ->
            returnedMediaList.add(anime)
        }
        returnedMediaList.mapIndexed { index: Int, media: Media ->
            assertEquals(mockMediaList[index], media)
        }
        assertFalse(sut.viewState.value.isLoading)
    }

    @Test
    fun `onLoadStateReceived should correctly update viewState when refresh loadState is loading`() = runTest {
        val sut = createSut()
        sut.onLoadStateReceived(mockLoadState)
        advanceUntilIdle()
        assertTrue(sut.viewState.value.isRefreshing)
        assertFalse(sut.viewState.value.isAppendingLoading)
    }

    @Test
    fun `onLoadStateReceived should correctly update viewState when append loadState is loading`() = runTest {
        val sut = createSut()
        val appendingLoadStateSource = mockLoadStateSource.copy(refresh = LoadState.NotLoading(endOfPaginationReached = false), append = LoadState.Loading)
        sut.onLoadStateReceived(
            CombinedLoadStates(
                refresh = LoadState.NotLoading(endOfPaginationReached = false),
                prepend = LoadState.NotLoading(endOfPaginationReached = false),
                append = LoadState.Loading,
                source = appendingLoadStateSource
            )
        )
        advanceUntilIdle()
        assertTrue(sut.viewState.value.isAppendingLoading)
        assertFalse(sut.viewState.value.isRefreshing)
    }

    @Test
    fun `onLoadStateReceived should correctly update viewState when append loadState is error`() = runTest {
        val sut = createSut()
        val appendingLoadStateSource = mockLoadStateSource.copy(
            refresh = LoadState.NotLoading(endOfPaginationReached = false),
            append = LoadState.Error(
                Throwable("Appending Error!")
            )
        )
        sut.onLoadStateReceived(
            CombinedLoadStates(
                refresh = LoadState.NotLoading(endOfPaginationReached = false),
                prepend = LoadState.NotLoading(endOfPaginationReached = false),
                append = LoadState.Error(Throwable("Appending Error!")),
                source = appendingLoadStateSource
            )
        )
        advanceUntilIdle()
        assertEquals("Appending Error!", sut.viewState.value.appendError)
        assertFalse(sut.viewState.value.isRefreshing)
    }

    @Test
    fun `onLoadStateReceived should correctly update viewState when refresh loadState is error`() = runTest {
        val sut = createSut()
        sut.eventFlow.test {
            val appendingLoadStateSource = mockLoadStateSource.copy(
                refresh = LoadState.Error(
                    Throwable("Appending Error!")
                ),
                append = LoadState.NotLoading(endOfPaginationReached = false)
            )
            sut.onLoadStateReceived(
                CombinedLoadStates(
                    refresh = LoadState.Error(Throwable("Appending Error!")),
                    prepend = LoadState.NotLoading(endOfPaginationReached = false),
                    append = LoadState.NotLoading(endOfPaginationReached = false),
                    source = appendingLoadStateSource
                )
            )
            advanceUntilIdle()
            assertEquals(awaitItem(), AnimeViewModel.Event.RefreshError("Appending Error!"))
        }
    }

    @Test
    fun `onSortTypeClick should update viewState sort type and call getTopAnime by AnimeRepository`() = runTest {
        val mockMediaRepository: MediaRepository = mockk {
            every { this@mockk.getTopMedia(type = MediaType.Anime, sortType = any()) } returns flowOf(androidx.paging.PagingData.from(mockMediaList))
        }
        val sut = createSut(mediaRepository = mockMediaRepository)
        sut.onSortTypeClick(sortType = SortType.Popular)
        assertEquals(AnimeViewModel.ViewState(selectedSortType = SortType.Popular), sut.viewState.value)
        advanceUntilIdle()
        verify { mockMediaRepository.getTopMedia(type = MediaType.Anime, sortType = SortType.Popular) }
    }
}
