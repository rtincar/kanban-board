package io.rtincar.kanbanboard.web

import com.fasterxml.jackson.databind.ObjectMapper
import io.rtincar.kanbanboard.account.AccountData
import io.rtincar.kanbanboard.account.AccountManager
import org.springframework.http.HttpStatus
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.ServerResponse.status
import org.springframework.web.reactive.function.server.body
import reactor.core.publisher.Mono
import reactor.core.publisher.toMono

class AccountHandler(private val accountManager: AccountManager) {

    fun createAccount(req: ServerRequest): Mono<ServerResponse> {
        val accountData = AccountData("domain@account.com", "First name", "Last name", "4#Mooooo", "4#Mooooo")
        val account = accountManager.createAccount(accountData)
        return status(HttpStatus.CREATED).body(ObjectMapper().writeValueAsString(account).toMono())
    }

}