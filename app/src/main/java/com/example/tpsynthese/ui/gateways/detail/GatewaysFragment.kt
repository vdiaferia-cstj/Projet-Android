package com.example.tpsynthese.ui.gateways.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.viewbinding.library.fragment.viewBinding
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.tpsynthese.R
import com.example.tpsynthese.databinding.FragmentGatewayBinding
import kotlinx.coroutines.flow.onEach


class GatewaysFragment : Fragment(R.layout.fragment_gateway) {
    private val args: GatewaysFragmentArgs by navArgs()
    private val binding: FragmentGatewayBinding by viewBinding()
    private val viewModel: GatewaysViewModel by viewModels() {
        GatewaysViewModel.Factory(args.href)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.gatewayUiState.onEach {
            when(it){
                GatewaysUiState.Empty -> Unit
                is GatewaysUiState.Error -> Toast.makeText(
                    requireContext(),
                    it.exception.localizedMessage,
                    Toast.LENGTH_LONG
                ).show()
                is GatewaysUiState.Loading -> TODO()
                is GatewaysUiState.Success ->
            }
        }
    }


}