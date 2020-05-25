package com.deezer.test.tracklist.view

import com.deezer.test.tracklist.R
import com.deezer.test.tracklist.domain.TrackViewModel
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.view_track_row.view.*

class TrackRow(
    private val data: TrackViewModel
) : Item() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.containerView.apply {
            trackTitle.text = data.title
            trackDuration.text = data.duration.toString()
        }
    }

    override fun getLayout(): Int = R.layout.view_track_row
}