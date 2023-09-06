package com.example.tpsynthese.ui.tickets.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tpsynthese.R
import com.example.tpsynthese.core.ColorHelper
import com.example.tpsynthese.core.DateHelper
import com.example.tpsynthese.databinding.TicketCardBinding
import com.example.tpsynthese.domain.models.Ticket

class TicketsRecyclerViewAdapter (
    var tickets: List<Ticket> = listOf(),
    private val onTicketClick: (Ticket) -> Unit):RecyclerView.Adapter<TicketsRecyclerViewAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.ticket_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ticket = tickets[position]
        holder.bind(ticket)

        holder.itemView.setOnClickListener {
            onTicketClick(ticket)
        }

    }

    override fun getItemCount() = tickets.size



    inner class ViewHolder(val view:View): RecyclerView.ViewHolder(view) {
        private val binding = TicketCardBinding.bind(view)

        fun bind(ticket: Ticket)  {
            binding.txvTicket.text = binding.root.context.getString(R.string.ticket_msg,ticket.ticketNumber)
            binding.txvDate.text = DateHelper.formatISODate(ticket.createdDate)
            binding.chipStatus.chipBackgroundColor = ColorHelper.ticketStatusColor(binding.root.context,ticket.status)
            binding.chipPriority.chipBackgroundColor = ColorHelper.ticketPriorityColor(binding.root.context,ticket.priority)
            binding.chipStatus.text = ticket.status
            binding.chipPriority.text = ticket.priority

        }
    }
}