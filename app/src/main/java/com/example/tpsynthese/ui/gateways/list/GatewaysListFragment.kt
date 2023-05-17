package com.example.tpsynthese.ui.gateways.list

import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tpsynthese.R
import com.example.tpsynthese.databinding.FragmentListGatewaysBinding
import com.example.tpsynthese.databinding.FragmentListTicketsBinding
import com.example.tpsynthese.domain.models.Gateway
import com.example.tpsynthese.domain.models.Ticket
import com.example.tpsynthese.ui.tickets.list.TicketsListFragmentDirections
import com.example.tpsynthese.ui.tickets.list.TicketsListUiState
import com.example.tpsynthese.ui.tickets.list.TicketsListViewModel
import com.example.tpsynthese.ui.tickets.list.TicketsRecyclerViewAdapter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class GatewaysListFragment: Fragment(R.layout.fragment_list_gateways) {

    private val binding: FragmentListGatewaysBinding by viewBinding()
    private val viewModel: GatewaysListViewModel by activityViewModels()

    private lateinit var gatewaysRecyclerViewAdapter: GatewaysRecyclerViewAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gatewaysRecyclerViewAdapter = GatewaysRecyclerViewAdapter(listOf(), ::onRecyclerViewGatewayClick)
        binding.rcvGateway.apply {
            layoutManager = GridLayoutManager(activity, 2)
            adapter = gatewaysRecyclerViewAdapter
        }

        viewModel.mainUiState.onEach {
            when(it) {
                is GatewaysListUiState.Error -> {
                    Toast.makeText(requireContext(), it.exception?.localizedMessage?: getString(R.string.apiErrorMessage), Toast.LENGTH_SHORT).show()
                }
                GatewaysListUiState.Loading -> {
                    binding.rcvGateway.visibility = View.GONE
                    binding.pgbLoading.show()
                }
                is GatewaysListUiState.Success -> {
                    binding.rcvGateway.visibility = View.VISIBLE
                    gatewaysRecyclerViewAdapter.gateways = it.gateways
                    gatewaysRecyclerViewAdapter.notifyDataSetChanged()
                    binding.pgbLoading.hide()
                }
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)

    }

    private fun onRecyclerViewGatewayClick(gateway: Gateway) {
        val action = GatewaysListFragmentDirections.actionNavListGatewaysToNavGateways(gateway.href)
        findNavController().navigate(action)
    }


}