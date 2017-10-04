package io.rtincar.kanbanboard.account

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Account Store Tests")
class AccountStoreTests {

    private val accountStore: AccountStore = MockAccountStore()


    @Test
    @DisplayName("Save Account")
    fun save() {
        val account = createAccount("io.rtincar.kanbanboard.account@domain.com")
        accountStore.save(account)
        val accountCreated = accountStore.find(account.email)
        Assertions.assertNotNull(accountCreated?.id)
        Assertions.assertEquals(account, accountCreated)
    }

    @Test
    @DisplayName("Save duplicate Account")
    fun saveDuplicateAccount() {
        val account = createAccount("account1@domain.com")
        accountStore.save(account)
        Assertions.assertThrows(DuplicateAccountException::class.java, {
            accountStore.save(account)
        })

    }

    @Test
    @DisplayName("Find an Account that doesn't exist")
    fun findNonExistsAccount() {
        val account = accountStore.find("acc@dom.com")
        Assertions.assertNull(account)
    }


    private fun createAccount(email: String): Account = Account(null, email, "First name", "Last name", "@sdsA343Ssa")
}