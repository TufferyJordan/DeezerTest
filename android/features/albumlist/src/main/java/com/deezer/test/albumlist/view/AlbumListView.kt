package com.deezer.test.albumlist.view

import com.deezer.test.albumlist.presenter.AlbumListViewModel

interface AlbumListView {
    fun displayAlbumList(viewModel: AlbumListViewModel)
}