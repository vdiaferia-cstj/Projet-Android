package com.example.tpsynthese.ui.gateways.list

import android.opengl.Visibility
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
            if (gateway.connection.status == "Online")
            {
                binding.chipStatus.text = "Online"
                binding.txvSerial.text = gateway.serialNumber
                binding.txvNA.visibility = View.GONE
                binding.txvNS.text = gateway.connection.ping.toString() + " ns"
                binding.txvDownload.text = gateway.connection.download.toString() + " Ebps"
                binding.txvUpload.text = gateway.connection.upload.toString() + " Ebps"
                binding.chipStatus.setChipBackgroundColorResource(R.color.gateway_status_online)
                binding.imvUpload.setImageResource(R.drawable.ic_outline_cloud_upload_24)
                binding.imvDownload.setImageResource(R.drawable.ic_outline_cloud_download_24)
                binding.imvNs.setImageResource(R.drawable.ic_baseline_sync_alt_24)
            }
            if (gateway.connection.status == "Offline")
            {
                binding.chipStatus.text = "Offline"
                binding.txvNA.visibility = View.VISIBLE
                binding.txvSerial.text = gateway.serialNumber
                binding.txvNS.text = ""
                binding.txvDownload.text = ""
                binding.txvUpload.text = ""
                binding.imvUpload.setImageDrawable(null)
                binding.imvDownload.setImageDrawable(null)
                binding.imvNs.setImageDrawable(null)
                binding.chipStatus.setChipBackgroundColorResource(R.color.gateway_status_offline)
            }

        }
    }
}