package com.deezer.test.tracklist

import com.deezer.test.interfaces.player.PlayerController
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.view_track_row.view.*

class TrackRow(
    private val data: TrackViewModelData,
    private val playerController: PlayerController
) : Item() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.containerView.apply {
            setOnClickListener {
                playerController.play(data.title, data.previewUrl)
            }
            trackTitle.text = data.title
            trackDuration.text = data.duration
        }
    }

    override fun getLayout(): Int = R.layout.view_track_row
}