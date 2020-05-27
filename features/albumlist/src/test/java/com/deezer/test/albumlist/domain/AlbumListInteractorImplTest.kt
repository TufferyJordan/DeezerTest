package com.deezer.test.albumlist.domain

import io.mockk.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import org.junit.Before
import org.junit.Test

class AlbumListInteractorImplTest {

    private lateinit var interactorImpl: AlbumListInteractorImpl
    private lateinit var presenter: AlbumListPresenter
    private lateinit var repository: AlbumListRepository

    @Before()
    fun setUp() {
        presenter = mockk(relaxed = true)
        repository = mockk(relaxed = true)

        interactorImpl = AlbumListInteractorImpl(repository, presenter, CoroutineScope(Job()))
    }

    @Test
    fun `When repository return null value, Then presenter should present error`() {
        coEvery { repository.getAlbumList() } returns null

        interactorImpl.load()

        verify { presenter.presentLoading() }
        coVerify { presenter.presentError(any()) }
    }

    @Test
    fun `When repository return real value, Then presenter should present data`() {
        coEvery { repository.getAlbumList() } returns AlbumListData(
            listOf(
                AlbumData(
                    id = 1,
                    title = "John Doe",
                    cover = "this is an url"
                )
            )
        )

        interactorImpl.load()

        verify { presenter.presentLoading() }
        coVerify { presenter.presentAlbumList(withArg {
            assert(it.list.size == 1)
            it.list[0].apply {
                assert(this.id == 1)
                assert(this.title == "John Doe")
                assert(this.cover == "this is an url")
            }
        }) }
    }
}
