package com.example.tpsynthese.ui.gateways.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.viewbinding.library.fragment.viewBinding
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
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
                    //TODO: mes glide ne marche pas. Je ne comprend pas comment mettre un drawable avec un string
                    Glide.with(requireContext())
                        .load(getString(R.string.elementGateway,it.gateway.config.kernel[0].lowercase()))
                        .into(binding.imvGateway1)
                    Glide.with(requireContext())
                        .load(getString(R.string.elementGateway,it.gateway.config.kernel[1].lowercase()))
                        .into(binding.imvGateway2)
                    Glide.with(requireContext())
                        .load(getString(R.string.elementGateway,it.gateway.config.kernel[2].lowercase()))
                        .into(binding.imvGateway3)
                    Glide.with(requireContext())
                        .load(getString(R.string.elementGateway,it.gateway.config.kernel[3].lowercase()))
                        .into(binding.imvGateway4)
                    Glide.with(requireContext())
                        .load(getString(R.string.elementGateway,it.gateway.config.kernel[4].lowercase()))
                        .into(binding.imvGateway5)

                    binding.txvDbm.text = it.gateway.connection.signal.toString()  + " dBm"
                    binding.txvLatency.text = it.gateway.connection.signal.toString() + " ns"
                    binding.txvDownloadGateway.text = it.gateway.connection.download.toString() + " Ebps"
                    binding.txvUploadGateway.text = it.gateway.connection.upload.toString() + " Ebps"
                    binding.txvIp.text = it.gateway.connection.ip
                    binding.txvMac.text = it.gateway.serialNumber
                    binding.txvPin.text = it.gateway.pin
                    binding.txvHref.text = it.gateway.href
                    binding.txvSSID.text = it.gateway.config.SSID
                    binding.txvRevision.text = it.gateway.revision
                    binding.txvHash.text = it.gateway.hash
                    val idGateway = it.gateway.href
                    binding.btnReboot.setOnClickListener {
                        viewModel.reboot(idGateway)
                    }
                    binding.btnUpdate.setOnClickListener {
                        viewModel.update(idGateway)
                    }
                }
            }
        }.launchIn(lifecycleScope)
    }


}