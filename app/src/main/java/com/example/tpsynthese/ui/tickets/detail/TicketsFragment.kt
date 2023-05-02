package com.example.tpsynthese.ui.tickets.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.tpsynthese.R
import com.example.tpsynthese.databinding.FragmentTicketBinding
import android.viewbinding.library.fragment.viewBinding
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import io.github.g00fy2.quickie.QRResult
import io.github.g00fy2.quickie.ScanQRCode
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class TicketsFragment : Fragment(R.layout.fragment_ticket) {

    private val binding: FragmentTicketBinding by viewBinding()
    private val viewModel: TicketsViewModel by viewModels()

    private val scanQRCode = registerForActivityResult(ScanQRCode(), ::handleQuickieResult)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnInstall.setOnClickListener{
            scanQRCode.launch(null)
        }

        viewModel.ticketUiState.onEach {
            when(it){
                TicketsUiState.Empty -> Unit
                is TicketsUiState.Error -> Toast.makeText(requireContext(), it.exception.localizedMessage, Toast.LENGTH_LONG).show()
                is TicketsUiState.Solved -> TODO()
                is TicketsUiState.Success -> Toast.makeText(requireContext(), it.gateway.toString() , Toast.LENGTH_LONG).show()
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun handleQuickieResult(qrResult: QRResult){

        when(qrResult){
            is QRResult.QRSuccess -> {
               //TODO: Une mise à jour de la liste de l’affichage des bornes du client est nécessaire après une installation
                viewModel.installGateway(qrResult.content.rawValue)
            }
            QRResult.QRUserCanceled -> binding.txvCodeContent.text = getString(R.string.user_canceled)
            QRResult.QRMissingPermission -> binding.txvCodeContent.text = getString(R.string.missing_permission)
            is QRResult.QRError -> binding.txvCodeContent.text = qrResult.exception.localizedMessage
        }

    }
}