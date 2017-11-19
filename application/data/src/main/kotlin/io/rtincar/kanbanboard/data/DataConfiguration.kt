package io.rtincar.kanbanboard.data

import com.mongodb.reactivestreams.client.MongoClient
import com.mongodb.reactivestreams.client.MongoClients
import io.rtincar.kanbanboard.account.AccountStore
import io.rtincar.kanbanboard.data.accounts.AccountRepository
import io.rtincar.kanbanboard.data.accounts.MongoAccountStore
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.AutoConfigureAfter
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.DependsOn
import org.springframework.core.env.Environment
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories

@Configuration
@EnableReactiveMongoRepositories
@AutoConfigureAfter(EmbeddedMongoAutoConfiguration::class)
@EnableAutoConfiguration
class DataConfiguration: AbstractReactiveMongoConfiguration() {

    @Autowired
    lateinit var env: Environment

    @Bean
    @DependsOn("embeddedMongoServer")
    override fun reactiveMongoClient(): MongoClient {
        // la propiedad local.mongo.port procede de EmbeddedMongoAutoConfiguration
        val port = env.getProperty("local.mongo.port", Int::class.java)
        return MongoClients.create("mongodb://localhost:$port")
    }

    override fun getDatabaseName(): String = "test"

    @Bean
    @Autowired
    fun accountStore(accountRepository: AccountRepository): AccountStore = MongoAccountStore(accountRepository)

}