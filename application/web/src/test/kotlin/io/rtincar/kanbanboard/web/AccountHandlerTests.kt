package io.rtincar.kanbanboard.web

import io.rtincar.kanbanboard.configuration.AccountConfiguration
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters


@RunWith(SpringRunner::class)
@ContextConfiguration(classes = arrayOf(AccountConfiguration::class))
@WebFluxTest
class AccountHandlerTests {


    /**
     * TODO: Crear mock de AccountManager
     *
     * El test debe estar orientado a la colaboración de
     * AccountHandler con AccountManager. Debo investigar
     * cómo generar un mock de AccountHandler y probar
     * las llamadas
     *
     *
      */


    @Autowired
    lateinit var webTestClient: WebTestClient

    @Test
    fun `Should return code 201 and JSON with status=ok`() {

        val accountJson = """
        {
        "email" : "account@domain.com",
        "firstName" : "John",
        "lastName" : "Doe",
        "password" : "Kl7!opop",
        "passwordConfirmation" : "Kl7!opop"
        }

        """

        webTestClient.post()
                .uri("/api/v1/account")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(accountJson))
                .exchange()
                .expectStatus().isCreated
                .expectBody().jsonPath("$.status").isEqualTo("ok")
    }

    fun `Should create correct AccountData from received JSON`() {

    }

    fun `Should return error 400 when AccountManager throws an exception`() {

    }
}