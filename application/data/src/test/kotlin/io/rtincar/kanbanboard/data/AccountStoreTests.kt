package io.rtincar.kanbanboard.data

import io.rtincar.kanbanboard.account.Account
import io.rtincar.kanbanboard.account.AccountStore
import io.rtincar.kanbanboard.account.DuplicateAccountException
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@ContextConfiguration(classes = arrayOf(DataConfiguration::class))
class AccountStoreTests {

    @Autowired
    private lateinit var accountStore: AccountStore

    @Test
    fun `Should save an account and return account created with unique ID`() {

        //TODO: Continuar con el repositorio
        val savedAccount = accountStore.save(Account(null, "account@domain.com", "John", "Doe", "password"))

        assertNotNull("Should return created account", savedAccount)
        assertNotNull("Account.id shouldn't be null", savedAccount.id)
        val id = savedAccount.id ?: ""
        assertTrue("Account.id shouldn't be empty", id.isNotEmpty())
    }

    @Test
    fun `Should find an account by email`() {
        val exists = accountStore.exists("account@domain.com")
        assertTrue("Should exist", exists)
    }

    @Test(expected = DuplicateAccountException::class)
    fun `Should throw DuplicateAccountData if there is another account with same email`() {
        accountStore.save(Account(null, "account@domain.com", "John", "Doe", "password"))
    }
}