package com.example.tpsynthese.ui.network

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tpsynthese.R
import com.example.tpsynthese.core.ColorHelper
import com.example.tpsynthese.databinding.NetworkCardBinding
import com.example.tpsynthese.domain.models.Network
import com.example.tpsynthese.domain.models.NetworkNode
import java.security.AccessController.getContext

class NetworkRecyclerViewAdapter(var networks: List<NetworkNode> = listOf()):
    RecyclerView.Adapter<NetworkRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NetworkRecyclerViewAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.network_card, parent, false)
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: NetworkRecyclerViewAdapter.ViewHolder, position: Int) {
        val network = networks[position]
        holder.bind(network)

    }

    override fun getItemCount() = networks.count()


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = NetworkCardBinding.bind(view)

        fun bind(network: NetworkNode) {
            binding.txvIP.text = network.connection.ip
            binding.txvLatence.text = network.connection.ping.toString()
            binding.txvNom.text = network.name
            binding.txvQualiteSignal.text = network.connection.signal.toString()
            binding.txvVitesseTelecharge.text = network.connection.download.toString()
            binding.txvVitesseTeleverse.text = network.connection.upload.toString()
            binding.chipStatus.text = network.connection.status
            binding.chipStatus.chipBackgroundColor = ColorHelper.connectionStatusColor(binding.root.context,network.connection.status)
        }
    }
}