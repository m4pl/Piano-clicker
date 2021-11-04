package com.mapl.records.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mapl.core_ui.util.viewbinding.viewBinding
import com.mapl.records.R
import com.mapl.records.databinding.FragmentRecordsBinding

class RecordsFragment : Fragment(R.layout.fragment_records) {

    private val binding by viewBinding(FragmentRecordsBinding::bind)
    private val viewModel: RecordsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        fun newInstance(): RecordsFragment = RecordsFragment()
    }
}
