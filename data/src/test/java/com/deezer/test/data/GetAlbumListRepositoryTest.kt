package com.deezer.test.data

import com.deezer.test.data.albumlist.GetAlbumListRepository
import com.deezer.test.interfaces.AlbumStore
import com.deezer.test.interfaces.data.Album
import com.deezer.test.interfaces.data.AlbumList
import com.deezer.test.interfaces.data.Artist
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import java.io.IOException

class GetAlbumListRepositoryTest {

    private lateinit var repository: GetAlbumListRepository
    private lateinit var store: AlbumStore

    @Before
    fun setUp() {
        store = mockk()
        repository = GetAlbumListRepository(store)
    }

    @Test
    fun `When store throw IOException, Then repository return null`() {
        coEvery { store.getAlbumList() } throws IOException()

        runBlocking {
            val data = repository.getAlbumList()
            assert(data == null)
        }
    }

    @Test
    fun `When store return real value, Then repository parse the data needed`() {
        coEvery { store.getAlbumList() } returns AlbumList(
            data = listOf(
                Album(
                    id = 1,
                    title = "John Doe's Album",
                    cover_xl = "URL",
                    nb_tracks = 1,
                    release_date = "date",
                    explicit_lyrics = true,
                    artist = Artist(
                        name = "John Doe",
                        picture_medium = "URL2"
                    )
                )
            ),
            total = 1
        )
        runBlocking {
            val data = repository.getAlbumList()!!
            assert(data.list.size == 1)
            data.list[0].let {
                assert(it.id == 1)
                assert(it.coverImage == "URL")
                assert(it.explicit)
                assert(it.albumReleaseDate == "date")
                assert(it.artistImage == "URL2")
                assert(it.artistName == "John Doe")
                assert(it.tracksNumber == 1)
                assert(it.albumName == "John Doe's Album")
            }

        }
    }
}
