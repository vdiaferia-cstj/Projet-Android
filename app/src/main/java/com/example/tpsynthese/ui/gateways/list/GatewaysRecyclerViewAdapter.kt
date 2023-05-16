package com.example.tpsynthese.ui.gateways.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tpsynthese.R
import com.example.tpsynthese.databinding.ItemGatewayBinding
import com.example.tpsynthese.domain.models.Gateway

class GatewaysRecyclerViewAdapter (var gateways: List<Gateway>, private val onGateWayClick: (Gateway) -> Unit)
    : RecyclerView.Adapter<GatewaysRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_gateway, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: GatewaysRecyclerViewAdapter.ViewHolder, position: Int) {
        val gateway = gateways[position]
        holder.bind(gateway)

        holder.itemView.setOnClickListener {
            onGateWayClick(gateway)
        }
    }

    override fun getItemCount() = gateways.size

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemGatewayBinding.bind(view)

        fun bind(gateway: Gateway) {
            binding.txvSerial.text = gateway.serialNumber
            binding.txvNS.text = gateway.connection.ping.toString() + " ns"
            binding.txvDownload.text = gateway.connection.download.toString()
            binding.txvUpload.text = gateway.connection.upload.toString()
        }
    }
}