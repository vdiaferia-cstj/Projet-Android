package com.example.tpsynthese.data.datasource

import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.json.responseJson
import com.github.kittinunf.result.Result
import com.example.tpsynthese.core.Constants
import com.example.tpsynthese.core.JsonDataSource
import com.example.tpsynthese.domain.models.Customer
import kotlinx.serialization.decodeFromString

class CustomerDataSource : JsonDataSource() {

    //TODO Verifier si necessaire (de Max)
    fun retrieveAll(): List<Customer> {
        val (_, _, result) = Constants.BaseURL.CUSTOMERS.httpGet().responseJson()

        return when (result) {
            is Result.Success -> json.decodeFromString(result.value.content)
            is Result.Failure -> throw result.error.exception
        }
    }

    fun retrieveOne(href: String): Customer {
        val (_, _ , result) = href.httpGet().responseJson()

        return when(result) {
            is Result.Success -> json.decodeFromString(result.value.content)
            is Result.Failure -> throw result.error.exception
        }

    }
}