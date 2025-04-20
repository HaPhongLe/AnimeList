package com.example.animelist.data.repository

import androidx.paging.testing.asSnapshot
import com.example.animelist.MainDispatcherRule
import com.example.animelist.data.api.MediaApi
import com.example.animelist.domain.mockModel.mock
import com.example.animelist.domain.model.Media
import com.example.animelist.domain.model.MediaDetails
import com.example.animelist.domain.repository.MediaType
import com.example.animelist.domain.repository.SortType
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class MediaEntityRepositoryImplTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val firstSetOfMedia = (1..10).map { Media.mock(id = it) }
    private val secondSetOfMedia = (11..20).map { Media.mock(id = it) }
    private val thirdSetOfMedia = (21..30).map { Media.mock(id = it) }

    private fun createSut(
        mediaApi: MediaApi = mockk {
            coEvery { getTopMedia(1, type = MediaType.Anime, sortType = SortType.Trending) } returns firstSetOfMedia
            coEvery { getTopMedia(2, type = MediaType.Anime, sortType = SortType.Trending) } returns secondSetOfMedia
            coEvery { getTopMedia(3, type = MediaType.Anime, sortType = SortType.Trending) } returns thirdSetOfMedia
            coEvery { getMediaDetailsById(any()) } returns MediaDetails.mock()
        }
    ) = MediaRepositoryImpl(mediaApi)

    @Test
    fun `getTrendingAnime should correctly return list of anime`() = runTest {
        val repo = createSut()
        val flow = repo.getTopMedia(type = MediaType.Anime, sortType = SortType.Trending)
        val items = flow.asSnapshot()
        assertEquals(firstSetOfMedia + secondSetOfMedia, items) // account for some prefetch
    }

    @Test
    fun `getTrendingAnime should correctly return list of anime when scrolling`() = runTest {
        val repo = createSut()
        val flow = repo.getTopMedia(type = MediaType.Anime, sortType = SortType.Trending)
        val items = flow.asSnapshot {
            scrollTo(19)
            delay(5000)
        }
        assertEquals(firstSetOfMedia + secondSetOfMedia + thirdSetOfMedia, items)
    }

    @Test
    fun `getAnimeDetailsById should correctly return animeDetails`() = runTest {
        val repo = createSut()
        val result = repo.getMediaById(1)
        advanceUntilIdle()
        assertEquals(MediaDetails.Companion.mock(), result)
    }
}
