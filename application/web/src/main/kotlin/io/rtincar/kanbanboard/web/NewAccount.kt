package io.rtincar.kanbanboard.web

import io.rtincar.kanbanboard.account.AccountData

data class NewAccount(
    var email: String,
    var firstName: String,
    var lastName: String,
    var password: String,
    var passwordConfirmation: String) {
    fun toAccountData(): AccountData = AccountData(email, firstName, lastName, password, passwordConfirmation)
}