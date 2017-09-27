package io.rtincar.account

import io.rtincar.validation.Validatable
import io.rtincar.validation.Validation

class AccountData(
        val email: String,
        val firstName: String,
        val lastName: String,
        val password: String,
        val passwordConfirmation: String): Validatable<AccountData> {

    override fun isValid(validation: Validation<AccountData>): Validation.ValidationResult = validation.validate(this)

}