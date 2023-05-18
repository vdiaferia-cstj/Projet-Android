package com.example.tpsynthese.data.repositories

import com.example.tpsynthese.core.ApiResult
import com.example.tpsynthese.core.Constants
import com.example.tpsynthese.data.datasource.GatewayDataSource
import com.example.tpsynthese.data.datasource.TicketDataSource
import com.example.tpsynthese.domain.models.Customer
import com.example.tpsynthese.domain.models.Gateway
import com.example.tpsynthese.domain.models.Ticket
import com.google.android.gms.common.api.Api
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GatewayRepository {

    private val gatewayDataSource = GatewayDataSource()

    fun retrieveOne(href:String) : Flow<ApiResult<Gateway>> {
        return flow {
            emit(ApiResult.Loading)
            try {

                emit(ApiResult.Success(gatewayDataSource.retrieveOne(href)))

            } catch (ex: java.lang.Exception) {
                emit(ApiResult.Error(ex))
            }
        }.flowOn(Dispatchers.IO)
    }

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

    fun retrieveCustomerGateways(href : String) : Flow<ApiResult<List<Gateway>>>?{
         //val href1 = "https://api.andromia.science/customers/60762f36fc13ae242c000c80/gateways"
        return flow{
            emit(ApiResult.Loading)
            try{
                emit(ApiResult.Success(gatewayDataSource.retrieveFromCustomer(href)))
            }
            catch (ex: Exception){
                emit(ApiResult.Error(ex))
            }
        }.flowOn(Dispatchers.IO)
    }

    fun reboot(href : String) : Flow<ApiResult<Gateway>> {
        return flow{
            try {
                emit(ApiResult.Success(gatewayDataSource.rebootGateway(href)))
            }
            catch (ex: java.lang.Exception){
                emit(ApiResult.Error(ex))
            }
        }.flowOn(Dispatchers.IO)
    }

    fun update(href: String): Flow<ApiResult<Gateway>>{
        return flow{
            try {
                emit(ApiResult.Success(gatewayDataSource.updateGateway(href)))
            }
            catch (ex: java.lang.Exception){
                emit(ApiResult.Error(ex))
            }
        }.flowOn(Dispatchers.IO)
    }

}