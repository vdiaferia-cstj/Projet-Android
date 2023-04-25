package com.example.tpsynthese.data

import com.example.tpsynthese.domain.models.Ticket
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.json.responseJson
import com.github.kittinunf.result.Result
import com.example.tpsynthese.core.Constants
import com.example.tpsynthese.core.JsonDataSource
import kotlinx.serialization.decodeFromString

class TicketDataSource : JsonDataSource() {
    fun retrieveAll(): List<Ticket> {
        val (_, _, result) = Constants.BaseURL.TICKETS.httpGet().responseJson()

        return when (result) {
            is Result.Success -> json.decodeFromString(result.value.content)
            is Result.Failure -> throw result.error.exception
        }
    }

    fun retrieveOne(href: String): Ticket {
        val (_, _ , result) = href.httpGet().responseJson()

        return when(result) {
            is Result.Success -> json.decodeFromString(result.value.content)
            is Result.Failure -> throw result.error.exception
        }

    }
}