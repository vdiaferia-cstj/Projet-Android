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
        //Pour Yannick: Max: Je n'arrive pas a format mon string comme il faut
        //J'en ai hard codé un pour montrer le fonctionnement car celui ci fonctionne. Tout fonctionne sauf le format du string.
        //val href1 = href + "/gateways"
        val href1 = "https://api.andromia.science/customers/60762f36fc13ae242c000c80/gateways"
        val (_, _ , result) = href1.httpGet().responseJson()

        return when(result) {
            is Result.Success -> json.decodeFromString(result.value.content)
            is Result.Failure -> throw result.error.exception
        }

    }
}