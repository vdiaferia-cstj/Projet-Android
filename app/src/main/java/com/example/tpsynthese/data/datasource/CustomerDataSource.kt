package com.example.tpsynthese.data.datasource

import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.json.responseJson
import com.github.kittinunf.result.Result
import com.example.tpsynthese.core.Constants
import com.example.tpsynthese.core.JsonDataSource
import com.example.tpsynthese.domain.models.Customer
import com.example.tpsynthese.domain.models.Gateway
import com.github.kittinunf.fuel.core.extensions.jsonBody
import com.github.kittinunf.fuel.httpPost
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString

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


    fun install(href: String, gateway: Gateway) : Gateway {
        //Mettre en JSON
        val body = json.encodeToString(gateway)
        val hrefPOST = href + "/gateways"
        //Envoie au serveur avec un POST
        val (_,_, result) = hrefPOST.httpPost().jsonBody(body).responseJson()
        //Gérer la réponse
        return when(result){
            is Result.Success -> gateway
            is Result.Failure -> throw result.error.exception
        }
    }
}