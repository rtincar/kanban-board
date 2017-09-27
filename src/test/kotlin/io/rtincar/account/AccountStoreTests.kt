package io.rtincar.account

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Account Store Tests")
class AccountStoreTests {

    private val accountStore: AccountStore = MockAccountStore()


    @Test
    @DisplayName("Save Account")
    fun save() {
        val account = createAccount("account@domain.com")
        accountStore.save(account)
        val accountCreated = accountStore.find(account.email)
        Assertions.assertEquals(account, accountCreated)
    }

    @Test
    @DisplayName("Save duplicate account")
    fun saveDuplicateAccount() {
        val account = createAccount("account1@domain.com")
        accountStore.save(account)
        Assertions.assertThrows(DuplicateAccountException::class.java, {
            accountStore.save(account)
        })

    }

    @Test
    @DisplayName("Find account that doesn't exist")
    fun findNonExistsAccount() {
        val account = accountStore.find("acc@dom.com")
        Assertions.assertNull(account)
    }


    private fun createAccount(email: String): Account = Account(email, "First name", "Last name", "@sdsA343Ssa")
}