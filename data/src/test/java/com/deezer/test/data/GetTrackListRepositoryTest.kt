package com.deezer.test.data

import com.deezer.test.interfaces.AlbumService
import com.deezer.test.interfaces.data.Track
import com.deezer.test.interfaces.data.TrackList
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import java.io.IOException

class GetTrackListRepositoryTest {

    private lateinit var repository: GetTrackListRepository
    private lateinit var service: AlbumService

    @Before
    fun setUp() {
        service = mockk()
        repository = GetTrackListRepository(service)
    }

    @Test
    fun `When store throw IOException, Then repository return null`() {
        coEvery { service.getTrackList(0) } throws IOException()

        runBlocking {
            val data = repository.getTrackList(0)
            assert(data == null)
        }
    }

    @Test
    fun `When store return real value, Then repository parse the data needed`() {
        coEvery { service.getTrackList(0) } returns TrackList(
            data = listOf(
                Track(
                    title = "John Doe's Song",
                    duration = 1,
                    preview = "URL",
                    explicit_lyrics = true
                )
            )
        )
        runBlocking {
            val data = repository.getTrackList(0)!!
            assert(data.trackList.size == 1)
            data.trackList[0].let {
                assert(it.duration == 1)
                assert(it.title == "John Doe's Song")
                assert(it.previewURL == "URL")
                assert(it.explicit)
            }
        }
    }
}
