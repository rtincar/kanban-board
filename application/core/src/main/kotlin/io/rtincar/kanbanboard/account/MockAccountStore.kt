package io.rtincar.kanbanboard.account

open class MockAccountStore : AccountStore {


    private val accounts = mutableListOf<Account>()

    override fun save(account: Account) {
        if (exists(account.email)) {
            throw DuplicateAccountException("Another io.rtincar.kanbanboard.account exists with the provided email")
        }
        accounts.add(account)
    }

    override fun find(email: String): Account? = accounts.firstOrNull { it.email == email }

    override fun exists(email: String): Boolean = accounts.any { it.email == email }
}