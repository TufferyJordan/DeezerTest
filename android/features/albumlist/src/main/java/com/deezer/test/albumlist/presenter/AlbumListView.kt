package com.deezer.test.albumlist.presenter

import com.deezer.test.albumlist.domain.AlbumListViewModel

interface AlbumListView {
    fun displayAlbumList(viewModel: AlbumListViewModel)
    fun displayError(error: String)
}