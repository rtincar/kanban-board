package io.rtincar

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable


/**
 * Created by rtinc on 24/09/2017.
 */
@DisplayName("AccountManager Tests")
class AccountManagerTests {

    @Test
    @DisplayName("Create new account")
    fun createAccount()  {
        val accountManager = AccountManager()
        val data = AccountData("account@domain.com", "John", "Doe", "a8Akls@)s", "a8Akls@)s")
        val account = accountManager.createAccount(data)
        Assertions.assertAll("Account data",
                Executable { Assertions.assertNotNull(account) },
                Executable { Assertions.assertEquals(data.email, account.email) },
                Executable { Assertions.assertEquals(data.firstName, account.firstName) },
                Executable { Assertions.assertEquals(data.lastName, account.lastName) }
        )
    }

    @Test
    @DisplayName("Create new account is not active")
    fun createInactiveAccount() {
        val accountManager = AccountManager()
        val data = AccountData("account@domain.com", "John", "Doe", "a8Akls@)s", "a8Akls@)s")
        val account = accountManager.createAccount(data)
        Assertions.assertFalse(account.active)
    }

    @Test
    @DisplayName("Create invalid account")
    fun createInvalidAccount() {
        val accountManager = AccountManager()
        val data = AccountData("account@domain.com", "John", "Doe", "a8Akls@)s", "a8Akls@)k")
        Assertions.assertThrows(InvalidAccountDataException::class.java, {
           accountManager.createAccount(data)
        })
    }


}

