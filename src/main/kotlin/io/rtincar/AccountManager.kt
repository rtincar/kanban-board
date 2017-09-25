package io.rtincar

class AccountManager {

    fun createAccount(data: AccountData): Account {
        data.validate()
        return Account(data.email, data.firstName, data.lastName, data.password)
    }

}