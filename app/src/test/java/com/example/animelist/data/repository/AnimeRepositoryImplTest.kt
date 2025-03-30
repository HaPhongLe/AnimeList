package com.example.animelist.data.repository

import androidx.paging.testing.asSnapshot
import com.example.animelist.MainDispatcherRule
import com.example.animelist.data.api.AnimeApi
import com.example.animelist.domain.mockModel.mock
import com.example.animelist.domain.model.Anime
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class AnimeRepositoryImplTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val firstSetOfAnime = (1..10).map { Anime.mock(id = it) }
    private val secondSetOfAnime = (11..20).map { Anime.mock(id = it) }
    private val thirdSetOfAnime = (21..30).map { Anime.mock(id = it) }

    private fun createSut(
        animeApi: AnimeApi = mockk {
            coEvery { getTopTrendingAnime(1) } returns firstSetOfAnime
            coEvery { getTopTrendingAnime(2) } returns secondSetOfAnime
            coEvery { getTopTrendingAnime(3) } returns thirdSetOfAnime
        }
    ) = AnimeRepositoryImpl(animeApi)

    @Test
    fun `getTrendingAnime should correctly return list of anime`() = runTest {
        val repo = createSut()
        val flow = repo.getTrendingAnime()
        val items = flow.asSnapshot()
        assertEquals(firstSetOfAnime + secondSetOfAnime, items) // account for some prefetch
    }

    @Test
    fun `getTrendingAnime should correctly return list of anime when scrolling`() = runBlocking {
        val repo = createSut()
        val flow = repo.getTrendingAnime()
        val items = flow.asSnapshot {
            scrollTo(19)
            delay(5000)
        }
        assertEquals(firstSetOfAnime + secondSetOfAnime + thirdSetOfAnime, items)
    }
}
