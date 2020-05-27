package com.deezer.test.albumlist.presenter

import com.deezer.test.albumlist.domain.AlbumDto
import com.deezer.test.albumlist.domain.AlbumListDto
import com.deezer.test.albumlist.domain.AlbumListException
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class AlbumListPresenterImplTest {
    private lateinit var presenter: AlbumListPresenterImpl
    private lateinit var view: AlbumListView

    @Before
    fun setUp() {
        view = mockk(relaxed = true)
        presenter = AlbumListPresenterImpl(view)
    }

    @Test
    fun `When presenter present loading, Then view display loader`() {
        presenter.presentLoading()

        verify { view.displayLoading() }
    }

    @Test
    fun `When presenter present error, Then view display error message`() {
        presenter.presentError(AlbumListException())

        verify {
            view.displayError(withArg {
                assert(it == AlbumListException().message)
            })
        }
    }

    @Test
    fun `When presenter present data, Then view display viewModel`() {
        presenter.presentAlbumList(
            AlbumListDto(
                listOf(
                    AlbumDto(
                        id = 1,
                        title = "Album",
                        cover = "URL"
                    )
                )
            )
        )

        verify {
            view.displayAlbumList(withArg {
                assert(it.list.size == 1)
                it.list[0].let { vm ->
                    assert(vm.id == 1)
                    assert(vm.title == "Album")
                    assert(vm.cover == "URL")
                }
            })
        }
    }
}