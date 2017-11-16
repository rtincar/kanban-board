package io.rtincar.kanbanboard.account

import io.rtincar.kanbanboard.validation.Validation

class AccountManager(private val accountStore: AccountStore, private val validation: Validation<AccountData>) : IAccountManager {

    override fun createAccount(data: AccountData): Account {
        val validationResult = data.isValid(validation)

        if (validationResult.valid) {
            return accountStore.save(data.toAccount())
        } else {
            throw InvalidAccountDataException("Invalid account data: ${validationResult.messages}")
        }

    }

}

