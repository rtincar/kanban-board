package io.rtincar

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("AccountData Tests")
class AccountDataTests {

    @Test
    @DisplayName("Validate wrong format email")
    fun validateWrongEmail() {
        val accountData = AccountData("", "John", "Doe", "pass123", "pass123")
        val assertThrows= Assertions.assertThrows(InvalidAccountDataException::class.java, {
            accountData.validate()
        })
        Assertions.assertEquals("Invalid email", assertThrows.message)
    }

    @Test
    @DisplayName("Validate right email format")
    fun validateRightEmail() {
        val accountData = AccountData("account@domain.com", "John", "Doe", "pass123", "pass123")
        accountData.validate()
    }

    @Test
    @DisplayName("Validate password confirmation")
    fun validatePassword() {
        val accountData = AccountData("account@domain.com", "John", "Doe", "pass123", "pass12")
        val assertThrows= Assertions.assertThrows(InvalidAccountDataException::class.java, {
            accountData.validate()
        })
        Assertions.assertEquals("Confirmation password doesn't match", assertThrows.message)
    }

}

