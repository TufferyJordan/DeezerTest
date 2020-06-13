package com.deezer.test.design

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.Item

@BindingAdapter("data")
fun <T> setRecyclerViewItems(recyclerView: RecyclerView, items: List<Item>?) {
    if (recyclerView.adapter is GroupAdapter) {
        (recyclerView.adapter as GroupAdapter).apply {
            items?.let {
                clear()
                addAll(it)
            }
        }
    }
}

@BindingAdapter("urlPlaceholder")
fun <T> setImageViewUrlWithPlaceholder(imageView: ImageView, url: String?) {
    url?.let {
        Glide.with(imageView.context)
            .load(url)
            .placeholder(R.drawable.ic_album_placeholder)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(imageView)
    }
}

@BindingAdapter("urlCircleCrop")
fun <T> setImageViewUrlCircleCrop(imageView: ImageView, url: String?) {
    url?.let {
        Glide.with(imageView.context)
            .load(url)
            .transition(DrawableTransitionOptions.withCrossFade())
            .circleCrop()
            .into(imageView)
    }
}