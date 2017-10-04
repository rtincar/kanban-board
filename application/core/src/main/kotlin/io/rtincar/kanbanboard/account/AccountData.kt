package io.rtincar.kanbanboard.account

import io.rtincar.kanbanboard.validation.Validatable
import io.rtincar.kanbanboard.validation.Validation

class AccountData(
        val email: String,
        val firstName: String,
        val lastName: String,
        val password: String,
        val passwordConfirmation: String): Validatable<AccountData> {

    override fun isValid(validation: Validation<AccountData>): Validation.ValidationResult = validation.validate(this)

    fun toAccount() = Account(null, email, firstName, lastName, password)
}