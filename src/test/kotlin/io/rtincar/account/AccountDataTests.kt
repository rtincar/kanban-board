package io.rtincar.account

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable

@DisplayName("AccountData Tests")
class AccountDataTests {

    private val validPassword = "a8Akls@)s"

    @Test
    @DisplayName("Validate wrong format email")
    fun validateWrongEmail() {
        val accountData = AccountData("", "John", "Doe", validPassword, validPassword)
        assertValidateException(accountData, "Invalid email")

    }

    @Test
    @DisplayName("Validate right email format")
    fun validateRightEmail() {
        val accountData = AccountData("account@domain.com", "John", "Doe", validPassword, validPassword)
        accountData.validate()
    }

    @Test
    @DisplayName("Validate password")
    fun validatePassword() {


        Assertions.assertAll("Invalid passwords",
                Executable {
                    val accountData = createAccountDataWithPassword("aaaaaaa")
                    assertValidateException(accountData, "Invalid password")
                },
                Executable {
                    val accountData = createAccountDataWithPassword("aaaaaaaa")
                    assertValidateException(accountData, "Invalid password")
                },
                Executable {
                    val accountData = createAccountDataWithPassword("aaAaaaaa")
                    assertValidateException(accountData, "Invalid password")
                },
                Executable {
                    val accountData = createAccountDataWithPassword("a2Aaaaaa")
                    assertValidateException(accountData, "Invalid password")
                }
        )

        val accountData = createAccountDataWithPassword(validPassword)
        accountData.validate()

    }

    @Test
    @DisplayName("Validate password confirmation")
    fun validatePasswordConfirmation() {
        val accountData = AccountData("account@domain.com", "John", "Doe", validPassword, "a8Akls@)k")
        assertValidateException(accountData, "Confirmation password doesn't match")
    }


    @Test
    @DisplayName("Validate first name")
    fun validateFirstName() {
        val accountData = AccountData("account@domain.com", "a", "Doe", validPassword, "a8Akls@)k")
        assertValidateException(accountData, "First name should contain at least 2 characters")
    }

    @Test
    @DisplayName("Validate last name")
    fun validateLastName() {
        val accountData = AccountData("account@domain.com", "John", "s", validPassword, "a8Akls@)k")
        assertValidateException(accountData, "Last name should contain at least 2 characters")
    }

    private fun createAccountDataWithPassword(password: String): AccountData =
            AccountData("account@domain.com", "John", "Doe", password, password)

    private fun assertValidateException(accountData: AccountData, message: String) {
        val assertThrows= Assertions.assertThrows(InvalidAccountDataException::class.java, {
            accountData.validate()
        })
        Assertions.assertEquals(message, assertThrows.message)
    }

}

