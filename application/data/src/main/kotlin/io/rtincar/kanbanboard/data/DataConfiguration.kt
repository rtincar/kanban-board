package io.rtincar.kanbanboard.data

import com.mongodb.reactivestreams.client.MongoClient
import com.mongodb.reactivestreams.client.MongoClients
import io.rtincar.kanbanboard.account.AccountStore
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories

@Configuration
@EnableReactiveMongoRepositories("io.rtincar.kanbanboard.data")
class DataConfiguration: AbstractReactiveMongoConfiguration() {

    override fun reactiveMongoClient(): MongoClient = MongoClients.create("mongodb://127.0.0.1:27017")

    override fun getDatabaseName(): String = "test"

    @Bean
    @Autowired
    fun accountStore(accountRepository: AccountRepository): AccountStore = MongoAccountStore(accountRepository)

}