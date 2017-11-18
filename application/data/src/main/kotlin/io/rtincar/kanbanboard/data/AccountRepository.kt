package io.rtincar.kanbanboard.data

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
interface AccountRepository: ReactiveMongoRepository<AccountDocument, String> {

    fun findByEmail(email: String): Mono<AccountDocument>?
}