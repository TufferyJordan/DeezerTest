package com.deezer.test.albumdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.deezer.test.albumdetail.databinding.FragmentAlbumDetailBinding
import com.deezer.test.tracklist.view.TrackListFragment
import kotlinx.android.synthetic.main.fragment_album_detail.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class AlbumDetailFragment : Fragment() {

    private val albumDetailViewModel: AlbumDetailViewModel by viewModel()
    private var albumId: Int = -1

    private lateinit var binding: FragmentAlbumDetailBinding

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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_album_detail, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = albumDetailViewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        childFragmentManager.beginTransaction()
            .replace(R.id.albumDetailTracklistFragmentHolder, TrackListFragment.newInstance(albumId))
            .commitNow()

        binding.albumDetailAlbumNameText.isSelected = true
        binding.albumDetailAlbumDetailsText.isSelected = true
    }

    override fun onStart() {
        super.onStart()
        albumDetailViewModel.load(albumId)
    }

}