package com.example.animelist.ui.feature.detail

import app.cash.turbine.test
import com.example.animelist.MainDispatcherRule
import com.example.animelist.domain.mockModel.mock
import com.example.animelist.domain.model.MediaDetails
import com.example.animelist.domain.repository.MediaRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class MediaDetailsViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private fun createSut(
        mediaRepository: MediaRepository = mockk {
            coEvery { getMediaById(any()) } returns MediaDetails.mock()
        }
    ) = AnimeDetailsViewModel(mediaRepository)

    @Test
    fun `onResume would correctly update viewState with animeDetails fetched`() = runTest {
        val sut = createSut()
        sut.viewState.test {
            assertEquals(AnimeDetailsViewModel.ViewState.Loading, awaitItem())
            sut.onResume(animeId = 1)
            assertEquals(AnimeDetailsViewModel.ViewState.Success(MediaDetails.mock()), awaitItem())
        }
    }

    @Test
    fun `onResume would correctly update viewState if api calls fails`() = runTest {
        val sut = createSut(
            mediaRepository = mockk {
                coEvery { getMediaById(any()) } throws Exception("Fails to fetch anime")
            }
        )
        sut.viewState.test {
            assertEquals(AnimeDetailsViewModel.ViewState.Loading, awaitItem())
            sut.onResume(animeId = 1)
            assertEquals(AnimeDetailsViewModel.ViewState.Error("Fails to fetch anime"), awaitItem())
        }
    }
}
