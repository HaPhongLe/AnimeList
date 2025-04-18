package com.example.animelist.data.repository

import androidx.paging.testing.asSnapshot
import com.example.animelist.MainDispatcherRule
import com.example.animelist.data.api.AnimeApi
import com.example.animelist.domain.mockModel.mock
import com.example.animelist.domain.model.Anime
import com.example.animelist.domain.model.MediaDetails
import com.example.animelist.domain.repository.SortType
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class MediaRepositoryImplTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val firstSetOfAnime = (1..10).map { Anime.mock(id = it) }
    private val secondSetOfAnime = (11..20).map { Anime.mock(id = it) }
    private val thirdSetOfAnime = (21..30).map { Anime.mock(id = it) }

    private fun createSut(
        animeApi: AnimeApi = mockk {
            coEvery { getTopAnime(1, sortType = SortType.Trending) } returns firstSetOfAnime
            coEvery { getTopAnime(2, sortType = SortType.Trending) } returns secondSetOfAnime
            coEvery { getTopAnime(3, sortType = SortType.Trending) } returns thirdSetOfAnime
            coEvery { getMediaDetailsById(any()) } returns MediaDetails.mock()
        }
    ) = MediaRepositoryImpl(animeApi)

    @Test
    fun `getTrendingAnime should correctly return list of anime`() = runTest {
        val repo = createSut()
        val flow = repo.getTopAnime(sortType = SortType.Trending)
        val items = flow.asSnapshot()
        assertEquals(firstSetOfAnime + secondSetOfAnime, items) // account for some prefetch
    }

    @Test
    fun `getTrendingAnime should correctly return list of anime when scrolling`() = runTest {
        val repo = createSut()
        val flow = repo.getTopAnime(sortType = SortType.Trending)
        val items = flow.asSnapshot {
            scrollTo(19)
            delay(5000)
        }
        assertEquals(firstSetOfAnime + secondSetOfAnime + thirdSetOfAnime, items)
    }

    @Test
    fun `getAnimeDetailsById should correctly return animeDetails`() = runTest {
        val repo = createSut()
        val result = repo.getMediaById(1)
        advanceUntilIdle()
        assertEquals(MediaDetails.Companion.mock(), result)
    }
}
