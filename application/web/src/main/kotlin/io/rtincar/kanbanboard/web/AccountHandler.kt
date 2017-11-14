package io.rtincar.kanbanboard.web

import com.fasterxml.jackson.databind.ObjectMapper
import io.rtincar.kanbanboard.account.AccountData
import io.rtincar.kanbanboard.account.AccountManager
import io.rtincar.kanbanboard.account.DuplicateAccountException
import io.rtincar.kanbanboard.account.InvalidAccountDataException
import org.springframework.http.HttpStatus
import org.springframework.web.reactive.function.BodyExtractors
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.badRequest
import org.springframework.web.reactive.function.server.ServerResponse.status
import org.springframework.web.reactive.function.server.body
import reactor.core.publisher.Mono
import reactor.core.publisher.toMono

class AccountHandler(private val accountManager: AccountManager) {

    fun createAccount(req: ServerRequest): Mono<ServerResponse> {
        return try {
            val accountData = getAccountData(req)
            accountManager.createAccount(accountData)
            status(HttpStatus.CREATED).body(ObjectMapper().writeValueAsString(ResponseData("ok", null)).toMono())
            // TODO: Ver documentacion de gestion de excepciones en WebFlux
        } catch (e: Exception) {
            badRequest().body(ObjectMapper().writeValueAsString(ResponseData("error", e.message)).toMono())
        }

    }

    // TODO: Refaaaactooooooor. Estudiar BodyExtractor
    private fun getAccountData(req: ServerRequest): AccountData {
        val valueMap = req.body(BodyExtractors.toFormData()).block()
         return AccountData(
                valueMap["email"]?.get(0) ?: "",
                valueMap["firstName"]?.get(0) ?: "",
                valueMap["lastName"]?.get(0) ?: "",
                valueMap["password"]?.get(0) ?: "",
                valueMap["passwordConfirmation"]?.get(0) ?: ""

        )
    }

}