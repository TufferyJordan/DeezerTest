package com.deezer.test.albumlist.view

import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.deezer.test.albumlist.R
import com.deezer.test.albumlist.presenter.AlbumViewModel
import com.deezer.test.interfaces.routing.AlbumListRouter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.view_album.view.*

class AlbumItem(
    private val dataItem: AlbumViewModel,
    private val router: AlbumListRouter
): Item() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.containerView.apply {
            setOnClickListener {
                router.navigate(dataItem.id)
            }
            Glide.with(this)
                .load(dataItem.cover)
                .placeholder(R.drawable.ic_album_placeholder)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(albumViewCover)
            albumViewTitleText.text = dataItem.title
        }
    }

    override fun getLayout(): Int = R.layout.view_album
}