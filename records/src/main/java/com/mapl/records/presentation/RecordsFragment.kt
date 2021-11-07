package com.mapl.records.presentation

import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mapl.core_ui.util.viewbinding.viewBinding
import com.mapl.records.R
import com.mapl.records.databinding.FragmentRecordsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecordsFragment : Fragment(R.layout.fragment_records) {

    private val binding by viewBinding(FragmentRecordsBinding::bind)
    private val viewModel: RecordsViewModel by viewModels()

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
        fun newInstance(): RecordsFragment = RecordsFragment()
    }
}
