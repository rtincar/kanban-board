package io.rtincar.kanbanboard.account

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable


/**
 * Created by rtinc on 24/09/2017.
 */
@DisplayName("AccountManager Tests")
class AccountManagerTests {

    private val validAccountData: AccountData = AccountData("io.rtincar.kanbanboard.account@domain.com", "John", "Doe", "a8Akls@)s", "a8Akls@)s")
    private val invalidAccountData: AccountData = AccountData("io.rtincar.kanbanboard.account@domain.com", "John", "Doe", "a8Akls@)s", "a8Akls@)k")
    private val accountManager: AccountManager = AccountManager(MockAccountStore(), AccountDataValidation())

    @Test
    @DisplayName("Create new io.rtincar.kanbanboard.account")
    fun createAccount()  {
        val account = accountManager.createAccount(validAccountData)
        Assertions.assertAll("New io.rtincar.kanbanboard.account data",
                Executable { Assertions.assertNotNull(account) },
                Executable { Assertions.assertEquals(validAccountData.email, account.email) },
                Executable { Assertions.assertEquals(validAccountData.firstName, account.firstName) },
                Executable { Assertions.assertEquals(validAccountData.lastName, account.lastName) },
                Executable { Assertions.assertFalse(account.active) }
        )
    }


    @Test
    @DisplayName("Create invalid io.rtincar.kanbanboard.account")
    fun createInvalidAccount() {
        Assertions.assertThrows(InvalidAccountDataException::class.java, {
           accountManager.createAccount(invalidAccountData)
        })
    }


    @Test
    @DisplayName("Create an io.rtincar.kanbanboard.account that exists")
    fun createExistentAccount() {
        Assertions.assertThrows(DuplicateAccountException::class.java, {
            accountManager.createAccount(validAccountData)
            accountManager.createAccount(validAccountData)
        })
    }




}
