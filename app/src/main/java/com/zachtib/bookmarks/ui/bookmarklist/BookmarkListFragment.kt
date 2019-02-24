package com.zachtib.bookmarks.ui.bookmarklist

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.zachtib.bookmarks.R
import com.zachtib.bookmarks.converters.toApiModel
import com.zachtib.bookmarks.ui.BaseFragment
import kotlinx.android.synthetic.main.bookmark_list_fragment.*
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class BookmarkListFragment : BaseFragment(R.layout.bookmark_list_fragment) {

    private val viewModel: BookmarkListViewModel by viewModel()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val bookmarkListAdapter = BookmarkListAdapter()

        bookmarkRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = bookmarkListAdapter
        }

        viewModel.getBookmarks().observe {
            bookmarkListAdapter.submitList(it.map { dbModel -> dbModel.toApiModel() })
        }

        launch {
            viewModel.onStart()
        }
    }

}
