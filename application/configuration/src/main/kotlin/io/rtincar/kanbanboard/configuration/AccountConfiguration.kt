package io.rtincar.kanbanboard.configuration

import io.rtincar.kanbanboard.account.AccountDataValidation
import io.rtincar.kanbanboard.account.AccountManager
import io.rtincar.kanbanboard.account.MockAccountStore
import io.rtincar.kanbanboard.web.AccountHandler
import io.rtincar.kanbanboard.web.AccountRouter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.config.EnableWebFlux

@Configuration
@EnableWebFlux
class AccountConfiguration {

    @Bean
    fun accountManager() = AccountManager(MockAccountStore(), AccountDataValidation())

    @Bean
    fun accountHandler(accountManager: AccountManager) = AccountHandler(accountManager)

    @Bean
    fun accountRouter(accountHandler: AccountHandler) = AccountRouter(accountHandler).accountRouter()

}