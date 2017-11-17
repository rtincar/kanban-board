package io.rtincar.kanbanboard.data

import io.rtincar.kanbanboard.account.AccountStore
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories

@Configuration
@EnableReactiveMongoRepositories
class TestDataConfiguration {

    @Bean
    @Autowired
    fun accountStore(accountRepository: AccountRepository): AccountStore = MongoAccountStore(accountRepository)
}