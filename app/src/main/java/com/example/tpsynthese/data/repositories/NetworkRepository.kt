package com.example.tpsynthese.data.repositories

import com.example.tpsynthese.core.ApiResult
import com.example.tpsynthese.core.Constants
import com.example.tpsynthese.data.datasource.NetworkDataSource
import com.example.tpsynthese.domain.models.Network
import com.example.tpsynthese.domain.models.NetworkNode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class NetworkRepository {

    private val networkDataSource = NetworkDataSource()

    private lateinit var network : Network
    private var networkNode: List<NetworkNode> = listOf()

    fun retrieveAll(): Flow<ApiResult<List<NetworkNode>>> {
        return flow {
            while(true){
                emit(ApiResult.Loading)
                try {
                    network = networkDataSource.retrieveAll()
                    networkNode = network.nodes
                    emit(ApiResult.Success(networkNode))
                }
                catch(ex: Exception) {
                    emit(ApiResult.Error(ex))
                }
                delay(Constants.RefreshDelay.NETWORK_DELAY)
            }
        }.flowOn(Dispatchers.IO)

    }
}