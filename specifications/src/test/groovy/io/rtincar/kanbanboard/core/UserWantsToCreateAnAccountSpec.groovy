package io.rtincar.kanbanboard.core

import groovy.json.JsonOutput
import io.rtincar.kanbanboard.configuration.AccountConfiguration
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.config.EnableWebFlux
import org.springframework.web.reactive.function.BodyInserters
import spock.lang.*

@Title('An user wants to create an account')
@Narrative('''
 
 In order to access the system
 As an anonymous user 
 I want to create an account
 
''')
@ContextConfiguration(classes = [AccountConfiguration])
@WebFluxTest
class UserWantsToCreateAnAccountSpec extends Specification {

    @Autowired
    WebTestClient testClient

    void "Should create new account and receive an activation email"() {

        given: "A valid account JSON data"
        def accountData = JsonOutput.toJson([
                email: 'account@domain.com',
                firstName: 'John',
                lastName: 'Doe',
                password: 'K21$kljo',
                confirmationPassword: 'K21$kljo'
        ])

        when: "Try to create an account"

        def exchange = postAccountData(accountData)


        then: "A JSON with status = ok is received"
        exchange.expectStatus().isCreated()
        exchange.expectBody().jsonPath('$.status').isEqualTo('ok')

    }

    @Unroll
    void "Should get an error when #reason"() {

        given: "An invalid account"
        def accountData = JsonOutput.toJson([
                email: email,
                firstName: firstName,
                lastName: lastName,
                password: password,
                confirmationPassword: confirmationPassword
        ])


        when: "Try to create the account"

        def exchange = postAccountData(accountData)

        then: "Should get JSON with status = error and detailed info"

        exchange.expectStatus().isBadRequest()
        exchange.expectBody().jsonPath('$.status').isEqualTo('error')

        where:
        email                | firstName | lastName | password   | confirmationPassword | reason
        ''                   | ''        | ''       | ''         | ''                   | 'All values empties'
        'account@domain'     | 'John'    | 'Doe'    | 'K21$kljo' | 'K21$kljo'           | 'Invalid email'
        'account@domain.com' | ''        | 'Doe'    | 'K21$klyy' | 'K21$klyy'           | 'First name is empty'
        'account@domain.com' | 'John'    | ''       | 'K21$kljo' | 'K21$kljo'           | 'Last name empty'
        'account@domain.com' | 'John'    | 'Doe'    | 'K21$klj6' | 'K21$kljo'           | "Confirmation password doesn't match"
        'account@domain.com' | 'John'    | 'Doe'    | 'K21$kl'   | 'K21$kl'             | 'Password has less than 8 characters'
        'account@domain.com' | 'John'    | 'Doe'    | 'K212klyy' | 'K212klyy'           | "Password doesn't have at least one special character"
        'account@domain.com' | 'John'    | 'Doe'    | 'Kttt$lyy' | 'Kttt$lyy'           | "Password doesn't have at least one digit"
        'account@domain.com' | 'John'    | 'Doe'    | 'KTT2$LYY' | 'KTT2$LYY'           | "Password doesn't have at least one lowercase character"
        'account@domain.com' | 'John'    | 'Doe'    | 'ktt2$lyy' | 'ktt2$lyy'           | "Password doesn't have at least one uppercase character"
    }

    void "Should get an error when exists an account with same email"() {
        given: "An AccountData with an email that already exists"
        def accountData = JsonOutput.toJson([
                email: 'duplicated.account@domain.com',
                firstName: 'John',
                lastName: 'Doe',
                password: 'K21$kljo',
                confirmationPassword: 'K21$kljo'
        ])

        when: "Try to create an account"
        def exchange = postAccountData(accountData)


        then: "Should get JSON with status = error and detailed info"
        exchange.expectStatus().isBadRequest()
        exchange.expectBody().jsonPath('$.status').isEqualTo('error')

    }

    private WebTestClient.ResponseSpec postAccountData(String accountData) {
        testClient.post()
                .uri("/api/v1/account")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(accountData))
                .exchange()
    }
}
