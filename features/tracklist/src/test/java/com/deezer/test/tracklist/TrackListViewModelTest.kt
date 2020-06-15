package com.deezer.test.tracklist

import android.view.View
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.deezer.test.data.GetTrackListRepository
import com.deezer.test.data.model.TrackData
import com.deezer.test.data.model.TrackListData
import com.deezer.test.interfaces.player.PlayerController
import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class TrackListViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: TrackListViewModel
    private lateinit var repository: GetTrackListRepository
    private lateinit var playerController: PlayerController
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        repository = mockk(relaxed = true)
        playerController = mockk(relaxed = true)
        viewModel =
            TrackListViewModel(repository, playerController)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }


    @Test
    fun `Given loading, When an error occur, Then it should be displayed`() {
        coEvery { repository.getTrackList(0) } returns null

        viewModel.load(0)

        coVerify { repository.getTrackList(0) }

        Truth.assertThat(viewModel.loaderVisibilityLiveData.value).isEqualTo(View.GONE)
        Truth.assertThat(viewModel.errorTextVisibilityLiveData.value).isEqualTo(View.VISIBLE)
        Truth.assertThat(viewModel.recyclerViewVisibilityLiveData.value).isEqualTo(View.GONE)
        Truth.assertThat(viewModel.errorTextLiveData.value)
            .isEqualTo("An error has occurred while requesting the tracklist")
    }

    @Test
    fun `When loading, Then it should be displayed`() {
        coEvery { repository.getTrackList(0) } coAnswers {
            delay(1000)
            TrackListData(trackList = listOf())
        }

        viewModel.load(0)

        Truth.assertThat(viewModel.loaderVisibilityLiveData.value).isEqualTo(View.VISIBLE)
        Truth.assertThat(viewModel.errorTextVisibilityLiveData.value).isEqualTo(View.GONE)
        Truth.assertThat(viewModel.recyclerViewVisibilityLiveData.value).isEqualTo(View.GONE)
        Truth.assertThat(viewModel.errorTextLiveData.value)
            .isNull()
    }

    @Test
    fun `Given loading, When data are received, Then it should be displayed`() {
        coEvery { repository.getTrackList(0) } returns TrackListData(
            trackList = listOf(
                TrackData(
                    title = "Song",
                    duration = 1,
                    previewURL = "URL",
                    explicit = true
                )
            )
        )

        viewModel.load(0)

        coVerify { repository.getTrackList(0) }

        Truth.assertThat(viewModel.loaderVisibilityLiveData.value).isEqualTo(View.GONE)
        Truth.assertThat(viewModel.errorTextVisibilityLiveData.value).isEqualTo(View.GONE)
        Truth.assertThat(viewModel.recyclerViewVisibilityLiveData.value).isEqualTo(View.VISIBLE)
        Truth.assertThat(viewModel.errorTextLiveData.value)
            .isNull()
        Truth.assertThat(viewModel.tracklistItemsLiveData.value?.size).isEqualTo(2)
    }

}