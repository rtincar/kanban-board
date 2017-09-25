package io.rtincar

class AccountData(
        val email: String,
        val firstName: String,
        val lastName: String,
        val password: String,
        val passwordConfirmation: String) {


    fun validate() {
        validateEmail()
        validateFirstName()
        validateLastName()
        validatePassword()
        validatePasswordConfirmation()
    }


    private fun validateFirstName() {
        if (firstName.length < 2)
            throw InvalidAccountDataException("First name should contain at least 2 characters")
    }

    private fun validateLastName() {
        if (lastName.length < 2)
            throw InvalidAccountDataException("Last name should contain at least 2 characters")
    }

    private fun validatePassword() {
        if (passwordHasNotMinLength()
                || passwordHasNotContainOneLowerCaseChar()
                || passwordHasNotContainOneUpperCaseCharacter()
                || passwordHasNotContainOneDigit()
                || passwordHasNotContainOneSpecialCharacter())
            throw InvalidAccountDataException("Invalid password")

    }

    private fun passwordHasNotMinLength() = password.length < 8

    private fun passwordHasNotContainOneLowerCaseChar() = !validateRegex(password, "(.*)([a-z]+)(.*)")

    private fun passwordHasNotContainOneUpperCaseCharacter() = !validateRegex(password, "(.*)([A-Z]+)(.*)")

    private fun passwordHasNotContainOneDigit() = !validateRegex(password, "(.*)([0-9]+)(.*)")

    private fun passwordHasNotContainOneSpecialCharacter() = !validateRegex(password, "(.*)([!-/:-@]+)(.*)")

    private fun validateEmail() {
        val emailRegex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})\$"
        if (!validateRegex(email, emailRegex))
            throw InvalidAccountDataException("Invalid email")
    }

    private fun validatePasswordConfirmation() {
        if (password != passwordConfirmation)
            throw InvalidAccountDataException("Confirmation password doesn't match")
    }

    private fun validateRegex(str: String, regex: String): Boolean = str.matches(Regex(regex))


}