package io.rtincar.kanbanboard.account

import java.util.*

open class MockAccountStore : AccountStore {


    private val accounts = mutableListOf<Account>()

    override fun save(account: Account): Account {
        if (exists(account.email)) {
            throw DuplicateAccountException("Another account exists with the provided email")
        }
        account.id = UUID.randomUUID().toString()
        accounts.add(account)
        return account
    }

    override fun find(email: String): Account? = accounts.firstOrNull { it.email == email }

    override fun exists(email: String): Boolean = accounts.any { it.email == email }
}