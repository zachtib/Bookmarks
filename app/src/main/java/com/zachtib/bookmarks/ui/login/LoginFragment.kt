package com.zachtib.bookmarks.ui.login

import android.os.Bundle
import android.view.View
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.zachtib.bookmarks.R
import com.zachtib.bookmarks.ui.BaseFragment
import kotlinx.android.synthetic.main.login_fragment.*
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : BaseFragment(R.layout.login_fragment) {
    private val viewModel: LoginViewModel by viewModel()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        loginButton.setOnClickListener {
            val username = usernameField.text.toString()
            val password = passwordField.text.toString()
            val serverUrl = serverField.text.toString()
            launch {
                showLoading()
                if(viewModel.tryLogin(serverUrl, username, password)) {
                    findNavController().navigate(R.id.bookmarkListFragment)
                } else {
                    hideLoading()
                }
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
