package com.deezer.test.tracklist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.deezer.test.tracklist.databinding.FragmentTracklistBinding
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.fragment_tracklist.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class TrackListFragment : Fragment() {

    private val viewModel: TrackListViewModel by viewModel()
    private lateinit var binding: FragmentTracklistBinding
    private var albumId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            albumId = it.getInt(ARGS_ALBUM_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tracklist, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tracklistRecyclerView.apply {
            val lm = LinearLayoutManager(requireContext())
            layoutManager = lm
            addItemDecoration(DividerItemDecoration(requireContext(),lm.orientation))
            adapter = GroupAdapter<GroupieViewHolder>()
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.load(albumId)
    }

    companion object {
        private const val ARGS_ALBUM_ID = "ARGS_ALBUM_ID"
        fun newInstance(albumId: Int): TrackListFragment = TrackListFragment()
            .apply {
            arguments = Bundle().apply {
                putInt(ARGS_ALBUM_ID, albumId)
            }
        }
    }
}