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
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.example.tpsynthese.core.ColorHelper
import com.example.tpsynthese.core.Constants
import com.example.tpsynthese.data.datasource.TicketDataSource
import com.example.tpsynthese.data.repositories.TicketRepository
import com.example.tpsynthese.domain.models.Customer
import com.example.tpsynthese.domain.models.Gateway
import com.example.tpsynthese.domain.models.Ticket
import com.example.tpsynthese.ui.tickets.list.TicketsListFragmentDirections
import com.github.kittinunf.fuel.json.jsonDeserializer
import com.google.android.gms.maps.model.LatLng
import io.github.g00fy2.quickie.QRResult
import io.github.g00fy2.quickie.ScanQRCode
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import com.bumptech.glide.Glide
import com.example.tpsynthese.ui.gateways.list.GatewaysRecyclerViewAdapter

class TicketsFragment : Fragment(R.layout.fragment_ticket) {
    private val args: TicketsFragmentArgs by navArgs()
    private val binding: FragmentTicketBinding by viewBinding()
    private val viewModel: TicketsViewModel by viewModels(){
        TicketsViewModel.Factory(args.href)
    }
    private lateinit var customer : Customer
    private lateinit var ticket: Ticket
    private val scanQRCode = registerForActivityResult(ScanQRCode(), ::handleQuickieResult)
    private var position : LatLng? = null
    private val gatewayRecyclerViewAdapter = GatewaysRecyclerViewAdapter(listOf(),::onClickGateway)

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

        binding.fabLocation.setOnClickListener {
            if(position != null)
            {
                val action = TicketsFragmentDirections.actionTicketFragmentToMapsActivity(position!!)
                findNavController().navigate(action)
            }
        }

        //binding.incTicketInfo.rcvGateway.layoutManager = GridLayoutManager(requireContext(),2)
        //binding.incTicketInfo.rcvGateway.adapter = gatewayRecyclerViewAdapter

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
                is TicketsUiState.Loading -> {
                    binding.pgbLoading.show()
                }

                is TicketsUiState.Solved -> Unit
                is TicketsUiState.Success -> {
                    binding.pgbLoading.visibility = View.GONE
                    binding.incTicketCard.txvTicket.text = buildString { append("Ticket: ")
                        append(it.ticket.ticketNumber) }
                    binding.incTicketCard.txvDate.text = it.ticket.createdDate
                    binding.incTicketCard.chipStatus.text = it.ticket.status
                    binding.incTicketCard.chipPriority.text = it.ticket.priority
                    binding.incTicketCard.chipStatus.chipBackgroundColor = ColorHelper.ticketStatusColor(binding.root.context,it.ticket.status)
                    binding.incTicketCard.chipPriority.chipBackgroundColor = ColorHelper.ticketPriorityColor(binding.root.context,it.ticket.priority)
                    ticket = it.ticket
                }
                is TicketsUiState.CustomerError -> TODO()
                is TicketsUiState.CustomerSuccess -> {
                 customer = it.customer
                    binding.incTicketInfo.txvName.text = customer.firstName
                    position = LatLng(customer.coord.latitude.toDouble(),customer.coord.longitude.toDouble())
                    binding.incTicketInfo.txvAdresse.text = customer.address
                    binding.incTicketInfo.txvVille.text = customer.city
                    Glide.with(binding.incTicketInfo.imgViewDrapeau).load(ticket.customer.country).into(binding.incTicketInfo.imgViewDrapeau)
                }
                is TicketsUiState.GatewayInstallError -> {
                    Toast.makeText(requireContext(),    getString(R.string.gateway_error)  , Toast.LENGTH_SHORT).show()
                }
                is TicketsUiState.GatewayInstallSuccess -> {
                    Toast.makeText(requireContext(), getString(R.string.gateway_success, it.gateway.serialNumber) , Toast.LENGTH_SHORT).show()
                }
                is TicketsUiState.GatewayError ->{
                    Toast.makeText(requireContext(),"Error in loading gateways rcv", Toast.LENGTH_SHORT).show()
                }
                is TicketsUiState.GatewaySuccess ->{
                    gatewayRecyclerViewAdapter.gateways = it.gateways
                    gatewayRecyclerViewAdapter.notifyDataSetChanged()
                }
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun onClickGateway(gateway: Gateway){

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
            QRResult.QRUserCanceled -> Toast.makeText(requireContext(), getString(R.string.qr_code_canceled) , Toast.LENGTH_SHORT).show()
            QRResult.QRMissingPermission -> Toast.makeText(requireContext(), getString(R.string.qr_code_missing_permission) , Toast.LENGTH_SHORT).show()
            is QRResult.QRError -> Toast.makeText(requireContext(), getString(R.string.qr_code_error) , Toast.LENGTH_SHORT).show()
        }

    }


}