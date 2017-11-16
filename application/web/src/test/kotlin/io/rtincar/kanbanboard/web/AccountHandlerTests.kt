package io.rtincar.kanbanboard.web

import com.fasterxml.jackson.databind.ObjectMapper
import io.rtincar.kanbanboard.account.AccountData
import io.rtincar.kanbanboard.account.IAccountManager
import io.rtincar.kanbanboard.account.InvalidAccountDataException
import io.rtincar.kanbanboard.configuration.AccountConfiguration
import io.rtincar.kanbanboard.validation.Validation
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters
import java.lang.RuntimeException


@RunWith(SpringRunner::class)
@ContextConfiguration(classes = arrayOf(AccountConfiguration::class))
@WebFluxTest
class AccountHandlerTests {

    private fun Any.toJson(): String = ObjectMapper().writeValueAsString(this)

    @MockBean
    private lateinit var accountManager: IAccountManager

    @Autowired
    private lateinit var webTestClient: WebTestClient

    private val EMPTY_ACCOUNT = AccountData("", "", "", "", "")

    private val accountJson = """

    {
        "email" : "account@domain.com",
        "firstName" : "John",
        "lastName" : "Doe",
        "password" : "Kl7!opop",
        "passwordConfirmation" : "Kl7!opop"
    }

    """

    @Test
    fun `Should return code 201 and JSON with status=ok when account is created`() {

        postAccount(accountJson)
                .expectStatus().isCreated
                .expectBody().jsonPath("$.status").isEqualTo("ok")
    }

    @Test
    fun `Should return code 400 and JSON with status=error when it doesn't get all account fields`() {

        val givenAPartialAccountJson = """

        {
            "email" : "account@domain.com",
            "firstName" : "John",
            "password" : "Kl7!opop",
            "passwordConfirmation" : "Kl7!opop"
        }

        """

        postAccount(givenAPartialAccountJson)
                .expectStatus().isBadRequest
                .expectBody()
                .jsonPath("$.status").isEqualTo("error")
                .jsonPath("$.message").isNotEmpty
    }


    @Test
    fun `Should return code 400 and JSON with status=error when AccountManager throws an exception`() {
        val e = RuntimeException("ValidationException")

        `when`(accountManager.createAccount(EMPTY_ACCOUNT))
                .thenThrow(e)

        postAccount(EMPTY_ACCOUNT.toJson())
                .expectStatus().isBadRequest
                .expectBody()
                    .jsonPath("$.status").isEqualTo("error")
                    .jsonPath("$.message").isNotEmpty
    }

    private fun postAccount(accountData: String): WebTestClient.ResponseSpec {
        return webTestClient.post()
                .uri("/api/v1/account")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(accountData))
                .exchange()
    }
}