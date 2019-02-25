package com.zachtib.bookmarks.ui.bookmarklist

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.zachtib.bookmarks.R
import com.zachtib.bookmarks.db.models.Bookmark
import com.zachtib.bookmarks.ui.BaseFragment
import kotlinx.android.synthetic.main.bookmark_list_fragment.*
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class BookmarkListFragment : BaseFragment(R.layout.bookmark_list_fragment) {

    private val viewModel: BookmarkListViewModel by viewModel()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val bookmarkListAdapter = BookmarkListAdapter(this::onBookmarkClicked)

        bookmarkRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = bookmarkListAdapter
        }

        viewModel.getBookmarks().observe(bookmarkListAdapter::submitList)

        launch {
            viewModel.onStart()
        }
    }

    private fun onBookmarkClicked(bookmark: Bookmark) {
        val browserIntent = Intent(Intent.ACTION_VIEW)
        browserIntent.data = Uri.parse(bookmark.url)
        startActivity(browserIntent)
    }

    override fun onResume() {
        super.onResume()
        launch {
            if (viewModel.shouldRedirectToAddAccount()) {
                findNavController().navigate(R.id.addAccountFragment)
            }
        }
    }
}
