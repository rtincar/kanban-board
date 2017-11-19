package io.rtincar.kanbanboard.data

import io.rtincar.kanbanboard.account.Account
import io.rtincar.kanbanboard.account.AccountStore
import io.rtincar.kanbanboard.account.DuplicateAccountException
import io.rtincar.kanbanboard.data.accounts.AccountDocument
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.ResourceLoader
import org.springframework.data.mongodb.core.ReactiveMongoOperations
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@ContextConfiguration(classes = arrayOf(DataConfiguration::class))
class AccountStoreTests {


    @Autowired
    private lateinit var accountStore: AccountStore

    @Autowired
    private lateinit var mongoOperations: ReactiveMongoOperations

    @Autowired
    private lateinit var resourceLoader: ResourceLoader

    @Before
    fun setUp() {

        // TODO: buscar forma de cargar datos desde JSON
        /*
        *
        val resource = resourceLoader.getResource("classpath:data/accounts.json")
        val objectMapper = ObjectMapper()

        */

        mongoOperations.insert(AccountDocument(null, "account1@domain.com", "John", "Doe", "password")).block()
        mongoOperations.insert(AccountDocument(null, "account2@domain.com", "John", "Doe", "password")).block()
        mongoOperations.insert(AccountDocument(null, "account3@domain.com", "John", "Doe", "password")).block()

    }

    @Test
    fun `Should save an account and return account created with unique ID`() {

        //TODO: Continuar con el repositorio
        val savedAccount = accountStore.save(Account(null, "account_new@domain.com", "John", "Doe", "password"))

        assertNotNull("Should return created account", savedAccount)
        assertNotNull("Account.id shouldn't be null", savedAccount.id)
        val id = savedAccount.id ?: ""
        assertTrue("Account.id shouldn't be empty", id.isNotEmpty())
    }

    @Test
    fun `Should find an account by email`() {
        val exists = accountStore.exists("account1@domain.com")
        assertTrue("Should exist", exists)
    }

    @Test(expected = DuplicateAccountException::class)
    fun `Should throw DuplicateAccountData if there is another account with same email`() {
        accountStore.save(Account(null, "account1@domain.com", "John", "Doe", "password"))
    }
}