package com.example.tpsynthese.data.repositories

import com.example.tpsynthese.core.ApiResult
import com.example.tpsynthese.domain.models.Gateway
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GatewayRepository {

    fun install(gateway: Gateway) : Flow<ApiResult<Gateway>> {
        return flow {
            try {
                emit(ApiResult.Success(gateway.install(gateway)))
            } catch (ex: Exception){
                emit(ApiResult.Error(ex))
            }
        }.flowOn(Dispatchers.IO)
    }

}