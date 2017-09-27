package io.rtincar.account

import io.rtincar.validation.Validation

class AccountManager(private val accountStore: AccountStore, private val validation: Validation<AccountData>) {

    fun createAccount(data: AccountData): Account {
        val validationResult = data.isValid(validation)
        if (validationResult.valid) {
            val account = Account(data.email, data.firstName, data.lastName, data.password)
            accountStore.save(account)
            return account
        } else {
            throw InvalidAccountDataException("Invalid account data: ${validationResult.messages}")
        }

    }

}

