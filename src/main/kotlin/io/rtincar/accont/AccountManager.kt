package io.rtincar.accont

class AccountManager(private val accountStore: AccountStore) {

    fun createAccount(data: AccountData): Account {
        data.validate()
        val account = Account(data.email, data.firstName, data.lastName, data.password)
        accountStore.save(account)
        return account

    }

}

