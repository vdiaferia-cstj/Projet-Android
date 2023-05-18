package com.example.tpsynthese.data.datasource

import com.example.tpsynthese.core.ApiResult
import com.example.tpsynthese.core.Constants
import com.example.tpsynthese.core.JsonDataSource
import com.example.tpsynthese.domain.models.Customer
import com.example.tpsynthese.domain.models.Gateway
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.fuel.json.responseJson
import com.github.kittinunf.result.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.decodeFromString
import java.lang.Exception


class GatewayDataSource : JsonDataSource(){
    fun retrieveAll(): List<Gateway> {
        val (_, _, result) = Constants.BaseURL.GATEWAYS.httpGet().responseJson()

        return when (result) {
            is Result.Success -> json.decodeFromString(result.value.content)
            is Result.Failure -> throw result.error.exception
        }
    }

    fun retrieveFromCustomer(href: String): List<Gateway> {
        val hrefGET = href + "/gateways"
        val (_, _ , result) = hrefGET.httpGet().responseJson()

        return when(result) {
            is Result.Success -> json.decodeFromString(result.value.content)
            is Result.Failure -> throw result.error.exception
        }
    }

    fun retrieveOne(href: String): Gateway {
        val (_, _ , result) = href.httpGet().responseJson()

        return when(result) {
            is Result.Success -> json.decodeFromString(result.value.content)
            is Result.Failure -> throw result.error.exception
        }

    }

    fun rebootGateway(href : String) : Gateway {
        val urlServeur = "${href}/actions?type=reboot"
        //Pas de body car aucun utilise par la route
        val (_,_,result) = urlServeur.httpPost().responseJson()

        return when(result){
            is Result.Success -> json.decodeFromString(result.value.content)
            is Result.Failure -> throw result.error.exception
        }
    }

    fun updateGateway(href : String) : Gateway {
        val urlServeur = "${href}/actions?type=update"
        //Pas de body car aucun utilise par la route
        val (_,_,result) = urlServeur.httpPost().responseJson()

        return when(result){
            is Result.Success -> json.decodeFromString(result.value.content)
            is Result.Failure -> throw result.error.exception
        }
    }
}