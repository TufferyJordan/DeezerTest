package com.deezer.test.albumlist.view

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deezer.test.data.albumlist.GetAlbumList
import com.deezer.test.data.model.AlbumListData
import com.deezer.test.interfaces.routing.AlbumListRouter
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class AlbumListViewModel(
    private val repository: GetAlbumList,
    private val router: AlbumListRouter
) : ViewModel() {

    private val ioScope = CoroutineScope(Dispatchers.IO + Job())

    private val _albumListLiveData: MutableLiveData<List<Item>> = MutableLiveData()
    val albumListLiveData: LiveData<List<Item>> = _albumListLiveData

    private val _errorTextLiveData: MutableLiveData<String> = MutableLiveData()
    val errorTextLiveData: LiveData<String> = _errorTextLiveData

    private val _recyclerViewVisibilityLiveData: MutableLiveData<Int> = MutableLiveData()
    val recyclerViewVisibilityLiveData: LiveData<Int> = _recyclerViewVisibilityLiveData

    private val _dotViewVisibilityLiveData: MutableLiveData<Int> = MutableLiveData()
    val dotViewVisibilityLiveData: LiveData<Int> = _dotViewVisibilityLiveData

    private val _errorTextVisibilityLiveData: MutableLiveData<Int> = MutableLiveData()
    val errorTextVisibilityLiveData: LiveData<Int> = _errorTextVisibilityLiveData

    fun load() {
        displayLoading()
        ioScope.launch {
            val data = repository.getAlbumList()
            print(data)
            viewModelScope.launch {
                data?.let {
                    displayAlbumList(data)
                } ?: run {
                    displayError("An error has occurred while requesting the album list")
                }
            }
        }
    }

    private fun displayAlbumList(data: AlbumListData) {
        _dotViewVisibilityLiveData.postValue(View.GONE)
        _errorTextVisibilityLiveData.postValue(View.GONE)
        _recyclerViewVisibilityLiveData.postValue(View.VISIBLE)
        _albumListLiveData.postValue(data.list.map { AlbumItem(it, router) })
    }

    private fun displayError(error: String) {
        _dotViewVisibilityLiveData.postValue(View.GONE)
        _errorTextVisibilityLiveData.postValue(View.VISIBLE)
        _recyclerViewVisibilityLiveData.postValue(View.GONE)
        _errorTextLiveData.postValue(error)
    }

    private fun displayLoading() {
        _dotViewVisibilityLiveData.postValue(View.VISIBLE)
        _errorTextVisibilityLiveData.postValue(View.GONE)
        _recyclerViewVisibilityLiveData.postValue(View.GONE)
    }
}