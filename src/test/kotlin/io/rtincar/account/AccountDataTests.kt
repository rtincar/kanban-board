package io.rtincar.account

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable

@DisplayName("AccountData Tests")
class AccountDataTests {

    private val validPassword = "a8Akls@)s"

    private val validation = AccountDataValidation()

    @Test
    @DisplayName("Validate wrong format email")
    fun validateWrongEmail() {
        val accountData = AccountData("", "John", "Doe", validPassword, validPassword)
        isNotValidField(accountData, "Invalid email")

    }

    @Test
    @DisplayName("Validate right email format")
    fun validateRightEmail() {
        val accountData = AccountData("account@domain.com", "John", "Doe", validPassword, validPassword)
        isValidField(accountData)

    }

    @Test
    @DisplayName("Validate password")
    fun validatePassword() {


        val message = "Invalid password"
        Assertions.assertAll("Invalid passwords",
                Executable {
                    val accountData = createAccountDataWithPassword("aaaaaaa")
                    isNotValidField(accountData, message)

                },
                Executable {
                    val accountData = createAccountDataWithPassword("aaaaaaaa")
                    isNotValidField(accountData, message)
                },
                Executable {
                    val accountData = createAccountDataWithPassword("aaAaaaaa")
                    isNotValidField(accountData, message)
                },
                Executable {
                    val accountData = createAccountDataWithPassword("a2Aaaaaa")
                    isNotValidField(accountData, message)
                }
        )

        val accountData = createAccountDataWithPassword(validPassword)
        isValidField(accountData)

    }

    private fun isValidField(accountData: AccountData) {
        val result = accountData.isValid(validation)
        Assertions.assertTrue(result.valid)
    }

    @Test
    @DisplayName("Validate password confirmation")
    fun validatePasswordConfirmation() {
        val accountData = AccountData("account@domain.com", "John", "Doe", validPassword, "a8Akls@)k")
        isNotValidField(accountData, "Confirmation password doesn't match")
    }


    @Test
    @DisplayName("Validate first name")
    fun validateFirstName() {
        val accountData = AccountData("account@domain.com", "a", "Doe", validPassword, validPassword)
        isNotValidField(accountData, "First name should contain at least 2 characters")
    }

    @Test
    @DisplayName("Validate last name")
    fun validateLastName() {
        val accountData = AccountData("account@domain.com", "John", "s", validPassword, validPassword)
        isNotValidField(accountData, "Last name should contain at least 2 characters")
    }

    private fun isNotValidField(accountData: AccountData, message: String) {
        val result = accountData.isValid(validation)
        Assertions.assertFalse(result.valid)
        Assertions.assertTrue(result.messages.size == 1)
        Assertions.assertTrue(result.messages.contains(message))
    }

    private fun createAccountDataWithPassword(password: String): AccountData =
            AccountData("account@domain.com", "John", "Doe", password, password)



}

