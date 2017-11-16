package io.rtincar.kanbanboard.web

import com.fasterxml.jackson.databind.ObjectMapper
import io.rtincar.kanbanboard.account.AccountData
import io.rtincar.kanbanboard.account.IAccountManager
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.badRequest
import org.springframework.web.reactive.function.server.ServerResponse.status
import org.springframework.web.reactive.function.server.body
import reactor.core.publisher.Mono
import reactor.core.publisher.toMono

class AccountHandler(private val accountManager: IAccountManager) {

    private val log = LoggerFactory.getLogger(this.javaClass)

    fun createAccount(req: ServerRequest): Mono<ServerResponse> {
        return try {
            val accountData = req.bodyToMono(AccountData::class.java).block()
            accountManager.createAccount(accountData)
            status(HttpStatus.CREATED).body(ObjectMapper().writeValueAsString(ResponseData("ok", null)).toMono())
            // TODO: Ver documentacion de gestion de excepciones en WebFlux
        } catch (e: Exception) {
            log.error("Error creating new account", e)
            badRequest().body(ObjectMapper().writeValueAsString(ResponseData("error", e.message)).toMono())
        }
    }

}