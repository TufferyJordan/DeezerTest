package com.deezer.test.tracklist.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.deezer.test.tracklist.R
import com.deezer.test.tracklist.TrackListDependencies
import com.deezer.test.tracklist.domain.TrackListInteractor
import com.deezer.test.tracklist.domain.TrackListViewModel
import com.deezer.test.tracklist.presenter.TrackListView
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.fragment_tracklist.*
import org.koin.android.ext.android.get

class TrackListFragment : Fragment(), TrackListView {

    private lateinit var interactor: TrackListInteractor
    private lateinit var groupAdapter: GroupAdapter<GroupieViewHolder>
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
        return inflater.inflate(R.layout.fragment_tracklist, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        interactor = TrackListDependencies(
            this,
            lifecycleScope,
            get()
        ).interactor

        val lm = LinearLayoutManager(requireContext())
        groupAdapter = GroupAdapter<GroupieViewHolder>()
        tracklistRecyclerView.apply {
            layoutManager = lm
            addItemDecoration(DividerItemDecoration(requireContext(),lm.orientation))
            adapter = groupAdapter
        }
    }

    override fun onStart() {
        super.onStart()
        interactor.load(albumId)
    }

    override fun displayTrackList(viewModel: TrackListViewModel) {
        tracklistLoader.visibility = View.GONE
        trackListErrorText.visibility = View.GONE
        tracklistRecyclerView.visibility = View.VISIBLE

        val header = TrackListHeaderRow()
        val tracks = viewModel.trackList.map {
            TrackRow(it)
        }
        val items = arrayListOf<Item>().apply {
            add(header)
            addAll(tracks)
        }
        groupAdapter.clear()
        groupAdapter.addAll(items)
    }

    override fun displayError(message: String) {
        tracklistLoader.visibility = View.GONE
        trackListErrorText.visibility = View.VISIBLE
        tracklistRecyclerView.visibility = View.GONE

        trackListErrorText.text = message
    }

    override fun displayLoading() {
        tracklistLoader.visibility = View.VISIBLE
        tracklistRecyclerView.visibility = View.GONE
        trackListErrorText.visibility = View.GONE
    }

    companion object {
        private const val ARGS_ALBUM_ID = "ARGS_ALBUM_ID"
        fun newInstance(albumId: Int): TrackListFragment = TrackListFragment().apply {
            arguments = Bundle().apply {
                putInt(ARGS_ALBUM_ID, albumId)
            }
        }
    }
}