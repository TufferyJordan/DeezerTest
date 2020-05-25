package com.deezer.test.albumdetail.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.transition.DrawableCrossFadeTransition
import com.deezer.test.albumdetail.AlbumDetailDependencies
import com.deezer.test.albumdetail.R
import com.deezer.test.albumdetail.domain.AlbumDetailInteractor
import com.deezer.test.albumdetail.domain.AlbumDetailViewModel
import com.deezer.test.albumdetail.presenter.AlbumDetailView
import kotlinx.android.synthetic.main.fragment_album_detail.*
import org.koin.android.ext.android.get

class AlbumDetailFragment : Fragment(), AlbumDetailView {

    private lateinit var interactor: AlbumDetailInteractor
    private var albumId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            albumId = it.getInt("albumId")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_album_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        interactor = AlbumDetailDependencies(
            this,
            lifecycleScope,
            get()
        ).interactor
    }

    override fun onStart() {
        super.onStart()
        interactor.load(albumId)
    }

    override fun displayAlbumDetail(viewModel: AlbumDetailViewModel) {
        Glide.with(requireContext())
            .load(viewModel.coverImage)
            .placeholder(R.drawable.ic_album_placeholder)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(albumDetailAlbumCoverImage)

        Glide.with(requireContext())
            .load(viewModel.artistImage)
            .transition(DrawableTransitionOptions.withCrossFade())
            .circleCrop()
            .into(albumDetailArtistImage)

        albumDetailArtistNameText.text = viewModel.artistName
        albumDetailAlbumNameText.text = viewModel.albumName
        albumDetailAlbumDetailsText.text = "${viewModel.tracksNumber} tracks, ${viewModel.albumReleaseDate}"
    }
}