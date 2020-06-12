package com.deezer.test.design

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.Item

@BindingAdapter("data")
fun <T> setRecyclerViewProperties(recyclerView: RecyclerView, items: List<Item>?) {
    if (recyclerView.adapter is GroupAdapter) {
        (recyclerView.adapter as GroupAdapter).apply {
            items?.let {
                clear()
                addAll(it)
            }
        }
    }
}