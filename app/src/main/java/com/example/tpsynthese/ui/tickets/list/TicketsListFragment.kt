package com.example.tpsynthese.ui.tickets.list

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import android.viewbinding.library.fragment.viewBinding
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tpsynthese.R
import com.example.tpsynthese.databinding.FragmentListTicketsBinding
import com.example.tpsynthese.domain.models.Ticket
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class TicketsListFragment : Fragment(R.layout.fragment_list_tickets) {
    private val binding: FragmentListTicketsBinding by viewBinding()
    private val viewModel: TicketsListViewModel by activityViewModels()

    private lateinit var ticketRecyclerViewAdapter: TicketsRecyclerViewAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ticketRecyclerViewAdapter = TicketsRecyclerViewAdapter(listOf(), ::onRecyclerViewTicketClick)
        binding.rcvTicket.apply {
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
            adapter = ticketRecyclerViewAdapter
        }

        viewModel.mainUiState.onEach {
            when(it) {
                is TicketsListUiState.Error -> {
                    Toast.makeText(requireContext(), it.exception?.localizedMessage?: getString(R.string.apiErrorMessage), Toast.LENGTH_SHORT).show()
                }
                is TicketsListUiState.Success -> {
                    binding.rcvTicket.visibility = View.VISIBLE

                    ticketRecyclerViewAdapter.tickets = it.ticket
                    ticketRecyclerViewAdapter.notifyDataSetChanged()
                    binding.pgbLoading.hide()

                }

                TicketsListUiState.Loading -> {
                    binding.rcvTicket.visibility = View.GONE
                    binding.pgbLoading.show()
                }
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)

    }


    private fun onRecyclerViewTicketClick(ticket:Ticket) {
        val action = TicketsListFragmentDirections.actionNavListTicketsToTicketFragment(ticket.customer.href)
        findNavController().navigate(action)
    }
}