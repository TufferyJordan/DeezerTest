package com.deezer.test.albumlist.domain

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class AlbumListInteractorImpl(
    private val repository: AlbumListRepository,
    private val presenter: AlbumListPresenter,
    private val viewScope: CoroutineScope
) : AlbumListInteractor {

    private val ioScope = CoroutineScope(Dispatchers.IO + Job())

    override fun load() {
        presenter.presentLoading()
        ioScope.launch {
            val data = repository.getAlbumList()
            viewScope.launch {
                data?.let {
                    presenter.presentAlbumList(
                        AlbumListDto(
                            data.list.map {
                                AlbumDto(
                                    it.id,
                                    it.title,
                                    it.cover
                                )
                            }
                        )
                    )
                } ?: run {
                    presenter.presentError(AlbumListException())
                }
            }
        }
    }
}