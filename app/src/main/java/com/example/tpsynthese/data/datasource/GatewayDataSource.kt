package com.example.tpsynthese.data.datasource

import com.example.tpsynthese.core.Constants
import com.example.tpsynthese.core.JsonDataSource
import com.example.tpsynthese.domain.models.Gateway
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.json.responseJson
import com.github.kittinunf.result.Result
import kotlinx.serialization.decodeFromString


class GatewayDataSource : JsonDataSource(){
    fun retrieveAll(): List<Gateway> {
        val (_, _, result) = Constants.BaseURL.GATEWAYS.httpGet().responseJson()

        return when (result) {
            is Result.Success -> json.decodeFromString(result.value.content)
            is Result.Failure -> throw result.error.exception
        }
    }

    fun retrieveFromCustomer(href: String): List<Gateway> {
        val (_, _ , result) = Constants.BaseURL.GATEWAY_CUSTOMER.format(href).httpGet().responseJson()

        return when(result) {
            is Result.Success -> json.decodeFromString(result.value.content)
            is Result.Failure -> throw result.error.exception
        }

    }
}