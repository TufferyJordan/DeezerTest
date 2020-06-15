package com.deezer.test.albumdetail

import android.content.res.Resources
import android.view.View
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.deezer.test.data.GetAlbumRepository
import com.deezer.test.data.model.AlbumData
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

class AlbumDetailViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: AlbumDetailViewModel
    private lateinit var repository: GetAlbumRepository
    private lateinit var resources: Resources
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        repository = mockk(relaxed = true)
        resources = mockk(relaxed = true)
        viewModel = AlbumDetailViewModel(repository, resources)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }


    @Test
    fun `Given loading, When an error occur, Then it should be displayed`() {
        coEvery { repository.getAlbumDetail(0) } returns null

        viewModel.load(0)

        coVerify { repository.getAlbumDetail(0) }

        Truth.assertThat(viewModel.errorVisibilityLiveData.value).isEqualTo(View.VISIBLE)
        Truth.assertThat(viewModel.headerVisibilityLiveData.value).isEqualTo(View.GONE)
        Truth.assertThat(viewModel.errorTextLiveData.value)
            .isEqualTo("An error has occurred during the album request")
    }

    @Test
    fun `When loading, Then it should be displayed`() {
        coEvery { repository.getAlbumDetail(0) } coAnswers {
            delay(1000)
            AlbumData(
                id = 1,
                albumName = "John Doe's album",
                artistName = "John Doe",
                albumReleaseDate = "date",
                coverImage = "URL",
                tracksNumber = 1,
                explicit = true,
                artistImage = "URL2"
            )
        }

        viewModel.load(0)

        Truth.assertThat(viewModel.errorVisibilityLiveData.value).isEqualTo(View.GONE)
        Truth.assertThat(viewModel.headerVisibilityLiveData.value).isEqualTo(View.VISIBLE)
        Truth.assertThat(viewModel.errorTextLiveData.value)
            .isNull()
    }

    @Test
    fun `Given loading, When data are received, Then it should be displayed`() {
        coEvery { resources.getQuantityString(R.plurals.album_detail_tracks_data, 1, 1, "date") } returns
                "1 track, date"
        coEvery { repository.getAlbumDetail(0) } returns AlbumData(
            id = 1,
            albumName = "John Doe's album",
            artistName = "John Doe",
            albumReleaseDate = "date",
            coverImage = "URL",
            tracksNumber = 1,
            explicit = true,
            artistImage = "URL2"
        )

        viewModel.load(0)

        coVerify { repository.getAlbumDetail(0) }

        Truth.assertThat(viewModel.errorVisibilityLiveData.value).isEqualTo(View.GONE)
        Truth.assertThat(viewModel.headerVisibilityLiveData.value).isEqualTo(View.VISIBLE)
        Truth.assertThat(viewModel.errorTextLiveData.value)
            .isNull()

        Truth.assertThat(viewModel.albumDetailsLiveData.value).isEqualTo("1 track, date")
        Truth.assertThat(viewModel.albumNameLiveData.value).isEqualTo("John Doe's album")
        Truth.assertThat(viewModel.albumUrlLiveData.value).isEqualTo("URL")
        Truth.assertThat(viewModel.artistNameTextLiveData.value).isEqualTo("John Doe")
        Truth.assertThat(viewModel.artistUrlLiveData.value).isEqualTo("URL2")
    }

}