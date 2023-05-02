package com.example.tpsynthese.ui.loading

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.tpsynthese.MainActivity
import com.example.tpsynthese.R
import com.example.tpsynthese.core.Constants
import com.example.tpsynthese.databinding.ActivityLoadingBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class LoadingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoadingBinding
    private val viewModel: LoadingViewModel by viewModels()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoadingBinding.inflate(layoutInflater)
        setContentView(binding.root)




        viewModel.loadingUiState.onEach {
            when(it){
                LoadingUiState.Empty -> Unit
                LoadingUiState.Success -> {
                   startActivity(MainActivity.newIntent(this))
                }
                is LoadingUiState.Loading -> {
                    binding.txtLoading.text = getString(R.string.loading_msg, it.secondes)
                    binding.pgbLoading.setProgress(it.progression, true)
                }
            }
        }.launchIn(lifecycleScope)
    }
}