package com.example.tpsynthese.ui.tickets.detail

import android.opengl.Visibility
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.tpsynthese.R
import com.example.tpsynthese.databinding.FragmentTicketBinding
import android.viewbinding.library.fragment.viewBinding
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.tpsynthese.core.Constants
import com.example.tpsynthese.data.datasource.TicketDataSource
import com.example.tpsynthese.data.repositories.TicketRepository
import com.example.tpsynthese.domain.models.Customer
import com.example.tpsynthese.domain.models.Gateway
import com.example.tpsynthese.domain.models.Ticket
import com.github.kittinunf.fuel.json.jsonDeserializer
import io.github.g00fy2.quickie.QRResult
import io.github.g00fy2.quickie.ScanQRCode
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class TicketsFragment : Fragment(R.layout.fragment_ticket) {
    private val args: TicketsFragmentArgs by navArgs()
    private val binding: FragmentTicketBinding by viewBinding()
    private val viewModel: TicketsViewModel by viewModels(){
        TicketsViewModel.Factory(args.href)
    }
    private lateinit var customer : Customer
    private val scanQRCode = registerForActivityResult(ScanQRCode(), ::handleQuickieResult)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSolve.setOnClickListener {
            binding.btnInstall.visibility = View.GONE
            binding.btnSolve.visibility = View.GONE
            binding.btnOpen.visibility = View.VISIBLE
            changeState(args.href,Constants.TicketStatus.Solved.toString())

        }

        binding.btnOpen.setOnClickListener {
            binding.btnInstall.visibility = View.VISIBLE
            binding.btnSolve.visibility = View.VISIBLE
            binding.btnOpen.visibility = View.GONE
            changeState(args.href,Constants.TicketStatus.Open.toString())
        }

        binding.btnInstall.setOnClickListener {
            scanQRCode.launch(null)
        }

        viewModel.ticketUiState.onEach {
            when (it) {
                TicketsUiState.Empty -> Unit
                is TicketsUiState.Error -> {
                    Toast.makeText(
                        requireContext(),
                        it.exception.localizedMessage,
                        Toast.LENGTH_LONG
                    ).show()
                    requireActivity().supportFragmentManager.popBackStack()
                }

                is TicketsUiState.Solved -> Unit
                is TicketsUiState.Success -> {
                    binding.incTicketCard.txvTicket.text = it.ticket.ticketNumber.toString()
                    binding.incTicketCard.txvDate.text = it.ticket.createdDate.toString()
                    //binding.incTicketCard.chipPriority.chipBackgroundColor = it.ticket.
                    //Besoin de changer la couleur des chips binding.incTicketCard.chipPriority
                    //Add contry flag Glide.with(this).load(it.ticket.)
                }

                is TicketsUiState.CustomerError -> TODO()
                is TicketsUiState.CustomerSuccess -> {
                 customer = it.customer
                    binding.incTicketInfo.txvName.text = customer.firstName
                }
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun changeState(href:String,state: String){
        viewModel.changeState(href,state)
    }

    private fun handleQuickieResult(qrResult: QRResult) {

        when (qrResult) {
            is QRResult.QRSuccess -> {
               //TODO: Une mise à jour de la liste de l’affichage des bornes du client est nécessaire après une installation
                val jsonGateway = Json.decodeFromString<Gateway>(qrResult.content.rawValue)
                viewModel.installGateway(jsonGateway)
            }
            QRResult.QRUserCanceled -> TODO()
            QRResult.QRMissingPermission -> TODO()
            is QRResult.QRError -> TODO()
        }

    }


}