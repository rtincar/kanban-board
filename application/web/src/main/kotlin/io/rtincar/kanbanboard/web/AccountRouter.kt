package io.rtincar.kanbanboard.web

import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.router

class AccountRouter(private val accountHandler: AccountHandler) {

    fun accountRouter() = router {
        "/api/v1".nest {
            accept(MediaType.APPLICATION_JSON).nest {
                POST("/account", accountHandler::createAccount)
            }
        }

    }

}