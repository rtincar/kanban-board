package io.rtincar.kanbanboard.data

import io.rtincar.kanbanboard.account.Account
import io.rtincar.kanbanboard.account.AccountStore
import io.rtincar.kanbanboard.account.DuplicateAccountException

class MongoAccountStore(private val accountRepository: AccountRepository) : AccountStore {

    override fun save(account: Account): Account {
        val exists = exists(account.email)
        if (exists) {
            throw DuplicateAccountException("Exists an account with the email ${account.email}")
        } else {
            return accountRepository.save(account.toAccountDocument()).block().toAccount()

        }
    }

    override fun find(email: String): Account? =
        accountRepository.findByEmail(email)?.block()?.toAccount()


    override fun exists(email: String): Boolean {
        val account = accountRepository.findByEmail(email)?.block()
        return when(account) {
            null -> false
            else -> true
        }

    }
}