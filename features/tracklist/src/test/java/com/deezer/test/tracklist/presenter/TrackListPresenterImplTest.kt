package com.deezer.test.tracklist.presenter

import com.deezer.test.tracklist.domain.TrackDto
import com.deezer.test.tracklist.domain.TrackListDto
import com.deezer.test.tracklist.domain.TrackListException
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class TrackListPresenterImplTest {
    private lateinit var presenterImpl: TrackListPresenterImpl
    private lateinit var view: TrackListView

    @Before
    fun setUp() {
        view = mockk(relaxed = true)
        presenterImpl = TrackListPresenterImpl(view)
    }

    @Test
    fun `When presenter present error, Then view should display error message`() {
        presenterImpl.presentError(TrackListException())

        verify { view.displayError(withArg { assert(it == TrackListException().message) }) }
    }

    @Test
    fun `When presenter present loader, Then view should display loader`() {
        presenterImpl.presentLoading()

        verify { view.displayLoading() }
    }

    @Test
    fun `When presenter present data, Then view should display data`() {
        presenterImpl.presentTrackList(TrackListDto(
            listOf(
                TrackDto(
                    title = "Song",
                    duration = 126,
                    previewURL = "URL"
                )
            )
        ))

        verify { view.displayTrackList(withArg {
            assert(it.trackList.size == 1)
            it.trackList[0].let { track ->
                assert(track.title == "Song")
                assert(track.previewURL == "URL")
                assert(track.duration == "2:06")
            }
        }) }
    }
}