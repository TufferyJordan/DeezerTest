package com.deezer.test.albumdetail

import android.content.res.Resources
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deezer.test.data.GetAlbumRepository
import com.deezer.test.data.model.AlbumData
import com.deezer.test.design.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class AlbumDetailViewModel(
    private val repository: GetAlbumRepository,
    private val resources: Resources
) : ViewModel() {

    private val _headerVisibilityLiveData = MutableLiveData<Int>(View.VISIBLE)
    val headerVisibilityLiveData: LiveData<Int> = _headerVisibilityLiveData

    private val _errorVisibilityLiveData = MutableLiveData<Int>(View.GONE)
    val errorVisibilityLiveData: LiveData<Int> = _errorVisibilityLiveData

    private val _errorTextLiveData = MutableLiveData<String>()
    val errorTextLiveData: LiveData<String> = _errorTextLiveData

    private val _artistNameTextLiveData = MutableLiveData<String>()
    val artistNameTextLiveData: LiveData<String> = _artistNameTextLiveData

    private val _artistUrlLiveData = MutableLiveData<String>()
    val artistUrlLiveData: LiveData<String> = _artistUrlLiveData

    private val _albumNameLiveData = MutableLiveData<String>()
    val albumNameLiveData: LiveData<String> = _albumNameLiveData

    private val _albumDetailsLiveData = MutableLiveData<String>()
    val albumDetailsLiveData: LiveData<String> = _albumDetailsLiveData

    private val _albumUrlLiveData = MutableLiveData<String>()
    val albumUrlLiveData: LiveData<String> = _albumUrlLiveData

    private val ioScope = CoroutineScope(Dispatchers.IO + Job())

    fun load(albumId: Int) {
        ioScope.launch {
            val data = repository.getAlbumDetail(albumId)
            viewModelScope.launch {
                data?.let {
                    displayAlbumDetail(data)
                } ?: run {
                    displayError()
                }
            }
        }
    }

    private fun displayAlbumDetail(data: AlbumData) {
        _headerVisibilityLiveData.postValue(View.VISIBLE)
        _errorVisibilityLiveData.postValue(View.GONE)
        _albumUrlLiveData.postValue(data.coverImage)
        _artistUrlLiveData.postValue(data.artistImage)
        _artistNameTextLiveData.postValue(data.artistName)
        _albumNameLiveData.postValue(data.albumName)

        val detailsAlbum = resources.getQuantityString(
            R.plurals.album_detail_tracks_data,
            data.tracksNumber,
            data.tracksNumber,
            data.albumReleaseDate
        )
        _albumDetailsLiveData.postValue(detailsAlbum)


//        val drawable =
//            ContextCompat.getDrawable(requireContext(), R.drawable.ic_parental_advisory_label)
//        drawable?.let {
//            val sizeFactor = drawable.intrinsicWidth.toFloat() / drawable.intrinsicHeight.toFloat()
//            drawable.setBounds(
//                0,
//                0,
//                (albumDetailAlbumDetailsText.lineHeight * sizeFactor).toInt(),
//                albumDetailAlbumDetailsText.lineHeight
//            )
//            val spannableString = SpannableStringBuilder().apply {
//                if (viewModel.explicit) {
//                    append("  ")
//                    setSpan(
//                        ImageSpan(
//                            drawable,
//                            com.deezer.test.design.R.drawable.ic_parental_advisory_label
//                        ),
//                        0,
//                        1,
//                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
//                    )
//                }
//                append(viewModel.albumDetail)
//            }
//        }
    }

    private fun displayError() {
        _headerVisibilityLiveData.postValue(View.GONE)
        _errorVisibilityLiveData.postValue( View.VISIBLE)
        _errorTextLiveData.postValue("An error has occurred during the album request")
    }
}