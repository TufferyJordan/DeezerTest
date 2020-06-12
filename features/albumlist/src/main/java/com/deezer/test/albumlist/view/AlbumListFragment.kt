package com.deezer.test.albumlist.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.deezer.test.albumlist.R
import com.deezer.test.albumlist.databinding.FragmentAlbumListBinding
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.fragment_album_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class AlbumListFragment : Fragment() {

    private val albumListViewModel: AlbumListViewModel by viewModel()
    private val groupAdapter = GroupAdapter<GroupieViewHolder>()

    private lateinit var binding: FragmentAlbumListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_album_list, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = albumListViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val lm = GridLayoutManager(requireContext(), 2)

        albumListFragmentRecyclerView.apply {
            layoutManager = lm
            adapter = groupAdapter
        }
    }

    override fun onStart() {
        super.onStart()
        albumListViewModel.load()
    }
}