package com.zachtib.bookmarks.ui.bookmarklist

import androidx.recyclerview.widget.DiffUtil
import com.zachtib.bookmarks.api.models.Bookmark

object BookmarkDiffCallback : DiffUtil.ItemCallback<Bookmark>() {
    override fun areItemsTheSame(oldItem: Bookmark, newItem: Bookmark): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Bookmark, newItem: Bookmark): Boolean {
        return oldItem == newItem
    }
}