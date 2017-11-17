package io.rtincar.kanbanboard.data

import io.rtincar.kanbanboard.account.Account
import io.rtincar.kanbanboard.account.AccountStore
import java.util.*

class MongoAccountStore(private val accountRepository: AccountRepository) : AccountStore {

    override fun save(account: Account): Account =
        accountRepository.save(account.toAccountDocument()).toAccount()

    override fun find(email: String): Account? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return null
    }

    override fun exists(email: String): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return false
    }
}