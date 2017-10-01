package io.rtincar.kanbanboard.web

import org.springframework.core.io.ClassPathResource
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.router

class AccountRouter(private val accountHandler: AccountHandler) {

    fun accountRouter() = router {
        "/api".nest {
            accept(MediaType.APPLICATION_JSON).nest {
                GET("/accounts", accountHandler::getAccounts)
                POST("/account", accountHandler::createAccount)
            }
        }

        GET("/echo", accountHandler::getAccounts)


        resources("/**", ClassPathResource("static/") )


    }

}