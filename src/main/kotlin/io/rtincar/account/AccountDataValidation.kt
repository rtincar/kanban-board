package io.rtincar.account

import io.rtincar.validation.Validation

class AccountDataValidation: Validation<AccountData> {

    override fun validate(o: AccountData): Validation.ValidationResult {
        val messages = mutableSetOf<String>()
        validateEmail(o, messages)
        validateFirstName(o, messages)
        validateLastName(o, messages)
        validatePassword(o, messages)
        validatePasswordConfirmation(o, messages)
        return Validation.ValidationResult(messages.isEmpty(), messages)
    }

    private fun validateEmail(accountData: AccountData, messages: MutableSet<String>) {
        val emailRegex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})\$"
        if (!validateRegex(accountData.email, emailRegex))
            messages.add("Invalid email")

    }

    private fun validateRegex(str: String, regex: String): Boolean = str.matches(Regex(regex))

    private fun validateFirstName(accountData: AccountData, messages: MutableSet<String>) {
        if (accountData.firstName.length < 2)
            messages.add("First name should contain at least 2 characters")
    }

    private fun validateLastName(accountData: AccountData, messages: MutableSet<String>) {
        if (accountData.lastName.length < 2)
            messages.add("Last name should contain at least 2 characters")
    }

    private fun validatePassword(accountData: AccountData, messages: MutableSet<String>) {
        if (passwordHasNotMinLength(accountData.password)
                || passwordHasNotContainOneLowerCaseChar(accountData.password)
                || passwordHasNotContainOneUpperCaseCharacter(accountData.password)
                || passwordHasNotContainOneDigit(accountData.password)
                || passwordHasNotContainOneSpecialCharacter(accountData.password))
            messages.add("Invalid password")

    }

    private fun passwordHasNotMinLength(password: String) = password.length < 8

    private fun passwordHasNotContainOneLowerCaseChar(password: String) = !validateRegex(password, "(.*)([a-z]+)(.*)")

    private fun passwordHasNotContainOneUpperCaseCharacter(password: String) = !validateRegex(password, "(.*)([A-Z]+)(.*)")

    private fun passwordHasNotContainOneDigit(password: String) = !validateRegex(password, "(.*)([0-9]+)(.*)")

    private fun passwordHasNotContainOneSpecialCharacter(password: String) = !validateRegex(password, "(.*)([!-/:-@]+)(.*)")

    private fun validatePasswordConfirmation(accountData: AccountData, messages: MutableSet<String>) {
        if (accountData.password != accountData.passwordConfirmation)
            messages.add("Confirmation password doesn't match")
    }


}