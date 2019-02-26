package com.zachtib.bookmarks.ui.addaccount

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.zachtib.bookmarks.R
import com.zachtib.bookmarks.ui.BaseFragment
import com.zachtib.bookmarks.ui.onTextChanged
import com.zachtib.bookmarks.ui.textValue
import kotlinx.android.synthetic.main.login_fragment.*
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddAccountFragment : BaseFragment(R.layout.login_fragment) {
    private val viewModel: AddAccountViewModel by viewModel()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        serverField.textValue = viewModel.serverUrl
        usernameField.textValue = viewModel.username
        passwordField.textValue = viewModel.password

        serverField.onTextChanged { viewModel.serverUrl = it }
        usernameField.onTextChanged { viewModel.username = it }
        passwordField.onTextChanged { viewModel.password = it }

        loginButton.setOnClickListener {
            launch {
                if(viewModel.loginButtonClicked()) {
                    findNavController().navigate(R.id.action_loginFragment_to_bookmarkListFragment)
                }
            }
        }

        viewModel.loginButtonEnabled.observe { enabled ->
            loginButton.isEnabled = enabled
        }

        viewModel.loadingIndicatorVisible.observe { isLoading ->
            if (isLoading) {
                showLoading()
            } else {
                hideLoading()
            }
        }
    }

    private fun showLoading() {
        inputWidgets.isEnabled = false
        progressSpinner.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        inputWidgets.isEnabled = true
        progressSpinner.visibility = View.GONE
    }
}
