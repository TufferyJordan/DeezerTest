package com.deezer.test.albumdetail.presenter

import android.content.res.Resources
import com.deezer.test.design.R
import com.deezer.test.albumdetail.domain.AlbumDetailDto
import com.deezer.test.albumdetail.domain.AlbumDetailException
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class AlbumDetailPresenterImplTest {
    private lateinit var presenterImpl: AlbumDetailPresenterImpl
    private lateinit var resources: Resources
    private lateinit var view: AlbumDetailView

    @Before
    fun setUp() {
        resources = mockk()
        view = mockk(relaxed = true)
        presenterImpl = AlbumDetailPresenterImpl(view, resources)
    }

    @Test
    fun `When presenter present error, Then view should display error message`() {
        presenterImpl.presentError(AlbumDetailException())

        verify { view.displayError(withArg { it == AlbumDetailException().message }) }
    }

    @Test
    fun `When presenter present data, Then view should display parsed data`() {
        every { resources.getQuantityString(R.plurals.album_detail_tracks_data, 1, 1, "date") } returns "ok"
        presenterImpl.presentAlbumDetail(AlbumDetailDto(
            coverImage = "coverURL",
            albumName = "albumName",
            tracksNumber = 1,
            artistName = "artistName",
            artistImage = "artistURL",
            albumReleaseDate = "date",
            explicit = true
        ))

        verify { view.displayAlbumDetail(withArg {
            assert(it.coverImage == "coverURL")
            assert(it.albumName == "albumName")
            assert(it.albumDetail == "ok")
            assert(it.explicit)
            assert(it.artistName == "artistName")
            assert(it.artistImage == "artistURL")
        }) }
    }
}