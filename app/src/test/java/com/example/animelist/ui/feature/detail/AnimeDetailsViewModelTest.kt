package com.example.animelist.ui.feature.detail

import app.cash.turbine.test
import com.example.animelist.MainDispatcherRule
import com.example.animelist.domain.mockModel.mock
import com.example.animelist.domain.model.AnimeDetails
import com.example.animelist.domain.repository.AnimeRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class AnimeDetailsViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private fun createSut(
        animeRepository: AnimeRepository = mockk {
            coEvery { getAnimeById(any()) } returns AnimeDetails.mock()
        }
    ) = AnimeDetailsViewModel(animeRepository)

    @Test
    fun `onResume would correctly update viewState with animeDetails fetched`() = runTest {
        val sut = createSut()
        sut.viewState.test {
            assertEquals(AnimeDetailsViewModel.ViewState.Loading, awaitItem())
            sut.onResume(animeId = 1)
            assertEquals(AnimeDetailsViewModel.ViewState.Success(AnimeDetails.mock()), awaitItem())
        }
    }

    @Test
    fun `onResume would correctly update viewState if api calls fails`() = runTest {
        val sut = createSut(
            animeRepository = mockk {
                coEvery { getAnimeById(any()) } throws Exception("Fails to fetch anime")
            }
        )
        sut.viewState.test {
            assertEquals(AnimeDetailsViewModel.ViewState.Loading, awaitItem())
            sut.onResume(animeId = 1)
            assertEquals(AnimeDetailsViewModel.ViewState.Error("Fails to fetch anime"), awaitItem())
        }
    }
}
