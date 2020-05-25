package com.deezer.test.albumdetail.presenter

import com.deezer.test.albumdetail.domain.AlbumDetailViewModel


interface AlbumDetailView {
    fun displayAlbumDetail(viewModel: AlbumDetailViewModel)
}