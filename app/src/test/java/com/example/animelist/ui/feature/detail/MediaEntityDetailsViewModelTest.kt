package com.example.animelist.ui.feature.detail

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.example.animelist.MainDispatcherRule
import com.example.animelist.domain.mockModel.mock
import com.example.animelist.domain.model.MediaDetails
import com.example.animelist.domain.repository.MediaRepository
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class MediaEntityDetailsViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private fun createSut(
        mediaRepository: MediaRepository = mockk {
            coEvery { getMediaDetailsById(any()) } returns MediaDetails.mock()
            every { getNumberOfSavedMediaByIdFlow(1) } returns flowOf(0)
        },
        savedStateHandle: SavedStateHandle = mockk<SavedStateHandle> {
            every { get<Int>("id") } returns 1
        }
    ) = MediaDetailsViewModel(mediaRepository, savedStateHandle)

    @Test
    fun `onResume would correctly update viewState with animeDetails fetched`() = runTest {
        val sut = createSut()
        sut.viewState.test {
            assertEquals(MediaDetailsViewModel.ViewState.Loading, awaitItem())
            assertEquals(MediaDetailsViewModel.ViewState.Success(MediaDetails.mock(), isBookmarked = false), awaitItem())
        }
    }

    @Test
    fun `onResume would correctly update viewState if api calls fails`() = runTest {
        val sut = createSut(
            mediaRepository = mockk {
                coEvery { getMediaDetailsById(any()) } throws Exception("Fails to fetch anime")
            }
        )
        sut.viewState.test {
            assertEquals(MediaDetailsViewModel.ViewState.Loading, awaitItem())
            assertEquals(MediaDetailsViewModel.ViewState.Error("Fails to fetch anime"), awaitItem())
        }
    }
}
