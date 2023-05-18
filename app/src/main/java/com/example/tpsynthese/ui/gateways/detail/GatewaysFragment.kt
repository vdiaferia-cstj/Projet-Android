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
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.tpsynthese.R
import com.example.tpsynthese.core.ColorHelper
import com.example.tpsynthese.databinding.FragmentGatewayBinding
import kotlinx.coroutines.flow.launchIn
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
                is GatewaysUiState.Success -> {
                    binding.GatewayChipStatus.text = it.gateway.connection.status
                    binding.GatewayChipStatus.chipBackgroundColor = ColorHelper.connectionStatusColor(binding.root.context,it.gateway.connection.status)
                    Glide.with(binding.imvGateway1).load(it.gateway.config.kernel[0]).into(binding.imvGateway1)
                    Glide.with(binding.imvGateway2).load(it.gateway.config.kernel[1]).into(binding.imvGateway2)
                    Glide.with(binding.imvGateway3).load(it.gateway.config.kernel[2]).into(binding.imvGateway3)
                    Glide.with(binding.imvGateway4).load(it.gateway.config.kernel[3]).into(binding.imvGateway4)
                    Glide.with(binding.imvGateway5).load(it.gateway.config.kernel[4]).into(binding.imvGateway5)
                    binding.txvDbm.text = it.gateway.connection.signal.toString()
                    binding.txvLatency.text = it.gateway.connection.signal.toString()
                    binding.txvDownloadGateway.text = it.gateway.connection.download.toString()
                    binding.txvUploadGateway.text = it.gateway.connection.upload.toString()
                    binding.txvIp.text = it.gateway.connection.ip
                    binding.txvMac.text = it.gateway.serialNumber
                    binding.txvPin.text = it.gateway.pin
                }
            }
        }.launchIn(lifecycleScope)
    }


}