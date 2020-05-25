package com.deezer.test.albumlist.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.deezer.test.albumlist.AlbumListDependencies
import com.deezer.test.albumlist.R
import com.deezer.test.albumlist.domain.AlbumListInteractor
import com.deezer.test.albumlist.presenter.AlbumListView
import com.deezer.test.albumlist.domain.AlbumListViewModel
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.fragment_album_list.*
import org.koin.android.ext.android.get

class AlbumListFragment : Fragment(),
    AlbumListView {

    private lateinit var interactor: AlbumListInteractor
    private val groupAdapter = GroupAdapter<GroupieViewHolder>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_album_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        interactor = AlbumListDependencies(
            this,
            viewLifecycleOwner.lifecycleScope,
            get()
        ).interactor

        val lm = GridLayoutManager(requireContext(), 2)

        albumListFragmentRecyclerView.apply {
            layoutManager = lm
            adapter = groupAdapter
        }
    }

    override fun onStart() {
        super.onStart()
        interactor.load()
    }

    override fun displayAlbumList(viewModel: AlbumListViewModel) {
        albumListFragmentRecyclerView.visibility = View.VISIBLE
        albumListFragmentErrorText.visibility = View.GONE
        groupAdapter.clear()
        val items = viewModel.list.map { AlbumItem(it, get()) }
        groupAdapter.addAll(items)
    }

    override fun displayError(error: String) {
        albumListFragmentRecyclerView.visibility = View.GONE
        albumListFragmentErrorText.visibility = View.VISIBLE
        albumListFragmentErrorText.text = error
    }
}