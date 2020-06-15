package com.deezer.test.albumlist

import android.view.View
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.deezer.test.albumlist.AlbumListViewModel
import com.deezer.test.data.albumlist.GetAlbumListRepository
import com.deezer.test.data.model.AlbumData
import com.deezer.test.data.model.AlbumListData
import com.deezer.test.interfaces.routing.AlbumListRouter
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

class AlbumListViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: AlbumListViewModel
    private lateinit var repository: GetAlbumListRepository
    private lateinit var router: AlbumListRouter
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        repository = mockk(relaxed = true)
        router = mockk(relaxed = true)
        viewModel =
            AlbumListViewModel(repository, router)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }


    @Test
    fun `Given loading, When an error occur, Then it should be displayed`() {
        coEvery { repository.getAlbumList() } returns null

        viewModel.load()

        coVerify { repository.getAlbumList() }

        Truth.assertThat(viewModel.dotViewVisibilityLiveData.value).isEqualTo(View.GONE)
        Truth.assertThat(viewModel.errorTextVisibilityLiveData.value).isEqualTo(View.VISIBLE)
        Truth.assertThat(viewModel.recyclerViewVisibilityLiveData.value).isEqualTo(View.GONE)
        Truth.assertThat(viewModel.errorTextLiveData.value)
            .isEqualTo("An error has occurred while requesting the album list")
    }

    @Test
    fun `When loading, Then it should be displayed`() {
        coEvery { repository.getAlbumList() } coAnswers {
            delay(1000)
            AlbumListData(list = listOf())
        }

        viewModel.load()

        Truth.assertThat(viewModel.dotViewVisibilityLiveData.value).isEqualTo(View.VISIBLE)
        Truth.assertThat(viewModel.errorTextVisibilityLiveData.value).isEqualTo(View.GONE)
        Truth.assertThat(viewModel.recyclerViewVisibilityLiveData.value).isEqualTo(View.GONE)
        Truth.assertThat(viewModel.errorTextLiveData.value)
            .isEmpty()
    }

    @Test
    fun `Given loading, When data are received, Then it should be displayed`() {
        coEvery { repository.getAlbumList() } returns AlbumListData(
            list = listOf(
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
            )
        )

        viewModel.load()

        coVerify { repository.getAlbumList() }

        Truth.assertThat(viewModel.dotViewVisibilityLiveData.value).isEqualTo(View.GONE)
        Truth.assertThat(viewModel.errorTextVisibilityLiveData.value).isEqualTo(View.GONE)
        Truth.assertThat(viewModel.recyclerViewVisibilityLiveData.value).isEqualTo(View.VISIBLE)
        Truth.assertThat(viewModel.errorTextLiveData.value)
            .isEmpty()
        Truth.assertThat(viewModel.albumListLiveData.value?.size).isEqualTo(1)
    }

}