package io.rtincar.kanbanboard.data

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface AccountRepository: MongoRepository<AccountDocument, String> {
}