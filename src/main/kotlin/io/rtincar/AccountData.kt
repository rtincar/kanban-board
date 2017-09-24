package io.rtincar

import java.util.regex.Pattern

class AccountData(
        val email: String,
        val firstName: String,
        val lastName: String,
        val password: String,
        val passwordConfirmation: String) {



    fun validate() {
        validateEmail()
        validatePasswordConfirmation()
    }

    private fun validateEmail() {
        val emailPattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})\$")
        if (!emailPattern.matcher(email).matches()) throw InvalidAccountDataException("Invalid email")
    }

    private fun validatePasswordConfirmation() {
        if (password != passwordConfirmation) throw InvalidAccountDataException("Confirmation password doesn't match")
    }


}