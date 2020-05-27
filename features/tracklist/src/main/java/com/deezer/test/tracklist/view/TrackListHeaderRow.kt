package com.deezer.test.tracklist.view

import com.deezer.test.tracklist.R
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item

class TrackListHeaderRow: Item() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {}
    override fun getLayout(): Int = R.layout.view_tracklist_header_row
}