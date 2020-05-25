package com.deezer.test.albumdetail.view

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ImageSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
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
            get(),
            resources
        ).interactor
    }

    override fun onStart() {
        super.onStart()
        interactor.load(albumId)
    }

    override fun displayAlbumDetail(viewModel: AlbumDetailViewModel) {
        albumDetailHeaderGroup.visibility = View.VISIBLE
        albumDetailErrorText.visibility = View.GONE
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
        albumDetailAlbumNameText.isSelected = true


        val drawable =
            ContextCompat.getDrawable(requireContext(), R.drawable.ic_parental_advisory_label)
        drawable?.let {
            val sizeFactor = drawable.intrinsicWidth.toFloat() / drawable.intrinsicHeight.toFloat()
            drawable.setBounds(
                0,
                0,
                (albumDetailAlbumDetailsText.lineHeight * sizeFactor).toInt(),
                albumDetailAlbumDetailsText.lineHeight
            )
            val spannableString = SpannableStringBuilder().apply {
                if (viewModel.explicit) {
                    append("  ")
                    setSpan(
                        ImageSpan(
                            drawable,
                            com.deezer.test.design.R.drawable.ic_parental_advisory_label
                        ),
                        0,
                        1,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                }
                append(viewModel.albumDetail)
            }
            albumDetailAlbumDetailsText.text = spannableString
        }
        albumDetailAlbumDetailsText.isSelected = true
    }

    override fun displayError(message: String) {
        albumDetailHeaderGroup.visibility = View.GONE
        albumDetailErrorText.visibility = View.VISIBLE
        albumDetailErrorText.text = message
    }
}