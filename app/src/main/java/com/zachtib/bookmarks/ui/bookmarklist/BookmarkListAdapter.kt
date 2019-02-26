package com.zachtib.bookmarks.ui.bookmarklist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zachtib.bookmarks.R
import com.zachtib.bookmarks.db.models.Bookmark
import kotlinx.android.synthetic.main.item_bookmark.view.*

class BookmarkListAdapter : ListAdapter<Bookmark, BookmarkListAdapter.BookmarkViewHolder>(BookmarkDiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_bookmark, parent, false)
        return BookmarkViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookmarkViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class BookmarkViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(bookmark: Bookmark) {
            itemView.bookmarkName.text = bookmark.title
        }
    }
}