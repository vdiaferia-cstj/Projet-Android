package com.example.tpsynthese.data.repositories

import com.example.tpsynthese.core.ApiResult
import com.example.tpsynthese.core.Constants
import com.example.tpsynthese.data.datasource.GatewayDataSource
import com.example.tpsynthese.data.datasource.TicketDataSource
import com.example.tpsynthese.domain.models.Gateway
import com.example.tpsynthese.domain.models.Ticket
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GatewayRepository {

    private val gatewayDataSource = GatewayDataSource()

    fun retrieveAll(): Flow<ApiResult<List<Gateway>>> {
        return flow {
            while (true) {
                emit(ApiResult.Loading)
                try {
                    emit(ApiResult.Success(gatewayDataSource.retrieveAll()))
                } catch (ex: Exception) {
                    emit(ApiResult.Error(ex))
                }
                delay(Constants.RefreshDelay.TICKET_DELAY)
            }
        }.flowOn(Dispatchers.IO)
    }

}