package io.rtincar.account

class AccountStoreImpl: AccountStore {


    private val accounts = mutableListOf<Account>()

    override fun save(account: Account) {
        if (exists(account.email)) {
            throw DuplicateAccountException("Another account exists with the provided email")
        }
        accounts.add(account)
    }

    override fun find(email: String): Account? = accounts.firstOrNull { it.email == email }

    private fun exists(email: String): Boolean = accounts.any { it.email == email }
}