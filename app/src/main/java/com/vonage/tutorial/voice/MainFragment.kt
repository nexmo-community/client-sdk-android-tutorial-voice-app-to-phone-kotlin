package com.vonage.tutorial.voice

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import kotlin.properties.Delegates

class MainFragment : Fragment(R.layout.fragment_main), BackPressHandler {

    private lateinit var startAppToPhoneCallButton: Button
    private lateinit var progressBar: ProgressBar

    private var dataLoading: Boolean by Delegates.observable(false) { _, _, newValue ->
        startAppToPhoneCallButton.isEnabled = !newValue
        progressBar.isVisible = newValue
    }

    private val viewModel by viewModels<MainViewModel>()

    private val toastObserver = Observer<String> {
        Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show();
    }

    private val loadingObserver = Observer<Boolean> {
        dataLoading = it
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.toast.observe(viewLifecycleOwner, toastObserver)
        viewModel.loading.observe(viewLifecycleOwner, loadingObserver)

        progressBar = view.findViewById(R.id.progressBar)
        startAppToPhoneCallButton = view.findViewById(R.id.startAppToPhoneCallButton)

        startAppToPhoneCallButton.setOnClickListener {
            viewModel.startAppToPhoneCall()
        }
    }

    override fun onBackPressed() {
        viewModel.onBackPressed()
    }
}
