package com.deezer.test.tracklist

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deezer.test.data.GetTrackListRepository
import com.deezer.test.data.model.TrackListData
import com.deezer.test.interfaces.player.PlayerController
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import java.text.NumberFormat

class TrackListViewModel(
    private val repository: GetTrackListRepository,
    private val playerController: PlayerController
) : ViewModel() {
    private val ioScope = CoroutineScope(Dispatchers.IO + Job())

    private val _loaderVisibilityLiveData: MutableLiveData<Int> = MutableLiveData()
    val loaderVisibilityLiveData: LiveData<Int> = _loaderVisibilityLiveData

    private val _errorTextVisibilityLiveData: MutableLiveData<Int> = MutableLiveData()
    val errorTextVisibilityLiveData: LiveData<Int> = _errorTextVisibilityLiveData

    private val _recyclerViewVisibilityLiveData: MutableLiveData<Int> = MutableLiveData()
    val recyclerViewVisibilityLiveData: LiveData<Int> = _recyclerViewVisibilityLiveData

    private val _tracklistItemsLiveData: MutableLiveData<List<Item>> = MutableLiveData()
    val tracklistItemsLiveData: LiveData<List<Item>> = _tracklistItemsLiveData

    private val _errorTextLiveData: MutableLiveData<String> = MutableLiveData()
    val errorTextLiveData: LiveData<String> = _errorTextLiveData

    fun load(albumId: Int) {
        displayLoading()
        ioScope.launch {
            val data = repository.getTrackList(albumId)
            viewModelScope.launch {
                data?.let {
                    displayTrackList(data)
                } ?: run {
                    displayError("An error has occurred while requesting the tracklist")
                }
            }
        }
    }

    private fun displayTrackList(data: TrackListData) {

        _loaderVisibilityLiveData.postValue(View.GONE)
        _errorTextVisibilityLiveData.postValue(View.GONE)
        _recyclerViewVisibilityLiveData.postValue(View.VISIBLE)

        val header = TrackListHeaderRow()
        val tracks = data.trackList.map {
            val f: NumberFormat = DecimalFormat("00")
            val duration = "${it.duration / 60}:${f.format(it.duration % 60)}"
            TrackRow(
                TrackViewModelData(
                    it.title,
                    duration,
                    it.previewURL
                ),
                playerController
            )
        }
        val items = arrayListOf<Item>().apply {
            add(header)
            addAll(tracks)
        }

        _tracklistItemsLiveData.postValue(items)
    }

    private fun displayError(message: String) {
        _loaderVisibilityLiveData.postValue(View.GONE)
        _errorTextVisibilityLiveData.postValue(View.VISIBLE)
        _recyclerViewVisibilityLiveData.postValue(View.GONE)

        _errorTextLiveData.postValue(message)
    }

    private fun displayLoading() {
        _loaderVisibilityLiveData.postValue(View.VISIBLE)
        _errorTextVisibilityLiveData.postValue(View.GONE)
        _recyclerViewVisibilityLiveData.postValue(View.GONE)
    }
}

data class TrackViewModelData(
    val title: String,
    val duration: String,
    val previewUrl: String
)