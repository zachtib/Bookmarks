package com.zachtib.bookmarks.ui.bookmarklist

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.zachtib.bookmarks.R
import com.zachtib.bookmarks.framework.BaseFragment
import kotlinx.android.synthetic.main.bookmark_list_fragment.*
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class BookmarkListFragment : BaseFragment(R.layout.bookmark_list_fragment) {

    private val viewModel: BookmarkListViewModel by viewModel()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val bookmarkListAdapter = BookmarkListAdapter(viewModel::onBookmarkClicked)

        bookmarkRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = bookmarkListAdapter
        }

        viewModel.getBookmarks().observe(bookmarkListAdapter::submitList)
    }

    override fun onResume() {
        super.onResume()
        launch {
            if (viewModel.shouldRedirectToAddAccount()) {
                findNavController().navigate(R.id.action_bookmarkListFragment_to_addAccountFragment)
            }
        }
    }
}
