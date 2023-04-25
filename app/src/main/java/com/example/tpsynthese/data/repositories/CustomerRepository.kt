package com.example.tpsynthese.data.repositories

import com.example.tpsynthese.core.ApiResult
import com.example.tpsynthese.data.datasource.CustomerDataSource
import com.example.tpsynthese.domain.models.Customer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception

class CustomerRepository {
    private val customerDataSource = CustomerDataSource()

    fun retrieveOne(href:String) : Flow<ApiResult<Customer>> {
        return flow {
            emit(ApiResult.Loading)
            try {
                emit(ApiResult.Success(customerDataSource.retrieveOne(href)))
            } catch (ex: Exception) {
                emit(ApiResult.Error(ex))
            }
        }.flowOn(Dispatchers.IO)
    }
}