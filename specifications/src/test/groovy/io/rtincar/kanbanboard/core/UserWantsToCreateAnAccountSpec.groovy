package io.rtincar.kanbanboard.core

import groovy.json.JsonOutput
import io.rtincar.kanbanboard.configuration.AccountConfiguration
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters
import spock.lang.Narrative
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Title

@Title('An user wants to create an account')
@Narrative('''
 
 In order to access the system
 As a Anonymous user 
 I want to create an account
 
''')
@SpringBootTest(classes = [AccountConfiguration])
class UserWantsToCreateAnAccountSpec extends Specification {

    def JSON_STATUS_OK = '{"status" : "ok"}'

    @Autowired
    ApplicationContext context

    @Shared
    WebTestClient testClient

    def setup() {
        testClient = WebTestClient.bindToApplicationContext(context).build()
    }

    void "Should create new account and receive an activation email"() {

        given: "A valid account data"
        def accountData = JsonOutput.toJson([
                email: 'account@domain.com',
                firstName: 'John',
                lastName: 'Doe',
                password: 'K21$kljo',
                confirmationPassword: 'K21$kljo'
        ])

        when: "Try to create an account"

        def exchange = testClient.post()
                .uri("/api/v1/account")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(accountData))
                .exchange()


        then: "An new inactive account is created with received data"
        exchange.expectStatus().isCreated()
        exchange.expectBody().json(JSON_STATUS_OK)



    }


    void "Should throw InvalidDataException when data account is not valid"() {

        given: "An invalid account"


        when: "Try to create the account"



        then: "Should throw InvalidAccountException"



    }

    void "Should throw DuplicateAccountException when exists an account with same email"() {
        given: "An AccountData with an email that already exists"


        when: "Try to create an account"



        then: "Should throw DuplicateAccountException"

    }
}
