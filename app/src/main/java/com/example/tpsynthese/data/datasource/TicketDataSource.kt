package com.example.tpsynthese.data.datasource

import com.example.tpsynthese.domain.models.Ticket
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.json.responseJson
import com.github.kittinunf.result.Result
import com.example.tpsynthese.core.Constants
import com.example.tpsynthese.core.JsonDataSource
import com.github.kittinunf.fuel.core.extensions.jsonBody
import com.github.kittinunf.fuel.httpPost
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString

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

    fun changeState(href: String,action:String): Ticket{
        //Mettre en json
        //val body = json.encodeToString(href)
        var post = href + "/action?type=" + action
        //Envoie au serveur avec POST
        val(_,_,result) = post.httpPost().jsonBody(href).responseJson()

        //Gérer la réponse
        return when (result){
            is Result.Success -> json.decodeFromString(result.value.content)
            is Result.Failure -> throw result.error.exception
        }
    }
}