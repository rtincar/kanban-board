package io.rtincar.kanbanboard.configuration

import io.rtincar.kanbanboard.account.AccountDataValidation
import io.rtincar.kanbanboard.account.AccountManager
import io.rtincar.kanbanboard.account.IAccountManager
import io.rtincar.kanbanboard.account.MockAccountStore
import io.rtincar.kanbanboard.web.AccountHandler
import io.rtincar.kanbanboard.web.AccountRouter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AccountConfiguration {

    @Bean
    fun accountManager(): IAccountManager = AccountManager(MockAccountStore(), AccountDataValidation())

    @Bean
    fun accountHandler(accountManager: IAccountManager) = AccountHandler(accountManager)

    @Bean
    fun accountRouter(accountHandler: AccountHandler) = AccountRouter(accountHandler).accountRouter()


}