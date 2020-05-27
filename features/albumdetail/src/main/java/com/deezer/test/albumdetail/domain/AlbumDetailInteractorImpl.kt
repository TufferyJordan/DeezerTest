package com.deezer.test.albumdetail.domain

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class AlbumDetailInteractorImpl(
    private val repository: AlbumDetailRepository,
    private val presenter: AlbumDetailPresenter,
    private val viewScope: CoroutineScope
) : AlbumDetailInteractor {

    private val ioScope = CoroutineScope(Dispatchers.IO + Job())

    override fun load(albumId: Int) {
        ioScope.launch {
            val data = repository.getAlbumDetail(albumId)
            viewScope.launch {
                data?.let {
                    presenter.presentAlbumDetail(
                        AlbumDetailDto(
                            data.coverImage,
                            data.albumName,
                            data.tracksNumber,
                            data.artistImage,
                            data.artistName,
                            data.albumReleaseDate,
                            it.explicit
                        )
                    )
                } ?: run {
                    presenter.presentError(AlbumDetailException())
                }
            }
        }
    }
}