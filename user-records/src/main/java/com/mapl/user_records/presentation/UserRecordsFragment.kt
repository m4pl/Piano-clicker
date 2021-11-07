package com.mapl.user_records.presentation

import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mapl.core_ui.util.viewbinding.viewBinding
import com.mapl.user_records.R
import com.mapl.user_records.databinding.FragmentUserRecordsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserRecordsFragment : Fragment(R.layout.fragment_user_records) {

    private val binding by viewBinding(FragmentUserRecordsBinding::bind)
    private val viewModel: UserRecordsViewModel by viewModels()

    private val requestPermissionsLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { map ->
            map.entries.forEach { (_, granted) ->
                if (granted) {
                    viewModel.checkPermissions()
                } else {
                    // TODO: Add a visual display
                }
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
        setupViewModel()
    }

    private fun setupView() {
        binding.newRecordFab.setOnClickListener {
            viewModel.checkPermissions()
        }
    }

    private fun setupViewModel() {
        viewModel
            .requestPermissions
            .observe(viewLifecycleOwner, ::requestPermissions)
    }

    private fun requestPermissions(permissions: Array<String>) {
        requestPermissionsLauncher.launch(permissions)
    }

    companion object {
        fun newInstance(): UserRecordsFragment = UserRecordsFragment()
    }
}
