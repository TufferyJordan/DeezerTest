package com.deezer.test.albumdetail.presenter

import android.content.res.Resources
import com.deezer.test.albumdetail.domain.AlbumDetailDto
import com.deezer.test.albumdetail.domain.AlbumDetailPresenter
import com.deezer.test.albumdetail.domain.AlbumDetailViewModel
import com.deezer.test.design.R

class AlbumDetailPresenterImpl(
    private val view: AlbumDetailView,
    private val resources: Resources
) : AlbumDetailPresenter {
    override fun presentAlbumDetail(dto: AlbumDetailDto) {
        val detailsAlbum = resources.getQuantityString(
            R.plurals.album_detail_tracks_data,
            dto.tracksNumber,
            dto.tracksNumber,
            dto.albumReleaseDate
        )

        view.displayAlbumDetail(
            AlbumDetailViewModel(
                dto.coverImage,
                dto.albumName,
                dto.artistImage,
                dto.artistName,
                detailsAlbum,
                dto.explicit
            )
        )
    }

    override fun presentError(exception: Exception) {
        view.displayError(exception.message ?: "An error has occurred")
    }
}