package com.deezer.test.albumdetail.presenter

import com.deezer.test.albumdetail.domain.AlbumDetailDto
import com.deezer.test.albumdetail.domain.AlbumDetailPresenter
import com.deezer.test.albumdetail.domain.AlbumDetailViewModel

class AlbumDetailPresenterImpl(
    private val view: AlbumDetailView
) : AlbumDetailPresenter {
    override fun presentAlbumDetail(dto: AlbumDetailDto) {
        view.displayAlbumDetail(
            AlbumDetailViewModel(
                dto.coverImage,
                dto.albumName,
                dto.tracksNumber,
                dto.artistImage,
                dto.artistName,
                dto.albumReleaseDate
            )
        )
    }
}