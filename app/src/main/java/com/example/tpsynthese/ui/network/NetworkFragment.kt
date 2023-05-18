package com.example.tpsynthese.ui.network

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.viewbinding.library.fragment.viewBinding
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.tpsynthese.R
import com.example.tpsynthese.databinding.FragmentNetworksBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


class NetworkFragment : Fragment(R.layout.fragment_networks) {
    private val binding: FragmentNetworksBinding by viewBinding()
    private val viewModel: NetworkViewModel by viewModels()

    private val networkRecyclerViewAdapter = NetworkRecyclerViewAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rcvNetwork.apply {
            layoutManager = GridLayoutManager(requireContext(),1)
            adapter = networkRecyclerViewAdapter
        }
        viewModel.networkUiState.onEach{
            when(it){
                NetworkUiState.Empty->Unit
                is NetworkUiState.Error->{
                    Toast.makeText(requireContext(), it.exception.message, Toast.LENGTH_LONG).show()
                }
                is NetworkUiState.Success->{
                    //TODO Afficher l'heure de redemarrage + Last update
                    networkRecyclerViewAdapter.networks = it.networkNode
                    networkRecyclerViewAdapter.notifyDataSetChanged()
                    binding.rcvNetwork.visibility = View.VISIBLE
                }
                NetworkUiState.Loading->Unit
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)

    }

}